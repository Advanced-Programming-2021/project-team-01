package view;

import controller.DatabaseController;
import controller.GameController;
import controller.RegisterController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Stack;

public class GameView {
    StackPane imageCard;
    StackPane playerOneHealthBar;
    StackPane playerTwoHealthBar;
    ScrollPane cardInformation;
    private int width;
    private int height;

    public void init(Pane root) {
        try {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            width = gd.getDisplayMode().getWidth();
            height = gd.getDisplayMode().getHeight();
            RegisterController.onlineUser = DatabaseController.getUserByName("ali");
            GameController.getInstance().startGame("username", 1);
            playerOneHealthBar = createStackPane(300, 100,0,0);
            playerOneHealthBar.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
            playerTwoHealthBar = createStackPane(300, 100,0,height - 100);
            playerTwoHealthBar.setBackground(new Background(new BackgroundFill(Color.RED,CornerRadii.EMPTY,Insets.EMPTY)));
            imageCard = createStackPane(300,400,0,100);
            imageCard.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE,CornerRadii.EMPTY,Insets.EMPTY )));
            cardInformation = new ScrollPane();
            cardInformation.setTranslateY(500);
            StackPane cardText = createStackPane(300,200,0,0);
            cardInformation.setContent(cardText);
            cardInformation.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD,CornerRadii.EMPTY,Insets.EMPTY)));
            root.getChildren().addAll(playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private StackPane createStackPane(int width, int height,int x, int y){
        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(height);
        stackPane.setMaxHeight(height);
        stackPane.setPrefWidth(width);
        stackPane.setMaxWidth(width);
        stackPane.setTranslateX(x);
        stackPane.setTranslateY(y);
        return stackPane;
    }
}
