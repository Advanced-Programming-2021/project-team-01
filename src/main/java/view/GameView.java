package view;

import controller.DatabaseController;
import controller.GameController;
import controller.RegisterController;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Board;
import model.ZoneSlot;
import model.card.Card;
import model.card.CardLocation;


public class GameView {
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1366;
    static StackPane imageCard;
    static ScrollPane cardInformation;
    StackPane playerOneHealthBar;
    StackPane playerTwoHealthBar;
    StackPane drawZonePlayer1;
    StackPane drawZonePlayer2;
    GridPane playerOneHand;
    GridPane playerTwoHand;
    StackPane mainPane;
    GameController gameController;
    GridPane playerOneCardsInBoard;
    GridPane playerTwoCardsInBoard;

    private static GameView instance;

    public static GameView getInstance() {
        if (instance == null) instance = new GameView();
        return instance;
    }

    public static Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

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
            imageCard.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            cardInformation = new ScrollPane();
            cardInformation.setTranslateY(470);
            cardInformation.prefHeight(133);
            cardInformation.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            StackPane cardText = createStackPane(300, 133, 0, 0);
            cardText.setId("cardText");
            cardInformation.setContent(cardText);
            playerOneHand = new GridPane();
            playerOneHand.setTranslateX(500);
            playerTwoHand = new GridPane();
            playerTwoHand.setTranslateX(500);
            playerOneCardsInBoard = new GridPane();
            playerTwoCardsInBoard = new GridPane();
            mainPane = new StackPane();
            setZoneInBoard();
            setupDrawPhase();
            setupGamePane();
            playerTwoHand.setTranslateY(HEIGHT);
            BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/view/card_information.png").toExternalForm()),
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(300, 133, false, false, false, false));
            cardText.setBackground(new Background(backgroundimage));
            setBackgroundImage(imageCard, "/Cards/Unknown.jpg", 300, 400);
            setBackgroundImage(mainPane, "/Assets/Field/fie_normal.bmp", 1050, HEIGHT);
            setBackgroundImage(playerOneHealthBar, "/view/lp_background.png", 300, 70);
            setBackgroundImage(playerTwoHealthBar, "/view/lp_background.png", 300, 70);
            mainPane.setTranslateX(300);
            mainPane.setPrefWidth(WIDTH - 300);
            mainPane.setPrefHeight(HEIGHT);
            mainPane.getChildren().addAll(playerOneCardsInBoard, playerTwoCardsInBoard, playerOneHand, playerTwoHand);
            setupHealthBar();
            setupHands();
            root.getChildren().addAll(mainPane, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setupDrawPhase() {
        ObservableList<Card> playerOneDrawZone = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerDrawZone(1);
        ObservableList<Card> playerTwoDrawZone = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerDrawZone(2);
        Label draw1 = new Label(String.valueOf(playerOneDrawZone.size()));
        Label draw2 = new Label(String.valueOf(playerTwoDrawZone.size()));
        draw1.setFont(Font.font("Tahoma", 12));
        draw1.setTextFill(Color.WHEAT);
        draw2.setFont(Font.font("Tahoma", 12));
        draw2.setTextFill(Color.WHEAT);
        Image image1 = new Image("/Assets/draw.PNG");
        Image image2 = new Image("/Assets/draw2.PNG");
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitHeight(100);
        imageView1.setFitWidth(70);
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(70);
        imageView2.setFitHeight(100);
        drawZonePlayer1 = new StackPane(imageView1, draw1);
        drawZonePlayer2 = new StackPane(imageView2, draw2);
        playerOneDrawZone.addListener((ListChangeListener<Card>) c -> {
            draw1.setText(String.valueOf(playerOneDrawZone.size()));
            while (c.next()) {
                playerOneHand.addRow(1, new CardView(c.getRemoved().get(0), 1, false, false));
            }
            //TODO: drawAnimations
        });
        playerTwoDrawZone.addListener((ListChangeListener<Card>) c -> {
            draw2.setText(String.valueOf(playerTwoDrawZone.size()));
            while (c.next()) {
                playerTwoHand.addRow(1, new CardView(c.getRemoved().get(0), 1, false, true));
            }
            //TODO: drawAnimations
        });
        drawZonePlayer1.setTranslateX(430);
        drawZonePlayer1.setTranslateY(200);
        drawZonePlayer2.setTranslateY(-220);
        drawZonePlayer2.setTranslateX(-430);
        mainPane.getChildren().addAll(drawZonePlayer1, drawZonePlayer2);
        Button button = new Button("ERFAN VA DADBEH");
        button.setOnMouseClicked(event -> {
            gameController.decreasePlayerLP(1, 1000);
//            playerOneDrawZone.remove(0);
//            playerTwoDrawZone.remove(0);
        });
        mainPane.getChildren().add(button);
        button.setTranslateX(-500);
    }

    private void setZoneInBoard() {
        playerOneCardsInBoard.setTranslateY(120);
        playerOneCardsInBoard.setTranslateX(220);
        playerOneCardsInBoard.setVgap(25);
        playerOneCardsInBoard.setHgap(70);

        playerTwoCardsInBoard.setTranslateY(385);
        playerTwoCardsInBoard.setTranslateX(220);
        playerTwoCardsInBoard.setVgap(25);
        playerTwoCardsInBoard.setHgap(70);

    }

    private void setupGamePane() {
        fillZones(playerOneCardsInBoard);
        fillZones(playerTwoCardsInBoard);
    }

    private void fillZones(GridPane playerCardsInBoard) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                StackPane stackPane = new StackPane();
                stackPane.prefHeight(100);
                stackPane.prefWidth(75);
                stackPane.getChildren().add(new Rectangle(75, 95));
                playerCardsInBoard.add(stackPane, j, i);
            }
        }
    }

    private void setBackgroundImage(Pane pane, String address, int width, int height) {
        Image image = new Image(getClass().getResource(address).toExternalForm());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(width, height, false, false, false, false));
        pane.setBackground(new Background(backgroundimage));
    }

    private void setupHands() {
        playerOneHand.setTranslateX(100);
        playerOneHand.setTranslateY(HEIGHT - 100);
        Board board = gameController.getGameBoard();
        for (int i = 0; i < board.getPlayerOneHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i), 1, false, false);
            playerOneHand.add(cardView, i, 1);
        }
        playerTwoHand.setTranslateX(100);
        playerTwoHand.setTranslateY(-100);
        for (int i = 0; i < board.getPlayerTwoHand().size(); i++) {
            CardView cardView = new CardView(board.getPlayerOneHand().get(i), 2, true, true);
            playerTwoHand.add(cardView, i, 1);
        }
    }

    public void setupHealthBar() {
        IntegerProperty playerOneLp = gameController.getPlayerOneLp();
        setupHealthBarPlayer(playerOneLp, playerOneHealthBar);
        IntegerProperty playerTwoLp = gameController.getPlayerTwoLp();
        setupHealthBarPlayer(playerTwoLp, playerTwoHealthBar);
    }

    private void setupHealthBarPlayer(IntegerProperty playerLp, StackPane playerHealthBar) {
        Text textLp = new Text();
        textLp.setId("lpText");

        Text textString = new Text();
        textString.setId("lpText");
        textString.setText("LP : ");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(textString, textLp);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(250);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox, progressBar);

        textLp.textProperty().bind(playerLp.asString());
        progressBar.progressProperty().bind(playerLp.divide(1.0 * 8000));

        playerHealthBar.getChildren().add(vBox);
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

    public void observeZoneSlot(ZoneSlot zoneSlot,boolean isAdded) {
        ZoneLocation zoneLocation = GameController.getInstance().getGameBoard().getZoneLocation(zoneSlot);
        int index = zoneLocation.getIndex();
        int playerNumber = zoneLocation.getPlayerNumber();
        CardLocation cardLocation = zoneLocation.getCardLocation();
        if (cardLocation == CardLocation.MONSTER){
            if (playerNumber == 1){
                if (isAdded){
                    System.out.println("hi");

                }

            }
        }
    }
}
