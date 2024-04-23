package ma.amarghad.books_sma.consumer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import javafx.application.Platform;
import ma.amarghad.books_sma.entities.Book;

public class ConsumerAgent extends GuiAgent {

    private ConsumerController consumerController;
    private static final AID buyerId = new AID("Buyer", AID.ISLOCALNAME);


    public void setup() {
        Object[] args = getArguments();
        if (args.length >= 1 && args[0] instanceof ConsumerController) {
            consumerController = (ConsumerController) args[0];
            consumerController.setConsumerAgent(this);
        }

        Behaviour waitForInformMessage = new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage message = receive(messageTemplate);
                if (message != null) {
                    try {
                        Book book = (Book) message.getContentObject();
                        System.out.println(book != null ? "Livre reçu, avec un prix de " + book.getPrice() : "Aucune livre reçu");
                        Platform.runLater(() -> consumerController.inform(book));
                    } catch (UnreadableException e) { throw new RuntimeException(e); }
                } else block();

            }
        };

        addBehaviour(waitForInformMessage);

    }

    @Override
    public void onGuiEvent(GuiEvent guiEvent) {



        switch (guiEvent.getType()) {
            case 1 -> {
                String bookName = (String) guiEvent.getParameter(0);

                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.setContent(bookName);
                message.addReceiver(buyerId);
                send(message);

                System.out.printf("Demande envoyé à l'acheteur, nom du livre : %s\n", bookName);
            }

            case 2 -> {
                ACLMessage message = new ACLMessage(ACLMessage.CANCEL);
                message.addReceiver(buyerId);
                send(message);
            }
        }


    }
}
