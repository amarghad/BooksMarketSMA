package ma.amarghad.books_sma.buyer;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import ma.amarghad.books_sma.entities.Book;

import java.io.IOException;
import java.util.List;

public class AskAndNegotiateBehaviour extends CyclicBehaviour {

    private final List<AID> sellers;
    private int numOfReceivedPropositions = 0;
    private boolean negotiationStarted = false;
    private Book bestProposedBook = null;
    private AID bestSeller = null;

    public AskAndNegotiateBehaviour(List<AID> sellers) {
        this.sellers = sellers;
    }

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive();
        AID consumerAID = new AID("Consumer", AID.ISLOCALNAME);

        if(aclMessage != null) {
            switch (aclMessage.getPerformative()) {
                case ACLMessage.REQUEST -> {
                    if ( ! negotiationStarted ) {
                        String bookName = aclMessage.getContent();
                        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                        cfp.setContent(bookName);
                        for(AID seller : sellers) cfp.addReceiver(seller);
                        myAgent.send(cfp);
                        negotiationStarted = true;
                    }
                }

                case ACLMessage.AGREE, ACLMessage.REFUSE -> {
                    if ( negotiationStarted ) {
                        ACLMessage informMessage = new ACLMessage(ACLMessage.INFORM);
                        informMessage.addReceiver(consumerAID);
                        try {
                            informMessage.setContentObject(aclMessage.getPerformative() == ACLMessage.AGREE ? bestProposedBook : null);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        myAgent.send(informMessage);
                        stopNegotiations();
                    }
                }

                case ACLMessage.CANCEL -> {
                    if (negotiationStarted) {
                        stopNegotiations();
                    }
                }

                case ACLMessage.PROPOSE -> {
                    numOfReceivedPropositions++;
                    Book proposedBook = null;

                    try { proposedBook = (Book) aclMessage.getContentObject(); }
                    catch (UnreadableException e) { throw new RuntimeException(e); }

                    if (bestSeller == null) {
                        bestSeller = aclMessage.getSender();
                        bestProposedBook = proposedBook;
                    } else if (bestProposedBook == null || (proposedBook != null && bestProposedBook.getPrice() > proposedBook.getPrice())) {
                        bestProposedBook = proposedBook;
                    }


                    // Check if all sellers sent there proposals,
                    // even if it may be null
                    if (numOfReceivedPropositions == sellers.size()) {
                        if (bestProposedBook != null) {

                            // Calculate a proposed price with random discound between 0% and 30%
                            double randomDiscount = Math.random() * 0.3;
                            double proposedPrice = bestProposedBook.getPrice() * (1 - randomDiscount);
                            bestProposedBook.setPrice(proposedPrice);

                            ACLMessage apMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                            apMessage.setContent(String.valueOf(randomDiscount));
                            apMessage.addReceiver(bestSeller);
                            myAgent.send(apMessage);
                        } else {
                            negotiationStarted = false;
                        }
                        numOfReceivedPropositions = 0;
                    }
                }
            }
        } else block();
    }

    private void stopNegotiations() {
        bestProposedBook = null;
        negotiationStarted = false;
        numOfReceivedPropositions = 0;
    }
}
