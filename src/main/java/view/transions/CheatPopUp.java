package view.transions;

import com.jfoenix.controls.JFXSlider;
import controller.GameController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import model.card.Card;
import view.MyAlert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheatPopUp extends Popup {
    JFXSlider slider = new JFXSlider();
    ChoiceBox<String> cardsChoiceBox = new ChoiceBox<>();
    ChoiceBox<String> nicknameChoiceBox = new ChoiceBox<>();
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
        pane.setBackground(new Background(backgroundimage));
        setupButtons(pane);
        setupExitButton(pane);
        getContent().add(pane);
        requestFocus();
        pane.getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());
    }

    private void setupExitButton(Pane pane) {
        Image image = new Image((getClass().getResource("/Assets/exit2.png")).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setTranslateX(550);
        imageView.setTranslateY(20);
        imageView.setOnMouseClicked(event -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);
            this.hide();
        });
        pane.getChildren().add(imageView);
    }

    private void setupButtons(Pane pane) {
        lpButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-min-width: 100px;");
        forceHandButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-min-width: 100px;");
        setWinnerButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-min-width: 100px;");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(lpButton, forceHandButton, setWinnerButton);
        vBox.setSpacing(45);
        vBox.setAlignment(Pos.CENTER);
        pane.getChildren().add(vBox);
        setupButtonsFunctionality();
        setupFields(pane, vBox);
    }

    private void setupFields(Pane pane, VBox buttonVBox) {
        Label  label  = new Label();
        nicknameChoiceBox.setPrefWidth(100);
        ObservableList<String> names = FXCollections.observableArrayList();
        names.add(GameController.getPlayerOne().getNickname());
        names.add(GameController.getPlayerTwo().getNickname());
        nicknameChoiceBox.setItems(names);
        for (Map.Entry<String, Card> entry : Card.getAllCards().entrySet()) {
            cardsChoiceBox.getItems().add(entry.getKey());
        }
        slider.setMax(500);
        slider.setMin(0);
        slider.setId("slider");
        label.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
        pane.getChildren().add(label);
        label.setTranslateX(550);
        label.setTranslateY(240);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(slider, cardsChoiceBox, nicknameChoiceBox);
        vBox.setSpacing(65);
        vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
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
            if (nicknameChoiceBox.getValue() == null)
                new MyAlert(Alert.AlertType.WARNING, "Field is blank").show();
            else {
                if (GameController.getPlayerOne().getNickname().equals(nicknameChoiceBox.getValue()) ||
                        GameController.getPlayerTwo().getNickname().equals(nicknameChoiceBox.getValue())) {
                    try {
                        this.hide();
                        GameController.getInstance().setWinner(nicknameChoiceBox.getValue());
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









