package view;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane mainPane = new AnchorPane();
        mainPane.setPrefWidth(700);
        mainPane.setPrefHeight(700);
        Image image = new Image(getClass().getResource("/view/2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        mainPane.getChildren().add(imageView);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
