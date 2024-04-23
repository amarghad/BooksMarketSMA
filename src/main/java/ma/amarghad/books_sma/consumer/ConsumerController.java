package ma.amarghad.books_sma.consumer;

import jade.gui.GuiEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ma.amarghad.books_sma.entities.Book;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsumerController implements Initializable {

    private ConsumerAgent consumerAgent;
    @FXML private TextField bookNameTextField;
    @FXML private Label bookNameLabel;
    @FXML private Label priceLabel;
    @FXML private ProgressIndicator spinner;
    @FXML private Button searchBtn;
    @FXML private VBox bookCard;
    @FXML private Button cancelBtn;
    private boolean waiting;


    @FXML
    public void search() {

        if (waiting) {
            new Alert(Alert.AlertType.ERROR, "Veuillez attender jusqu'à la demande actulle se terminera").show();
            return;
        }

        String bookName = bookNameTextField.getText();
        if (bookName.isEmpty() || bookName.isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Veuillez saisir le nom d'un livre").show();
            return;
        }


        GuiEvent guiEvent = new GuiEvent(this, 1);
        guiEvent.addParameter(bookName);
        consumerAgent.onGuiEvent(guiEvent);

        spinner.setOpacity(1);
        bookNameTextField.setDisable(true);
        searchBtn.setDisable(true);
        cancelBtn.setOpacity(1);
        bookCard.setOpacity(0);
        waiting = true;
    }

    public void inform(Book book) {
        stopWaiting();
        if (book == null) {
            new Alert(Alert.AlertType.ERROR, "Aucune livre a été trouvé").show();
            return;
        }

        bookNameLabel.setText(book.getTitle());
        priceLabel.setText(String.format("%.2f", book.getPrice()));
        bookCard.setOpacity(1);
    }

    public void setConsumerAgent(ConsumerAgent consumerAgent) {
        this.consumerAgent = consumerAgent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookCard.setOpacity(0);
        spinner.setOpacity(0);
        cancelBtn.setOpacity(0);
        waiting = false;
    }

    public void stopWaiting() {
        if (!waiting) return;

        cancelBtn.setOpacity(0);
        spinner.setOpacity(0);
        searchBtn.setDisable(false);
        bookNameTextField.setDisable(false);
        waiting = false;
    }

    public void cancel() {

        stopWaiting();

        GuiEvent guiEvent = new GuiEvent(this, 2);
        consumerAgent.onGuiEvent(guiEvent);

    }
}