package view.transions;

import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setPrefWidth(1000);
        pane.setPrefHeight(600);
        Rectangle rectangle = new Rectangle(100, 600, Color.TRANSPARENT);
        pane.getChildren().add(rectangle);
        Lighting lighting = new Lighting(rectangle);
        lighting.play();
        lighting.setOnFinished(event -> {
            rectangle.setFill(Color.TRANSPARENT);
        });
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
