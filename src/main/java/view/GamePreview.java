package view;

import Network.Client.Client;
import Network.Requests.Battle.CancelMatchMakingRequest;
import Network.Requests.Battle.NewMatchmakingRequest;
import Network.Requests.StartOnlineDuelRequest;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.DatabaseController;
import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Player;
import view.transions.SuperWaitingDialog;
import view.transions.WaitingDialog;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

public class GamePreview implements  GraphicalView{
    public JFXTextField opponentUsername;
    public JFXButton playWithAIButton;
    public JFXButton headButton;
    public JFXButton tailButton;
    public JFXButton playWithOpponentButton;
    public JFXTextField roundNumAI;
    public StackPane coinStackPane;
    public ChoiceBox<String> roundChoiceBox;
    public ChoiceBox<String> roundNumberChoiceBox;
    public JFXButton sendRequest;
    public JFXButton cancelRequest;
    public SuperWaitingDialog superWaitingDialog;
    private boolean isCoinTossed = false, isUserFirstPlayer;
    private ImageView headImage = new ImageView(new Image(getClass().getResource("HeadToss.gif").toExternalForm()));
    private ImageView tailImage = new ImageView(new Image(getClass().getResource("TailToss.gif").toExternalForm()));

    @Override
    public void init(Pane root) {
        roundChoiceBox.getItems().addAll("1", "3");
    }

    @FXML
    private void startGameWithOpponent() throws Exception {
        if (roundChoiceBox.getValue() != null) {
            if (isCoinTossed) {
                if (DatabaseController.doesUserExists(opponentUsername.getText())) {
                    try {
                        StartOnlineDuelRequest request = new StartOnlineDuelRequest(Client.getInstance().getToken(),
                                opponentUsername.getText(), Integer.parseInt(roundChoiceBox.getValue()),
                                GameController.getInstance().isReversed);
                        Client.getInstance().sendData(request.toString());
                        //GameController.getInstance().startGame(opponentUsername.getText(), Integer.parseInt(roundChoiceBox.getValue()));
                        //ViewSwitcher.switchTo(View.GAME_VIEW);
                    } catch (Exception e) {
                        new MyAlert(Alert.AlertType.WARNING, e.getMessage()).show();
                    }
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
        if (!isUserFirstPlayer)
            GameController.getInstance().reverse();
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
        if (!isUserFirstPlayer)
            GameController.getInstance().reverse();
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

    public void sendRequest(MouseEvent event) {
        int numberOfRounds = Integer.parseInt(roundNumberChoiceBox.getValue());
        NewMatchmakingRequest request = new NewMatchmakingRequest(Client.getInstance().getToken(),numberOfRounds);
        Client.getInstance().sendData(request.toString());
        superWaitingDialog = new SuperWaitingDialog();
        superWaitingDialog.showAndWait();
    }

}
