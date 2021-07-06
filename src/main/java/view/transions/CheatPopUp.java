package view.transions;

import controller.GameController;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import model.card.Card;
import view.MyAlert;

import java.util.Map;

public class CheatPopUp extends Popup {
    Slider slider = new Slider();
    ChoiceBox<String> cardsChoiceBox = new ChoiceBox<>();
    TextField nicknameTextField = new TextField();
    Button lpButton = new Button("Increase LP"),
            forceHandButton = new Button("Add card"),
            setWinnerButton = new Button("Set winner");

    public CheatPopUp() {
        centerOnScreen();
        Pane pane = new Pane();
        pane.setPrefHeight(600);
        pane.setPrefWidth(600);
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/view/CheatBackground.jpeg").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(600, 600, false, false, false, false));
        pane.setOnMouseClicked(event -> {
            hide();
        });
        pane.setBackground(new Background(backgroundimage));
        setupButtons(pane);
        getContent().add(pane);
        requestFocus();
    }

    private void setupButtons(Pane pane) {
        lpButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-pref-width: 100px;");
        forceHandButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-pref-width: 100px;");
        setWinnerButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-pref-width: 100px;");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(lpButton, forceHandButton, setWinnerButton);
        vBox.setSpacing(45);
        pane.getChildren().add(vBox);
        setupButtonsFunctionality();
        setupFields(pane, vBox);
    }

    private void setupFields(Pane pane, VBox buttonVBox) {
        Label  label  = new Label();
        nicknameTextField.setPrefWidth(100);
        nicknameTextField.setText("Enter winner's nickname:");
        for (Map.Entry<String, Card> entry : Card.getAllCards().entrySet()) {
            cardsChoiceBox.getItems().add(entry.getKey());
        }
        slider.setMax(500);
        slider.setMin(0);
        label.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
        pane.getChildren().add(label);
        label.setTranslateX(530);
        label.setTranslateY(220);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(slider, cardsChoiceBox, nicknameTextField);
        vBox.setSpacing(45);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(buttonVBox, vBox);
        hBox.setTranslateX(50);
        hBox.setTranslateY(230);
        hBox.setSpacing(100);
        pane.getChildren().add(hBox);
    }

    private void setupButtonsFunctionality() {
        lpButton.setOnMouseClicked(event -> {
            GameController.getInstance().increasePlayerLp((int)slider.getValue());
            new MyAlert(Alert.AlertType.INFORMATION, "LP got increased by value of " + (int)slider.getValue());
        });
        forceHandButton.setOnMouseClicked(event -> {
            if (cardsChoiceBox.getValue() != null) {
                try {
                    GameController.getInstance().cheater(cardsChoiceBox.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                new MyAlert(Alert.AlertType.WARNING, "No card is chosen!").show();
        });
        setWinnerButton.setOnMouseClicked(event -> {
            if (nicknameTextField.getText().equals(""))
                new MyAlert(Alert.AlertType.WARNING, "Field is blank").show();
            else {
                if (GameController.getPlayerOne().getNickname().equals(nicknameTextField.getText()) ||
                        GameController.getPlayerTwo().getNickname().equals(nicknameTextField.getText())) {
                    try {
                        GameController.getInstance().setWinner(nicknameTextField.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    new MyAlert(Alert.AlertType.WARNING, "No such username in game!").show();
            }
        });
    }
}









