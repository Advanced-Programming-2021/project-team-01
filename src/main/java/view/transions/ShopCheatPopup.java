package view.transions;

import controller.RegisterController;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import view.MyAlert;

import java.awt.*;

public class ShopCheatPopup extends Popup {
    Slider slider = new Slider();
    Button button = new Button("Add money");

    public ShopCheatPopup() {
        centerOnScreen();
        Pane pane = new Pane();
        pane.setPrefHeight(300);
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
        setupButtonAndSlider(pane);
        getContent().add(pane);
        requestFocus();
    }

    private void setupButtonAndSlider(Pane pane) {
        Label label = new Label();
        label.setFont(new Font(30));
        label.setStyle("-fx-text-fill: yellow");
        button.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;-fx-pref-width: 100px;");
        button.setOnMouseClicked(event -> {
            RegisterController.onlineUser.increaseMoney((int)slider.getValue());
            new MyAlert(Alert.AlertType.INFORMATION, "Money is increased by value of " + (int)slider.getValue()).show();
        });
        slider.setMax(10000);
        slider.setMin(0);
        label.textProperty().bind(Bindings.format("%.0f", slider.valueProperty()));
        pane.getChildren().add(label);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(button, slider, label);
        hBox.setSpacing(30);
        hBox.setTranslateY(150);
        hBox.setTranslateX(50);
        pane.getChildren().add(hBox);
    }
}
