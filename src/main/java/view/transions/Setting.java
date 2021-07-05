package view.transions;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import view.GameView;

import java.util.Objects;

public class Setting extends Popup {
    Pane root = new Pane();

    public Setting(){
        setWidth(700);
        setHeight(500);
        centerOnScreen();
        root.setPrefHeight(500);
        root.setPrefWidth(700);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Setting.png")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(700,500,false,false,true,true);
        BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,backgroundSize);
        root.setBackground(new Background(backgroundImage));
        this.getContent().add(root);
        ImageView imageView = new ImageView(new Image(getClass().getResource("/Assets/mute.png").toExternalForm()));
        imageView.setTranslateY(100);
        imageView.setTranslateX(100);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setOnMouseEntered(event -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);
        });
        imageView.setOnMouseExited(event -> {
            imageView.setScaleX(1);
            imageView.setScaleY(1);
        });
        imageView.setOnMouseClicked(event -> {
            GameView.getInstance().mute();
        });
        root.getChildren().add(imageView);
    }
}
