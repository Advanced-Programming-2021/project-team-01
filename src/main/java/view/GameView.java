package view;

import controller.DatabaseController;
import controller.GameController;
import controller.RegisterController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Board;

public class GameView {
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1366;
    StackPane imageCard;
    StackPane playerOneHealthBar;
    StackPane playerTwoHealthBar;
    ScrollPane cardInformation;
    GridPane playerOneHand;
    GridPane playerOneMonsterZone;
    GridPane playerTwoMonsterZone;
    StackPane mainPane;
    GameController gameController;
    private GridPane playerTwoHand;

    public void init(Pane root) {
        try {
            root.getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());
            gameController = GameController.getInstance();
            RegisterController.onlineUser = DatabaseController.getUserByName("ali");
            GameController.getInstance().startGame("username", 1);
            playerOneHealthBar = createStackPane(300, 70, 0, 0);
            playerOneHealthBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            playerTwoHealthBar = createStackPane(300, 70, 0, HEIGHT - 117);
            playerTwoHealthBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            imageCard = createStackPane(300, 400, 0, 70);
            imageCard.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            cardInformation = new ScrollPane();
            cardInformation.setTranslateY(470);
            cardInformation.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            StackPane cardText = createStackPane(300, 133, 0, 0);
            cardInformation.setContent(cardText);
            playerOneHand = new GridPane();
            playerOneHand.setTranslateX(500);
            playerTwoHand = new GridPane();
            playerTwoHand.setTranslateX(500);
            playerTwoHand.setTranslateY(HEIGHT);
            cardText.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
            mainPane = new StackPane();
            setBackgroundImage("fie_normal");
            mainPane.setTranslateX(300);
            mainPane.setPrefWidth(WIDTH - 300);
            mainPane.setPrefHeight(HEIGHT);
            mainPane.getChildren().addAll(playerOneHand, playerTwoHand);
            setupHealthBar();
            setupHands();
            setupGamePane();
            root.getChildren().addAll(mainPane, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
            cardText.setBackground(new Background(new BackgroundFill(Color.DARKGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setupGamePane() {
        playerOneMonsterZone = new GridPane();
        playerOneMonsterZone.setTranslateX(210);
        playerOneMonsterZone.setTranslateY(380);
        for (int i = 0; i < 4; i++){
            playerOneMonsterZone.setHgap(80);
            playerOneMonsterZone.add(new Rectangle(70,100,Color.GREEN),i,1);
        }
        mainPane.getChildren().add(playerOneMonsterZone);
    }

    private void setBackgroundImage(String filename) {
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/Assets/Field/" + filename + ".bmp").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1050, HEIGHT  , false, false, false, false));
        mainPane.setBackground(new Background(backgroundimage));
        //mainPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

    }

    private void setupHands() {
        playerOneHand.setTranslateX(200);
        playerOneHand.setTranslateY(HEIGHT - 100);
        Board board = gameController.getGameBoard();
        for (int i = 0; i < board.getPlayerOneHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i));
            cardView.setImage(false, false);
            cardView.setOnMouseEntered(event -> {
                imageCard.getChildren().clear();
                imageCard.getChildren().add(new Rectangle(300, 400, cardView.getImage()));
                cardView.setScaleX(1.2);
                cardView.setScaleY(1.2);
                StackPane cardText = (StackPane) cardInformation.getContent();
                cardText.getChildren().clear();
                if (!cardView.isHidden()) {
                    Text text = new Text();
                    text.setFont(Font.font(20));
                    text.setWrappingWidth(300);
                    text.setText(cardView.getCard().getDescription());
                    cardText.getChildren().add(text);
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
        progressBar.setProgress(playerOneLp * 1.0 / 8000);
        VBox vBox = new VBox();
        vBox.getChildren().add(text);
        vBox.getChildren().add(progressBar);
        playerOneHealthBar.getChildren().add(vBox);

        int playerTwoLp = gameController.getPlayerTwoLp();
        text = new Text();
        text.setText("LP : " + playerTwoLp);
        progressBar = new ProgressBar();
        progressBar.setProgress(playerTwoLp * 1.0 / 8000);
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
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
