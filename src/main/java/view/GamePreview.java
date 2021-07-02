package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DatabaseController;
import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

public class GamePreview implements Initializable {
    public JFXTextField opponentUsername;
    public JFXButton playWithAIButton;
    public JFXButton headButton;
    public JFXButton tailButton;
    public JFXButton playWithOpponentButton;
    public JFXTextField roundNumAI;
    public JFXTextField roundNumMultiplayer;
    public StackPane coinStackPane;
    private boolean isCoinTossed = false, isUserFirstPlayer;
    private ImageView headImage = new ImageView(new Image(getClass().getResource("HeadToss.gif").toExternalForm()));
    private ImageView tailImage = new ImageView(new Image(getClass().getResource("TailToss.gif").toExternalForm()));

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
    
    @FXML
    private void startGameWithAI() throws Exception {
        if (isNumberOfRoundsValid(roundNumAI.getText())) {
            GameController.getInstance().startGame("AI", Integer.parseInt(roundNumAI.getText()));
            //ViewSwitcher.switchTo(View.GAME);
        } else
            new MyAlert(Alert.AlertType.WARNING, "Number of rounds not supported.").show();
    }

    @FXML
    private void startGameWithOpponent() throws Exception {
        if (isNumberOfRoundsValid(roundNumMultiplayer.getText())) {
            if (isCoinTossed) {
                if (DatabaseController.doesUserExists(opponentUsername.getText())) {
                    GameController.getInstance().startGame(opponentUsername.getText(), Integer.parseInt(roundNumMultiplayer.getText()));
                    //ViewSwitcher.switchTo(View.GAME);
                } else
                    new MyAlert(Alert.AlertType.WARNING, "No such username.").show();
            } else {

            }
        } else
            new MyAlert(Alert.AlertType.WARNING, "Number of rounds not supported.").show();
    }

    @FXML
    private void tossCoinHead() {
        if (isCoinTossed) {
            new MyAlert(Alert.AlertType.WARNING, "You've already tossed a coin").show();
        } else {
            isCoinTossed = true;
            if (generateRandomNumber() == 0) {
                isUserFirstPlayer = true;
                coinStackPane.getChildren().add(headImage);
            } else {
                isUserFirstPlayer = false;
                coinStackPane.getChildren().add(tailImage);
            }
        }
    }

    @FXML
    private void tossCoinTail() {
        if (isCoinTossed) {
            new MyAlert(Alert.AlertType.WARNING, "You've already tossed a coin").show();
        } else {
            isCoinTossed = true;
            if (generateRandomNumber() == 0) {
                isUserFirstPlayer = false;
                coinStackPane.getChildren().add(headImage);
            } else {
                isCoinTossed = true;
                coinStackPane.getChildren().add(tailImage);
            }
        }
    }

    private boolean isNumberOfRoundsValid(String roundNum) {
        if (roundNum.equals("1") || roundNum.equals("3"))
            return true;
        return false;
    }

    private int generateRandomNumber() {
        Random random = new Random();
        random.setSeed(LocalDateTime.now().getSecond());
        int result = random.nextInt() % 2;
        return result < 0 ? -1 * result : result;
    }
}
