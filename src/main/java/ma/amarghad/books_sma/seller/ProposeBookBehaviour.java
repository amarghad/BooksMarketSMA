package ma.amarghad.books_sma.seller;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ma.amarghad.books_sma.entities.Book;

import java.io.IOException;
import java.util.List;

public class ProposeBookBehaviour extends CyclicBehaviour {

    private SellerController sellerController;

    public ProposeBookBehaviour(SellerController sellerController) {
        this.sellerController = sellerController;
    }

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive();
        if (aclMessage != null) {
            switch (aclMessage.getPerformative()) {

                case ACLMessage.CFP -> {

                    String bookName = aclMessage.getContent();
                    ACLMessage proposeMessage = aclMessage.createReply(ACLMessage.PROPOSE);

                    List<Book> proposedBooks = sellerController.getBooks()
                            .stream()
                            .filter(b -> b.getTitle().equals(bookName))
                            .toList();

                    Book proposedBook = null;
                    if (!proposedBooks.isEmpty()) proposedBook = proposedBooks.get(0);

                    try {
                        proposeMessage.setContentObject(proposedBook);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    myAgent.send(proposeMessage);

                }

                case ACLMessage.ACCEPT_PROPOSAL -> {

                    double proposedDiscount = Double.parseDouble(aclMessage.getContent());
                    // Calculate a random allowed discount
                    double randomDiscount = Math.random() * 0.3;

                    int replyPerformative = proposedDiscount <= randomDiscount ? ACLMessage.AGREE : ACLMessage.REFUSE;
                    ACLMessage acceptOrRefuse = aclMessage.createReply(replyPerformative);
                    myAgent.send(acceptOrRefuse);

                }
            }
        } else block();
    }
}
