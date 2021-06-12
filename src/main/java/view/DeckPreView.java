package view;

import controller.DatabaseController;
import controller.DeckController;
import controller.RegisterController;
import controller.exceptions.DeckNotExists;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DeckPreView implements Initializable {
    public ChoiceBox<String> deckBar;

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
        }
        DeckController.getInstance().deleteDeck(deckBar.getValue());
        deckBar.getItems().remove(deckBar.getValue());
        new MyAlert(Alert.AlertType.CONFIRMATION, "Deck is deleted.").show();
    }

    @FXML
    private void createDeck() {
        if (deckBar.getValue() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
        }

    }
}
