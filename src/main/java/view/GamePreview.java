package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DatabaseController;
import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
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
    public StackPane coinStackPane;
    public ChoiceBox<String> roundChoiceBox;
    private boolean isCoinTossed = false, isUserFirstPlayer;
    private ImageView headImage = new ImageView(new Image(getClass().getResource("HeadToss.gif").toExternalForm()));
    private ImageView tailImage = new ImageView(new Image(getClass().getResource("TailToss.gif").toExternalForm()));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roundChoiceBox.getItems().addAll("1", "3");
    }
    
    @FXML
    private void startGameWithAI() throws Exception {
        if (isNumberOfRoundsValid(roundNumAI.getText())) {
            GameController.getInstance().startGame("AI", Integer.parseInt(roundNumAI.getText()));
        } else
            new MyAlert(Alert.AlertType.WARNING, "Number of rounds not supported.").show();
    }

    @FXML
    private void startGameWithOpponent() throws Exception {
        if (roundChoiceBox.getValue() != null) {
            if (isCoinTossed) {
                if (DatabaseController.doesUserExists(opponentUsername.getText())) {
                    try {
                        GameController.getInstance().startGame(opponentUsername.getText(), Integer.parseInt(roundChoiceBox.getValue()));
                    } catch (Exception e) {
                        new MyAlert(Alert.AlertType.WARNING, e.getMessage()).show();
                    }
                    ViewSwitcher.switchTo(View.GAME_VIEW);
                } else
                    new MyAlert(Alert.AlertType.WARNING, "No such username.").show();
            } else
                new MyAlert(Alert.AlertType.WARNING, "Coin haven't been tossed!").show();
        } else
            new MyAlert(Alert.AlertType.WARNING, "Choose a round number").show();
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
                isUserFirstPlayer = true;
                coinStackPane.getChildren().add(tailImage);
            }
        }
    }

    private boolean isNumberOfRoundsValid(String roundNum) {
        return roundNum.equals("1") || roundNum.equals("3");
    }

    private int generateRandomNumber() {
        Random random = new Random();
        random.setSeed(LocalDateTime.now().getSecond());
        int result = random.nextInt() % 2;
        return result < 0 ? -1 * result : result;
    }
}
