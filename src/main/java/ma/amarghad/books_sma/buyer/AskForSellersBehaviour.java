package ma.amarghad.books_sma.buyer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import javafx.application.Platform;

import java.util.List;

public class AskForSellersBehaviour extends TickerBehaviour {

    private final List<AID> sellers;
    private final BuyerController buyerController;

    public AskForSellersBehaviour(Agent a, List<AID> sellers, BuyerController buyerController) {
        super(a, 20000);
        this.sellers = sellers;
        this.buyerController = buyerController;
    }

    @Override
    protected void onTick() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        service.setType("transaction");
        service.setName("sell-books");
        template.addServices(service);

        sellers.clear();
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            for (DFAgentDescription row : result) sellers.add(row.getName());
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }

        Platform.runLater(() -> {
            buyerController.getSellers().clear();
            sellers.forEach(s -> buyerController.getSellers().add(s.getLocalName()));
        });

    }
}
