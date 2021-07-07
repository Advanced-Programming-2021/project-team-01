package view.transions;

import controller.GameController;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import view.GameView;
import view.MyAlert;
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
            GameView.getInstance().reset();
            ViewSwitcher.switchTo(View.MAIN);
        });
        ImageView imageView2 = new ImageView(new Image(getClass().getResource("/view/Surrender.png").toExternalForm()));
        imageView2.setTranslateX(350);
        imageView2.setTranslateY(175);
        imageView2.setFitWidth(200);
        imageView2.setFitHeight(100);
        imageView2.setOnMouseEntered(event -> {
            imageView2.setScaleX(1.2);
            imageView2.setScaleY(1.2);
        });
        imageView2.setOnMouseExited(event -> {
            imageView2.setScaleX(1);
            imageView2.setScaleY(1);
        });
        imageView2.setOnMouseClicked(event -> {
            hide();
            GameController.getInstance().surrender();
            ViewSwitcher.switchTo(View.MAIN);
            new MyAlert(Alert.AlertType.INFORMATION, "Player surrendered").show();
        });
        root.getChildren().addAll(imageView, imageView1, imageView2);
    }
}
