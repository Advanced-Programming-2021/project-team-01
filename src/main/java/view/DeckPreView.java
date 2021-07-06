package view;

import controller.DatabaseController;
import controller.DeckController;
import controller.RegisterController;
import controller.exceptions.DeckNotExists;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeckPreView implements Initializable {
    public ChoiceBox<String> deckBar;
    public TextField textField;
    public Label sizeLabel;
    public Label activeDeckLabel;

    public void init() {
        RegisterController.onlineUser = DatabaseController.getUserByName("mamaaad");
        activeDeckLabel.setText("Active deck: " + (RegisterController.onlineUser.getActiveDeck() == null ? "None" :
                RegisterController.onlineUser.getActiveDeck()));
        deckBar.setOnAction(event -> {
            try {
                sizeLabel.setText("Size: " + DeckController.getInstance().showDeckByName(deckBar.getValue(), true).size());
            } catch (DeckNotExists deckNotExists) {
                deckNotExists.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        activeDeckLabel.setText("Active deck: " + (RegisterController.onlineUser.getActiveDeck() == null ? "None" :
                RegisterController.onlineUser.getActiveDeck()));
        new MyAlert(Alert.AlertType.INFORMATION, "Deck is activated.").show();
    }
}
