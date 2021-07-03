package view;

import controller.DatabaseController;
import controller.GameController;
import controller.RegisterController;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private static GameView instance;
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
    MediaPlayer mediaPlayer;
    boolean isPlaying = false;

    private GameView() {

    }

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

    public static void setCard() {
        try {
            GameController.getInstance().setCard();
            System.out.println("set successfully");
        } catch (Exception exp) {
            MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, exp.getMessage());
            myAlert.show();
        }
    }

    public static void flipSummonCard() {
        try {
            GameController.getInstance().flipSummon();
            System.out.println("flip summoned successfully");
        } catch (Exception exp) {
            MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, exp.getMessage());
            myAlert.show();
        }
    }

    public static void summonCard() {
        try {
            GameController.getInstance().summon();
            System.out.println("summoned successfully");
        }
        //TODO ino graphici konim
//        catch (LevelFiveException exception) {
//            String input = HandleRequestType.scanner.nextLine();
//            if (input.equals("cancel")) return;
//            int index = Integer.parseInt(input);
//            try {
//                GameController.getInstance().tributeSummonLevel5(index);
//                System.out.println("summoned successfully");
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        } catch (LevelSevenException levelSevenException) {
//            String input = HandleRequestType.scanner.nextLine();
//            if (input.equals("cancel")) return;
//            int index1 = Integer.parseInt(input);
//            int index2 = Integer.parseInt(HandleRequestType.scanner.nextLine());
//            try {
//                GameController.getInstance().tributeSummonLevel7(index1, index2);
//                System.out.println("summoned successfully");
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
        catch (Exception exp) {
            MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, exp.getMessage());
            myAlert.show();
        }
    }

    public static void activateSpellCard() {
        try {
            GameController.getInstance().activateEffect();
            System.out.println("spell activated");
        } catch (Exception error) {
            MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, error.getMessage());
            myAlert.show();
        }
    }

    public static void changeCardPosition() {
        try {
            String newPosition;
            if (GameController.getInstance().getZoneSlotSelectedCard().isDefending())
                newPosition = "attack";
            else
                newPosition = "defense";
            GameController.getInstance().changeCardPosition(newPosition);
            System.out.println("monster card position changed successfully");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    public void init(Pane root) {
        try {
            root.getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());
            gameController = GameController.getInstance();
            RegisterController.onlineUser = DatabaseController.getUserByName("ali");
            GameController.getInstance().startGame("username", 1);
            //TODO: delete down
            gameController.nextPhase();
            gameController.nextPhase();
            //TODO delete up
            //setupMusic();
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
            mainPane.getChildren().addAll(playerOneHand, playerTwoHand, playerOneCardsInBoard, playerTwoCardsInBoard);
//            mainPane.getChildren().addAll(playerOneHand, playerTwoHand);
            setupHealthBar();
            setupHands();
            setupHandObservables();
            root.getChildren().addAll(mainPane, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setupHandObservables() {
        ObservableList<Card> playerOneLogicHand = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerHand(1);
        ObservableList<Card> playerTwoLogicHand = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerHand(2);
        listenOnHand(playerOneLogicHand, playerOneHand);
        listenOnHand(playerTwoLogicHand, playerTwoHand);
        Button button = new Button("Click Me");
        button.setOnMouseClicked(event -> {
            for (int i = 0; i < 12; i++) {
                try {
                    gameController.nextPhase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mainPane.getChildren().add(button);

    }

    private void listenOnHand(ObservableList<Card> logicHand, GridPane playerHand) {
        logicHand.addListener((ListChangeListener<Card>) c -> {
            while (c.next()) {
                for (Card card : c.getRemoved()) {
                    for (int i = 0; i < playerHand.getChildren().size(); i++) {
                        CardView cardView = (CardView) playerHand.getChildren().get(i);
                        if (cardView.getCard().getName().equals(card.getName())) {
                            playerHand.getChildren().remove(cardView);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setupMusic() {
        Media media = new Media(getClass().getResource("/Assets/Music/main.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        isPlaying = true;
        mediaPlayer.play();
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
//        Button button = new Button("ERFAN VA DADBEH");
//        button.setOnMouseClicked(event -> {
//            try {
//                for (int i = 0; i < 12; i++)
//                    gameController.nextPhase();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        mainPane.getChildren().add(button);
//        button.setTranslateX(-500);
    }

    private void setZoneInBoard() {

        playerOneCardsInBoard.setTranslateY(133);
        playerOneCardsInBoard.setTranslateX(10);
        playerOneCardsInBoard.setMaxWidth(680);
        playerOneCardsInBoard.setMaxHeight(220);
        playerOneCardsInBoard.setAlignment(Pos.CENTER);
        playerOneCardsInBoard.setVgap(25);
        playerOneCardsInBoard.setHgap(40);


        playerTwoCardsInBoard.setTranslateY(-130);
        playerTwoCardsInBoard.setTranslateX(10);
        playerTwoCardsInBoard.setMaxWidth(680);
        playerTwoCardsInBoard.setMaxHeight(220);
        playerTwoCardsInBoard.setAlignment(Pos.CENTER);
        playerTwoCardsInBoard.setVgap(25);
        playerTwoCardsInBoard.setHgap(40);

    }

    private void setupGamePane() {
        fillZones(playerOneCardsInBoard);
        fillZones(playerTwoCardsInBoard);
    }

    private void fillZones(GridPane playerCardsInBoard) {
        playerCardsInBoard.getColumnConstraints().clear();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                StackPane stackPane = new StackPane();
                stackPane.maxHeight(100);
                stackPane.prefHeight(100);
                stackPane.maxWidth(100);
                stackPane.prefWidth(100);
                stackPane.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
                GridPane.setRowIndex(stackPane, i);
                GridPane.setColumnIndex(stackPane, j);
                playerCardsInBoard.getChildren().add(stackPane);
//                playerCardsInBoard.add(stackPane,j,i);
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

    public void observeZoneSlot(ZoneSlot zoneSlot, Action action) {
        ZoneLocation zoneLocation = GameController.getInstance().getGameBoard().getZoneLocation(zoneSlot);
        int index = zoneLocation.getIndex();
        int playerNumber = zoneLocation.getPlayerNumber();
        CardLocation cardLocation = zoneLocation.getCardLocation();
        switch (action) {
            case SET: {
                if (cardLocation == CardLocation.SPELL) {
                    setSpell(index, playerNumber, zoneSlot.getCard());
                } else if (cardLocation == CardLocation.MONSTER) {
                    setMonster(index, playerNumber, zoneSlot.getCard());
                } else if (cardLocation == CardLocation.FIELD) {
                    setField(playerNumber);
                }
                break;
            }
            case SUMMON: {
                summonMonsterCard(playerNumber, index, zoneSlot.getCard());
                break;
            }
            case FLIP_SUMMON: {
                flipSummon(playerNumber, index);
                break;
            }
            case ACTIVATE_SPELL: {
                activateSpell(playerNumber, index);
                break;
            }
            case CHANGE_POSITION: {
                changePosition(playerNumber, index);
                break;
            }
            case REMOVE_FROM_ZONE: {
                removeFromPlayerZone(cardLocation, playerNumber, index);
                break;
            }
            case FLIP:
                flipCard(playerNumber, index);
                break;
        }

    }

    private void flipCard(int playerNumber, int index) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, false);
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, true);
        }
    }

    private void removeFromPlayerZone(CardLocation cardLocation, int playerNumber, int index) {
        if (playerNumber == 1) {
            if (cardLocation == CardLocation.MONSTER) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            } else if (cardLocation == CardLocation.SPELL) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerOneCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            }
        } else {
            if (cardLocation == CardLocation.MONSTER) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerTwoCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            } else if (cardLocation == CardLocation.SPELL) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerTwoCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            }
        }

    }

    private void changePosition(int playerNumber, int index) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            if (gameController.getZoneSlotSelectedCard().isDefending()) {
                cardView.setViewLocation(ViewLocation.MONSTER_DEFENSIVE);
                cardView.setRotate(90);
            } else {
                cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
                cardView.setRotate(0);
            }
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.SPELL_ACTIVATED);
        }
    }

    private void activateSpell(int playerNumber, int index) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.SPELL_ACTIVATED);
            cardView.setImage(false, false);

        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.SPELL_ACTIVATED);
            cardView.setImage(false, true);
        }
    }

    private void flipSummon(int playerNumber, int index) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, false);
            cardView.setRotate(0);
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, true);
            cardView.setRotate(0);
        }
    }

    private void summonMonsterCard(int playerNumber, int index, Card card) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, false, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            zone.getChildren().add(cardView);
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, false, true);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            zone.getChildren().add(cardView);
        }
    }

    private void setField(int playerNumber) {


    }

    private void setMonster(int index, int playerNumber, Card card) {
        if (playerNumber == 1) {
            fillMonsterZone(index, playerNumber, card, playerOneCardsInBoard);
        } else {
            fillMonsterZone(index, playerNumber, card, playerTwoCardsInBoard);
        }
    }

    private void fillMonsterZone(int index, int playerNumber, Card card, GridPane playerOneCardsInBoard) {
        StackPane zone = (StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard);
        assert zone != null;
        zone.getChildren().clear();
        CardView cardView = new CardView(card, playerNumber, true, false);
        cardView.setToBoard();
        cardView.setViewLocation(ViewLocation.MONSTER_DEFENSIVE);
        zone.getChildren().add(cardView);
        cardView.setRotate(90);
    }

    private void setSpell(int index, int playerNumber, Card card) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerOneCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.SPELL_HIDDEN);
            zone.getChildren().add(cardView);
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, true, true);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.SPELL_HIDDEN);
            zone.getChildren().add(cardView);
        }
    }
}
