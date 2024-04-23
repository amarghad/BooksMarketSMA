package ma.amarghad.books_sma.consumer;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ConsumerContainer {

    public static void start(Object[] args) throws ControllerException {

        Runtime runtime = Runtime.instance();

        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.CONTAINER_NAME, "Consumer-Container");

        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        AgentController agentController = agentContainer.createNewAgent("Consumer", ConsumerAgent.class.getName(), args);
        agentController.start();
        agentContainer.start();

    }

}
