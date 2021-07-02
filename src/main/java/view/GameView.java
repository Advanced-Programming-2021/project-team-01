package view;

import com.jfoenix.controls.JFXScrollPane;
import controller.DatabaseController;
import controller.GameController;
import controller.RegisterController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;
import model.card.Card;

import java.awt.*;
import java.util.Stack;

public class GameView {
    StackPane imageCard;
    StackPane playerOneHealthBar;
    StackPane playerTwoHealthBar;
    ScrollPane cardInformation;
    GridPane playerOneHand;
    GameController gameController;
    private int width;
    private int height;
    private GridPane playerTwoHand;

    public void init(Pane root) {
        try {
            gameController = GameController.getInstance();
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
            playerOneHand = new GridPane();
            playerOneHand.setTranslateX(500);
            playerTwoHand = new GridPane();
            playerTwoHand.setTranslateX(500);
            playerTwoHand.setTranslateY(height);
            setupHands();
            cardText.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD,CornerRadii.EMPTY,Insets.EMPTY)));
            root.getChildren().addAll(playerTwoHand, playerOneHand, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setupHands() {
        playerOneHand.setTranslateY(height - 200);
        Board board = gameController.getGameBoard();
        for (int i = 0;i < board.getPlayerOneHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i));
            cardView.setImage(false,false);
            cardView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imageCard.getChildren().clear();
                    imageCard.getChildren().add(new Rectangle(300,400,cardView.getImage()));
                    cardView.setScaleX(1.2);
                    cardView.setScaleY(1.2);
                }
            });
            playerOneHand.add(cardView,i,1);
        }
        playerTwoHand.setTranslateY(5);
        for (int i = 0; i < board.getPlayerTwoHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i));
            cardView.setImage(true,true);
            cardView.setOnMouseEntered(event -> {
                imageCard.getChildren().clear();
                imageCard.getChildren().add(new Rectangle(300,400,cardView.getImage()));
                cardView.setScaleX(1.2);
                cardView.setScaleY(1.2);
            });
            playerTwoHand.add(cardView,i,1);
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