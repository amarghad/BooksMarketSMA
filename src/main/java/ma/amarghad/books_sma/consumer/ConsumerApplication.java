package ma.amarghad.books_sma.consumer;

import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConsumerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ControllerException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("consumer-app.fxml"));
        Parent parent = fxmlLoader.load();

        ConsumerContainer.start(new Object[] { fxmlLoader.getController() });

        Scene scene = new Scene(parent, 800, 600);
        stage.setTitle("Acheteur App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}