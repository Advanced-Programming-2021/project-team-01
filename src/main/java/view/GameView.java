package view;

import controller.DatabaseController;
import controller.GameController;
import controller.RegisterController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Board;

import java.awt.*;

public class GameView {
    StackPane imageCard;
    StackPane playerOneHealthBar;
    StackPane playerTwoHealthBar;
    ScrollPane cardInformation;
    GridPane playerOneHand;
    StackPane mainPane;
    GameController gameController;
    private int width;
    private int height;
    private GridPane playerTwoHand;

    public void init(Pane root) {
        try {
            gameController = GameController.getInstance();
//            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//            width = gd.getDisplayMode().getWidth();
//            height = gd.getDisplayMode().getHeight();
            width = 1366;
            height = 768;
            RegisterController.onlineUser = DatabaseController.getUserByName("ali");
            GameController.getInstance().startGame("username", 1);
            playerOneHealthBar = createStackPane(300, 100, 0, 0);
            playerOneHealthBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            playerTwoHealthBar = createStackPane(300, 100, 0, height - 100);
            playerTwoHealthBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            imageCard = createStackPane(300, 400, 0, 100);
            imageCard.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            cardInformation = new ScrollPane();
            cardInformation.setTranslateY(500);
            StackPane cardText = createStackPane(300, 200, 0, 0);
            cardInformation.setContent(cardText);
            playerOneHand = new GridPane();
            playerOneHand.setTranslateX(500);
            playerTwoHand = new GridPane();
            playerTwoHand.setTranslateX(500);
            playerTwoHand.setTranslateY(height);
            cardText.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
            mainPane = new StackPane();
            mainPane.setTranslateX(300);
            BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/Assets/Field/fie_normal.bmp").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            mainPane.setBackground(new Background(backgroundimage));
            mainPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            mainPane.setPrefWidth(1100);
            mainPane.setPrefHeight(768);
            mainPane.getChildren().addAll(playerOneHand, playerTwoHand);
            setupHealthBar();
            setupHands();
            root.getChildren().addAll(mainPane, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
            cardText.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
            root.getChildren().addAll(playerTwoHand, playerOneHand, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setupHands() {
        playerOneHand.setTranslateX(200);
        playerOneHand.setTranslateY(height - 100);
        Board board = gameController.getGameBoard();
        for (int i = 0; i < board.getPlayerOneHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i));
            cardView.setImage(false, false);
            cardView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imageCard.getChildren().clear();
                    imageCard.getChildren().add(new Rectangle(300, 400, cardView.getImage()));
                    cardView.setScaleX(1.2);
                    cardView.setScaleY(1.2);
                    StackPane cardText = (StackPane) cardInformation.getContent();
                    cardText.getChildren().clear();
                    if (!cardView.isHidden()) {
                        Text text = new Text();
                        text.setWrappingWidth(300);
                        text.setText(cardView.getCard().getDescription());
                        cardText.getChildren().add(text);
                    }
                }
            });
            playerOneHand.add(cardView, i, 1);
        }
        playerTwoHand.setTranslateX(200);
        playerTwoHand.setTranslateY(-100);
        for (int i = 0; i < board.getPlayerTwoHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i));
            cardView.setImage(true, true);
            cardView.setOnMouseEntered(event -> {
                imageCard.getChildren().clear();
                imageCard.getChildren().add(new Rectangle(300, 400, cardView.getImage()));
                cardView.setScaleX(1.2);
                cardView.setScaleY(1.2);

                StackPane cardText = (StackPane) cardInformation.getContent();
                cardText.getChildren().clear();
                if (!cardView.isHidden()) {
                    Text text = new Text();
                    text.setWrappingWidth(300);
                    text.setText(cardView.getCard().getDescription());
                    cardText.getChildren().add(text);
                }
            });
            playerTwoHand.add(cardView, i, 1);
        }
    }

    public void setupHealthBar() {
        int playerOneLp = gameController.getPlayerOneLp();
        Text text = new Text();
        text.setText("LP : " + playerOneLp);
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(playerOneLp*1.0/8000);
        VBox vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.getChildren().add(progressBar);
        playerOneHealthBar.getChildren().add(vBox);

        int playerTwoLp = gameController.getPlayerTwoLp();
        text = new Text();
        text.setText("LP : " + playerTwoLp);
        progressBar = new ProgressBar();
        progressBar.setProgress(playerTwoLp*1.0/8000);
        vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.getChildren().add(progressBar);
        playerTwoHealthBar.getChildren().add(vBox);
    }

    private StackPane createStackPane(int width, int height, int x, int y) {
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
