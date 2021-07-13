package view;

import Network.Client.Client;
import Network.Requests.Account.ActivateDeckRequest;
import Network.Requests.Account.DeckInfoRequest;
import Network.Requests.Request;
import Network.Responses.ActivateDeckResponse;
import Network.Responses.Response;
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
import javafx.scene.layout.Pane;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeckPreView implements GraphicalView {
    public ChoiceBox<String> deckBar;
    public TextField textField;
    public Label sizeLabel;
    public Label activeDeckLabel;
    public Player player;

    @Override
    public void init(Pane root) {
        Request request = new DeckInfoRequest(Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
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
        Request request = new ActivateDeckRequest(deckBar.getValue(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
    }

    public void activateDeckResponse(Response response, String deckName) {
        try {
            if (response.getException() != null) throw response.getException();
            activeDeckLabel.setText("Active deck: " + deckName);
            new MyAlert(Alert.AlertType.INFORMATION, "Deck is activated.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.WARNING, expt.getMessage()).show();
        }
    }

    public void receivePlayerResponse() {
        System.out.println("------>" + player.getActiveDeck());
        activeDeckLabel.setText("Active deck: " + (player.getActiveDeck() == null ? "None" :
                player.getActiveDeck()));
        deckBar.setOnAction(event -> {
            try {
                sizeLabel.setText("Size: " + DeckController.getInstance().showDeckByName(deckBar.getValue(), true).size());
            } catch (DeckNotExists deckNotExists) {
                deckNotExists.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        for (String playerDeck : player.getPlayerDecks()) {
            deckBar.getItems().add(playerDeck);
        }
    }
}
