package ma.amarghad.books_sma.buyer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class BuyerController implements Initializable {

    @FXML private ListView<String> sellersListView;

    private final ObservableList<String> sellers = FXCollections.observableArrayList();
    private final ObservableList<String> requests = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sellersListView.setItems(sellers);
    }

    public ObservableList<String> getSellers() {
        return sellers;
    }

    public ObservableList<String> getRequests() {
        return requests;
    }
}
