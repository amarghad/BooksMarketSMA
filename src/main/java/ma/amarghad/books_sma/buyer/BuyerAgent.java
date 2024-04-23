package ma.amarghad.books_sma.buyer;

import jade.core.AID;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import java.util.LinkedList;
import java.util.List;

public class BuyerAgent extends GuiAgent {

    private BuyerController buyerController;
    private final List<AID> sellers = new LinkedList<>();

    public void setup() {

        Object[] args = getArguments();
        if (args.length > 0 && args[0] instanceof BuyerController) {
            buyerController = (BuyerController) args[0];
        }

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new AskForSellersBehaviour(this, sellers, buyerController));
        parallelBehaviour.addSubBehaviour(new AskAndNegotiateBehaviour(sellers));
        addBehaviour(parallelBehaviour);

    }


    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
