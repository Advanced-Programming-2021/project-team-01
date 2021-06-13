package view;

import controller.DatabaseController;
import controller.DeckController;
import controller.RegisterController;
import controller.exceptions.DeckNotExists;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeckPreView implements Initializable {
    public ChoiceBox<String> deckBar;
    public TextField textField;

    public void init() {
        RegisterController.onlineUser = DatabaseController.getUserByName("ali");
        for (String playerDeck : RegisterController.onlineUser.getPlayerDecks()) {
            deckBar.getItems().add(playerDeck);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    @FXML
    private void exit() {
        ViewSwitcher.switchTo(View.MAIN);
    }

    @FXML
    private void deleteDeck() throws DeckNotExists {
        if (deckBar.getValue() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
            return;
        }
        DeckController.getInstance().deleteDeck(deckBar.getValue());
        deckBar.getItems().remove(deckBar.getValue());
        new MyAlert(Alert.AlertType.CONFIRMATION, "Deck is deleted.").show();
    }

    @FXML
    private void createDeck() {
        if (textField.getText() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
            return;
        }
        try {
            DeckController.getInstance().createDeck(textField.getText());
            deckBar.getItems().add(textField.getText());
            new MyAlert(Alert.AlertType.INFORMATION, "Deck is created.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.ERROR, expt.getMessage()).show();
        }
    }

    @FXML
    private void startDeckView() throws Exception {
        if (deckBar.getValue() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
            return;
        }
        DeckView.currentDeck = DatabaseController.getDeckByName(deckBar.getValue());
        ViewSwitcher.switchTo(View.DECK);
    }

    @FXML
    private void activateDeck() throws Exception {
        if (deckBar.getValue() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
            return;
        }
        DeckController.getInstance().activateDeck(deckBar.getValue());
        new MyAlert(Alert.AlertType.INFORMATION, "Deck is activated.").show();
    }
}
