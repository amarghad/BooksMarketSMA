package ma.amarghad.books_sma.seller;

import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

public class SellerAgent extends GuiAgent {

    private SellerController sellerController;


    public void setup(){

        Object[] args = getArguments();
        if (args.length > 0 && args[0] instanceof SellerController) {
            sellerController = (SellerController) args[0];
        }

        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        service.setType("transaction");
        service.setName("sell-books");

        template.addServices(service);
        try {
            DFService.register(this, template);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new ProposeBookBehaviour(sellerController));
        addBehaviour(parallelBehaviour);

    }

    @Override
    public void takeDown() {
        System.out.println("-- Taking down agent");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGuiEvent(GuiEvent guiEvent) {

    }
}
