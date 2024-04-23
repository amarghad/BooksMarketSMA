package ma.amarghad.books_sma.seller;

import jade.core.Agent;
import jade.core.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerApplication extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(SellerContainer::terminateAgent));
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("seller-app.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 800, 600);
        primaryStage.setTitle("Vendeur App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
