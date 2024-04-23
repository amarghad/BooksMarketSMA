package ma.amarghad.books_sma.buyer;

import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ma.amarghad.books_sma.consumer.ConsumerApplication;
import ma.amarghad.books_sma.consumer.ConsumerContainer;

import java.io.IOException;

public class BuyerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws ControllerException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("buyer-app.fxml"));
        Parent parent = fxmlLoader.load();

        BuyerContainer.start(new Object[] { fxmlLoader.getController() });

        Scene scene = new Scene(parent, 600, 400);
        primaryStage.setTitle("Acheteur App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
