package ma.amarghad.books_sma.seller;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class SellerContainer {

    private static AgentController agentController = null;

    public static void terminateAgent() {
        if (agentController == null) return ;
        try {
            agentController.kill();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    public static void start(String agentName, Object[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.CONTAINER_NAME, "Seller-" + agentName + "-Container");
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        agentContainer.start();

        agentController = agentContainer.createNewAgent(agentName, SellerAgent.class.getName(), args);
        agentController.start();

    }

}
