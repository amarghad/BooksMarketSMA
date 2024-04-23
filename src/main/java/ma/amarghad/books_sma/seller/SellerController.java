package ma.amarghad.books_sma.seller;

import jade.wrapper.ControllerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.amarghad.books_sma.entities.Book;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SellerController implements Initializable {

    @FXML private TextField bookNameTextField;
    @FXML private TextField priceTextField;

    @FXML private TableColumn<Book, Long> idColumn;
    @FXML private TableColumn<Book, String> bookNameColumn;
    @FXML private TableColumn<Book, Double> priceColumn;
    @FXML private TableView<Book> booksTable;

    @FXML private TextField sellerNameTextField;
    @FXML private Button registerBtn;

    private final ObservableList<Book> books = FXCollections.observableArrayList();
    private boolean deployed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        deployed = false;
        booksTable.setItems(books);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    @FXML
    void deploy(ActionEvent event) {

        if (deployed) return;
        Alert error = new Alert(Alert.AlertType.ERROR);
        String name = sellerNameTextField.getText();
        if (name.isEmpty() || name.isBlank()) {
            error.setContentText("Veuillez saisir le nom du vendeur");
            error.show();
            return;
        }

        try {
            SellerContainer.start(name, new Object[] {this});
        } catch (ControllerException e) {
            error.setContentText("Erreur d'enregistement! Le nom est probablement déja utilisé");
            error.show();
            return;
        }

        sellerNameTextField.setDisable(true);
        registerBtn.setDisable(true);
        deployed = true;
        new Alert(Alert.AlertType.CONFIRMATION, "Agent est enregistré avec succès").show();

    }

    void clearForm() {
        bookNameTextField.clear();
        priceTextField.clear();
    }

    @FXML
    void add(ActionEvent event) {
        int id = (int) (Math.random() * 10000);
        try {
            double price = Double.parseDouble(priceTextField.getText());
            String title = bookNameTextField.getText();

            Book book = new Book(id, title, price);
            books.add(book);
            new Alert(Alert.AlertType.CONFIRMATION, "Le livre est ajouté avec succès").show();
            clearForm();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erreur de saisie").show();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (Objects.isNull(selectedBook)) {
            new Alert(Alert.AlertType.ERROR, "Selectionner un livre du tableau").show();
            return;
        }

        books.remove(selectedBook);
        new Alert(Alert.AlertType.CONFIRMATION, "Le livre est supprimé avec succès").show();
    }

    public ObservableList<Book> getBooks() { return books; }

}
