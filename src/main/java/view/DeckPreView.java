package view;

import Network.Client.Client;
import Network.Requests.Account.*;
import Network.Requests.Request;
import Network.Responses.Account.CustomizeDeckResponse;
import Network.Responses.Response;
import controller.DatabaseController;
import controller.DeckController;
import controller.exceptions.DeckNotExists;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Deck;
import model.Player;

import java.io.IOException;

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
        Request request = new DeleteDeckRequest(deckBar.getValue(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
    }

    @FXML
    private void createDeck() {
        if (textField.getText() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
            return;
        }
        Request request = new CreateDeckRequest(textField.getText(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
    }

    @FXML
    private void startDeckView() throws Exception {
        if (deckBar.getValue() == null) {
            new MyAlert(Alert.AlertType.ERROR, "No deck is selected.").show();
            return;
        }
        Request request = new CustomizeDeckRequest(deckBar.getValue(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
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

    public void createDeckResponse(Response response) {
        try {
            if (response.getException() != null) throw response.getException();
            deckBar.getItems().add(textField.getText());
            new MyAlert(Alert.AlertType.INFORMATION, "Deck is created.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.ERROR, expt.getMessage()).show();
        }
    }

    public void deleteDeckResponse(Response response) {
        try {
            if (response.getException() != null)
                throw response.getException();
            deckBar.getItems().remove(deckBar.getValue());
            new MyAlert(Alert.AlertType.CONFIRMATION, "Deck is deleted.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.ERROR, expt.getMessage()).show();
        }
    }

    public void customizeDeckResponse(Response response, Deck deck) {
        DeckView.currentDeck = deck;
        DeckView.player = player;
        ViewSwitcher.switchTo(View.DECK);
    }
}
