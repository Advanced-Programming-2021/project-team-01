package view;

import controller.DatabaseController;
import controller.RegisterController;
import javafx.fxml.Initializable;
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
}
