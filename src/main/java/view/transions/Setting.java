package view.transions;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import view.GameView;
import view.View;
import view.ViewSwitcher;

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
        ImageView imageView1 = new ImageView(new Image(getClass().getResource("/Assets/exit.png").toExternalForm()));
        imageView1.setTranslateY(300);
        imageView1.setTranslateX(100);
        imageView1.setFitHeight(100);
        imageView1.setFitWidth(100);
        imageView1.setOnMouseEntered(event -> {
            imageView1.setScaleX(1.2);
            imageView1.setScaleY(1.2);
        });
        imageView1.setOnMouseExited(event -> {
            imageView1.setScaleX(1);
            imageView1.setScaleY(1);
        });
        imageView1.setOnMouseClicked(event -> {
            hide();
            GameView.getInstance().mute();
            ViewSwitcher.switchTo(View.MAIN);
        });
        root.getChildren().addAll(imageView, imageView1);
    }
}
