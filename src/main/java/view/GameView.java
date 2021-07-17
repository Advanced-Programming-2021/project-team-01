package view;

import Network.Client.Client;
import Network.Requests.Battle.BattleActionRequest;
import Network.Requests.Battle.SendNeededCardsRequest;
import Network.Responses.Battle.GetNeededCardResponse;
import Network.Responses.Response;
import Network.Utils.Logger;
import controller.GameController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Board;
import model.GamePhase;
import model.ZoneSlot;
import model.card.*;
import model.networkLocators.BattleAction;
import model.networkLocators.BattleState;
import model.networkLocators.CheatBattleAction;
import view.transions.*;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class GameView implements GraphicalView {
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1366;
    static StackPane imageCard;
    static ScrollPane cardInformation;
    private static GameView instance;
    ImageView phase;
    ImageView nextPhaseButton;
    StackPane playerOneHealthBar;
    StackPane playerTwoHealthBar;
    StackPane drawZonePlayer1;
    StackPane drawZonePlayer2;
    StackPane playerFieldZone1;
    StackPane playerFieldZone2;
    StackPane graveyardPlayer1;
    StackPane graveyardPlayer2;
    StackPane profileDetails1;
    StackPane profileDetails2;
    GridPane playerOneHand;
    GridPane playerTwoHand;
    VBox nameOfPhases;
    StackPane mainPane;
    GameController gameController;
    GridPane playerOneCardsInBoard;
    GridPane playerTwoCardsInBoard;
    MediaPlayer mediaPlayer;
    MediaPlayer piecePlayer;
    CardView targetCard;
    boolean isPlaying = true;
    boolean isAttacking = false;
    int i = 1;
    private Response currentResponse;

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

    public static void flipSummonCard() {
        try {
            GameController.getInstance().flipSummon();
            System.out.println("flip summoned successfully");
        } catch (Exception exp) {
            new MyAlert(Alert.AlertType.ERROR, exp.getMessage()).show();
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
            MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, exp.getMessage());
            myAlert.show();
        }
    }

    public static GraveyardPopUp showListOfCards(List<Card> cards) {
        return new GraveyardPopUp(cards);
    }

    public static List<Card> getNeededCards(List<Card> cards, int number) {
        if (GameController.getInstance().controllerNumber == GameController.getInstance().getCurrentPlayerNumber()) {
            SelectCardDialog selectCardDialog = new SelectCardDialog(cards, number);
            GameController.getInstance().getSelectedCard().lock();
            selectCardDialog.showAndWait();
            selectCardDialog.close();
            GameController.getInstance().getSelectedCard().unlock();
            SendNeededCardsRequest request = new SendNeededCardsRequest(selectCardDialog.getSelectedCards(), GameController.getOpponent().getUsername());
            Client.getInstance().sendData(request.toString());
            return selectCardDialog.getSelectedCards();
        } else {
            GetNeededCardResponse response = (GetNeededCardResponse) GameView.getInstance().getCurrentResponse();
            return ((SendNeededCardsRequest) response.getRequest()).getNeededCards();
        }
    }

    public static String getChoiceBox(List<String> items, String header) {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setHeaderText(header);
        choiceDialog.getItems().addAll(items);
        choiceDialog.showAndWait();
        return choiceDialog.getSelectedItem();
    }

    public static void showAlert(String message) {
        new MyAlert(Alert.AlertType.INFORMATION, message).show();
    }

    public static String getChoiceBox(String header, String... items) {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setHeaderText(header);
        choiceDialog.getItems().addAll(items);
        choiceDialog.showAndWait();
        return choiceDialog.getSelectedItem();
    }

    public static boolean getYesOrNo(String prompt) {
        YesNoDialog yesNoDialog = new YesNoDialog(prompt);
        yesNoDialog.showAndWait();
        return yesNoDialog.getResult();
    }

    public static List<Card> selectedCardsWithSelectableDialog(List<Card> cards) {
        GameController.getInstance().getSelectedCard().lock();
        SelectableDialog selectableDialog = new SelectableDialog(cards);
        selectableDialog.showAndWait();
        GameController.getInstance().getSelectedCard().unlock();
        return selectableDialog.getResult();
    }

    public void specialSummon() {
        Board board = gameController.getGameBoard();
        int player = gameController.getCurrentPlayerNumber();
        try {
            BattleAction battleAction = new BattleAction(BattleState.SPECIAL_SUMMON, gameController.getSelectedCard().getCardLocation(),
                    board.getIndexOfCard(gameController.getSelectedCard()), player);
            sendRequest(battleAction);
//            GameController.getInstance().activateEffect();
//            System.out.println("spell activated");
//            GameController.getInstance().getGameBoard().showBoard();
        } catch (Exception error) {
            new MyAlert(Alert.AlertType.ERROR, error.getMessage()).show();
        }
    }

    public Response getCurrentResponse() {
        return currentResponse;
    }

    public void setCurrentResponse(Response currentResponse) {
        this.currentResponse = currentResponse;
    }

    public void init(Pane root) {
        try {
            if (GameController.getInstance().controllerNumber == 2) i = -1;
            else i = 1;
            root.getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());
            gameController = GameController.getInstance();
//            RegisterController.onlineUser = DatabaseController.getUserByName("ali"); //TODO: used for testing
//            GameController.getInstance().startGame("username", 1);
            resetPanes();
            setupPiecePlayer();
            setupMusic();
            setupImageCard();
            StackPane cardText = setupCardInformation();
            setupProfile();
            setZoneInBoard();
            setupGraveYard();
            setupDrawPhase();
            setupGamePane();
            setupFieldZone();
            setupBackgroundImages(cardText);
            setMainPaneSize();
            setupHealthBar();
            setupPhaseButtons();
            setupHands();
            setupEndGameCondition();
            setupPhaseNames();
            mainPane.getChildren().addAll(nameOfPhases, playerOneHand, playerTwoHand, playerOneCardsInBoard, playerTwoCardsInBoard,
                    profileDetails1, profileDetails2, playerFieldZone1, playerFieldZone2, graveyardPlayer1, graveyardPlayer2);
            setupHandObservables();
            root.getChildren().addAll(mainPane, playerOneHealthBar, playerTwoHealthBar, imageCard, cardInformation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void summonCard() {
        Board board = GameController.getInstance().getGameBoard();
        int player = GameController.getInstance().getCurrentPlayerNumber();
        try {
            BattleAction battleAction = new BattleAction(BattleState.SUMMON, CardLocation.HAND,
                    board.getIndexOfCard(GameController.getInstance().getSelectedCard()),
                    GameController.getInstance().getCurrentPlayerNumber());
            sendRequest(battleAction);
//        } catch (LevelFiveException exception) {
//            try {
//                if (board.numberOfMonsterCards(player) == 0)
//                    throw new Exception("you have not monster");
//                List<Card> cardsInMonsterZone = board.getCardInMonsterZone(player);
//                Card card = getNeededCards(cardsInMonsterZone, 1).get(0);
//                GameController.getInstance().tributeSummonLevel5(card);
//                System.out.println("summoned successfully");
//            } catch (Exception e) {
//                MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, e.getMessage());
//                myAlert.show();
//            }
//        } catch (LevelSevenException levelSevenException) {
//            try {
//                if (board.numberOfMonsterCards(player) < 2)
//                    throw new Exception("you have not enough monsters");
//                List<Card> cardsInMonsterZone = board.getCardInMonsterZone(player);
//                List<Card> selectedCards = getNeededCards(cardsInMonsterZone, 2);
//                GameController.getInstance().tributeSummonLevel7(selectedCards.get(0), selectedCards.get(1));
//                System.out.println("summoned successfully");
//            } catch (Exception e) {
//                MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, e.getMessage());
//                myAlert.show();
//            }
        } catch (Exception exp) {
            MyAlert myAlert = new MyAlert(Alert.AlertType.ERROR, exp.getMessage());
            myAlert.show();
        }

    }

    public void activateSpellCard() {
        Board board = gameController.getGameBoard();
        int player = gameController.getCurrentPlayerNumber();
        try {
            BattleAction battleAction = new BattleAction(BattleState.ACTIVATE_SPELL, gameController.getSelectedCard().getCardLocation(),
                    board.getIndexOfCard(gameController.getSelectedCard()), player);
            sendRequest(battleAction);
//            GameController.getInstance().activateEffect();
//            System.out.println("spell activated");
//            GameController.getInstance().getGameBoard().showBoard();
        } catch (Exception error) {
            new MyAlert(Alert.AlertType.ERROR, error.getMessage()).show();
        }
    }

    public void setCard() {
        Board board = gameController.getGameBoard();
        int player = GameController.getInstance().getCurrentPlayerNumber();
        try {
            BattleAction battleAction = new BattleAction(BattleState.SET_SPELL, CardLocation.HAND,
                    board.getIndexOfCard(gameController.getSelectedCard()), player);
            sendRequest(battleAction);
//            GameController.getInstance().setCard();
//            GameController.getInstance().getGameBoard().showBoard();
//            System.out.println("set successfully");
        } catch (Exception exp) {
            new MyAlert(Alert.AlertType.ERROR, exp.getMessage()).show();
        }
    }


    public void showDirectAttack() {
        Image image = new Image(getClass().getResource("/view/2.gif").toExternalForm());
        ImageView imageView = new ImageView(image);
        mainPane.getChildren().add(imageView);
        imageView.setFitWidth(700);
        imageView.setFitHeight(500);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), event -> {
            mainPane.getChildren().remove(imageView);
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    public void attackOnCard() {
//        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), targetCard);
//        translateTransition.setByX(15);
//        translateTransition.setCycleCount(8);
//        translateTransition.setAutoReverse(true);
        Card card = targetCard.getCard();
        ZoneSlot[] monsterZone1 = GameController.getInstance().getGameBoard().getPlayerOneMonsterZone();
        ZoneSlot[] monsterZone2 = GameController.getInstance().getGameBoard().getPlayerTwoMonsterZone();
        int index = -1;
        for (int i = 1; i < 6; i++) {
            if (monsterZone1[i].getCard() == card) {
                index = i;
            }
        }
        for (int i = 1; i < 6; i++) {
            if (monsterZone2[i].getCard() == card) {
                index = i;
            }
        }
        BattleAction battleAction = new BattleAction(BattleState.ATTACK, CardLocation.MONSTER, index,
                GameController.getInstance().getCurrentPlayerNumber());
        sendRequest(battleAction);
    }

    private void resetPanes() {
        mainPane = new StackPane();
        playerOneCardsInBoard = new GridPane();
        playerTwoCardsInBoard = new GridPane();
    }

    private void setupPhaseNames() {
        nameOfPhases = new VBox();
        nameOfPhases.setMaxHeight(520);
        nameOfPhases.setMaxWidth(30);

        Rectangle drawPhaseImage = new Rectangle(30, 50, Color.WHITE);
        drawPhaseImage.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/phase/draw.png").toExternalForm())));
        drawPhaseImage.getStyleClass().add("butoo");

        Rectangle standbyPhaseImage = new Rectangle(30, 50, Color.WHITE);
        standbyPhaseImage.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/phase/standby.png").toExternalForm())));

        Rectangle mainPhase1Image = new Rectangle(30, 50, Color.WHITE);
        mainPhase1Image.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/phase/main1.png").toExternalForm())));

        Rectangle battlePhaseImage = new Rectangle(30, 50, Color.WHITE);
        battlePhaseImage.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/phase/battle.png").toExternalForm())));

        Rectangle mainPhase2Image = new Rectangle(30, 50, Color.WHITE);
        mainPhase2Image.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/phase/main2.png").toExternalForm())));

        Rectangle endPhaseImage = new Rectangle(30, 50, Color.WHITE);
        endPhaseImage.setFill(new ImagePattern(new Image(getClass().getResource("/Assets/phase/end.png").toExternalForm())));

        nameOfPhases.setSpacing(40);
        nameOfPhases.getChildren().addAll(drawPhaseImage, standbyPhaseImage, mainPhase1Image, battlePhaseImage, mainPhase2Image, endPhaseImage);
        nameOfPhases.setTranslateX(-509);
        nameOfPhases.setTranslateY(10);

    }

    private void setupPiecePlayer() {
        Media media = new Media(getClass().getResource("/Assets/sound/timeto.mp3").toExternalForm());
        piecePlayer = new MediaPlayer(media);
        piecePlayer.setOnEndOfMedia(piecePlayer::stop);
        piecePlayer.play();
    }

    private void setupEndGameCondition() {
        IntegerProperty playerOneLp = GameController.getInstance().getPlayerOneLp();
        IntegerProperty playerTwoLp = GameController.getInstance().getPlayerTwoLp();
        playerOneLp.addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0) {
                try {
                    GameController.getInstance().finishGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        playerTwoLp.addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 0) {
                try {
                    GameController.getInstance().finishGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupFieldZone() {
        playerFieldZone1 = createStackPane(100, 120, i > 0 ? -430 : 460, i > 0 ? 60 : -75);
        playerFieldZone2 = createStackPane(100, 120, i > 0 ? 460 : -430, i > 0 ? -75 : 60);
    }

    private void setupImageCard() {
        imageCard = createStackPane(300, 400, 0, 70);
        imageCard.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void setupBackgroundImages(StackPane cardText) {
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/view/card_information.png").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(300, 133, false, false, false, false));
        cardText.setBackground(new Background(backgroundimage));
        setBackgroundImage(imageCard, "/Cards/Unknown.jpg", 300, 400);
        setBackgroundImage(mainPane, "/Assets/Field/fie_normal.bmp", 1050, HEIGHT);
    }

    private void setMainPaneSize() {
        mainPane.setTranslateX(300);
        mainPane.setPrefWidth(WIDTH - 300);
        mainPane.setPrefHeight(HEIGHT);
    }

    private StackPane setupCardInformation() {
        cardInformation = new ScrollPane();
        cardInformation.setTranslateY(470);
        cardInformation.prefHeight(133);
        cardInformation.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        StackPane cardText = createStackPane(300, 133, 0, 0);
        cardText.setId("cardText");
        cardInformation.setContent(cardText);
        return cardText;
    }

    private void setupGraveYard() {
        graveyardPlayer1 = createStackPane(70, 80, 430, 70);
        graveyardPlayer2 = createStackPane(70, 80, -420, -75);
        setBackgroundImage(graveyardPlayer1, "/view/graveyardIcon.png", 70, 80);
        setBackgroundImage(graveyardPlayer2, "/view/graveyardIcon.png", 70, 80);
        graveyardPlayer2.setRotate(180);
        ObservableList<Card> playerOneGraveYard = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerOneGraveYard();
        ObservableList<Card> playerTwoGraveYard = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerTwoGraveYard();
        graveyardPlayer1.setOnMouseClicked(event -> {
            if (playerOneGraveYard.isEmpty()) {
                MyAlert myAlert = new MyAlert(Alert.AlertType.INFORMATION, "Graveyard is empty.");
                myAlert.show();
                return;
            }
            GraveyardPopUp popup = showListOfCards(playerOneGraveYard);
            gameController.getSelectedCard().lock();
            popup.show(ViewSwitcher.getStage());
        });
        graveyardPlayer2.setOnMouseClicked(event -> {
            if (playerTwoGraveYard.isEmpty()) {
                MyAlert myAlert = new MyAlert(Alert.AlertType.INFORMATION, "Graveyard is empty.");
                myAlert.show();
                return;
            }
            GraveyardPopUp popup = showListOfCards(playerTwoGraveYard);
            gameController.getSelectedCard().lock();
            popup.show(ViewSwitcher.getStage());
        });
    }

    private void setupPhaseButtons() {
        Image image = new Image(getClass().getResource("/view/nextt.png").toExternalForm());
        nextPhaseButton = new ImageView(image);
        nextPhaseButton.getStyleClass().add("next");
        nextPhaseButton.setFitHeight(50);
        nextPhaseButton.setFitWidth(80);
        nextPhaseButton.setTranslateX(-480);
        nextPhaseButton.setTranslateY(-330);
        phase = new ImageView();
        nextPhaseButton.setOnMouseClicked(event -> {
            BattleAction battleAction = new BattleAction(BattleState.NEXT_PHASE, CardLocation.NONE, 0,
                    GameController.getInstance().getCurrentPlayerNumber());
            sendRequest(battleAction);
        });
        mainPane.getChildren().addAll(nextPhaseButton, phase);

    }

    private void clickOnNextPhase() {
        GameController gameController = GameController.getInstance();
        try {
            gameController.nextPhase();
            setNextPhaseImage();
        } catch (Exception e) {
            new MyAlert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void sendRequest(BattleAction battleAction) {
        try {
            doAction(battleAction);
            BattleActionRequest request = new BattleActionRequest(Client.getInstance().getToken(),
                    GameController.getOpponent().getUsername(), battleAction);
            Client.getInstance().sendData(request.toString());
        } catch (Exception e) {
            new MyAlert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            if (battleAction.getBattleState() == BattleState.ATTACK) {
                targetCard = null;
                isAttacking = false;
                GameController.getInstance().getSelectedCard().unlock();
                GameController.getInstance().getGameBoard().showBoard();
            }
        }

    }

    public void doAction(BattleAction battleAction) throws Exception {
        switch (battleAction.getBattleState()) {
            case NEXT_PHASE:
                clickOnNextPhase();
                break;
            case SUMMON:
                summonAction(battleAction);
                break;
            case ATTACK:
                attackAction(battleAction);
                break;
            case CHEAT:
                cheat(battleAction);
                break;
            case SET_SPELL:
                setSpellAction(battleAction);
                break;
            case ACTIVATE_SPELL:
                activateSpellAction(battleAction);
                break;
            case SPECIAL_SUMMON:
                specialSummonAction(battleAction);
                break;
        }
    }

    private void specialSummonAction(BattleAction battleAction) throws Exception {
        SelectedCard selectedCard = GameController.getInstance().getSelectedCard();
        selectedCard.set(GameController.getInstance().getGameBoard().getCard(battleAction.getCardLocation(), battleAction.getIndex(), battleAction.getPlayerNumber()));
        GameController.getInstance().activateEffect();
    }

    private void activateSpellAction(BattleAction battleAction) throws Exception {
        SelectedCard selectedCard = GameController.getInstance().getSelectedCard();
        selectedCard.set(GameController.getInstance().getGameBoard().getCard(battleAction.getCardLocation(), battleAction.getIndex(), battleAction.getPlayerNumber()));
        GameController.getInstance().activateEffect();
    }

    private void setSpellAction(BattleAction battleAction) throws Exception {
        SelectedCard selectedCard = GameController.getInstance().getSelectedCard();
        selectedCard.set(GameController.getInstance().getGameBoard().getPlayerHand(battleAction.getPlayerNumber()).get(battleAction.getIndex()));
        selectedCard.setIndex(battleAction.getIndex());
        selectedCard.setPlayer((battleAction.getPlayerNumber() == 1) ? GameController.getPlayerOne() : GameController.getPlayerTwo());
        gameController.setCard();
    }


    private void cheat(BattleAction battleAction) throws Exception {
        String cardName = ((CheatBattleAction) battleAction).getCardName();
        GameController.getInstance().cheater(cardName);
    }

    private void attackAction(BattleAction battleAction) throws Exception {
        SelectedCard selectedCard = GameController.getInstance().getSelectedCard();
        selectedCard.set(GameController.getInstance().getGameBoard().getCardFromMonsterZone(battleAction.getIndex(), battleAction.getPlayerNumber()));
        selectedCard.setIndex(battleAction.getIndex());
        selectedCard.setPlayer((battleAction.getPlayerNumber() == 1) ? GameController.getPlayerOne() : GameController.getPlayerTwo());
        String response = GameController.getInstance().attack(battleAction.getIndex());
        System.out.println(response);
        new MyAlert(Alert.AlertType.INFORMATION, response).show();
        MyMusicPlayer.attack();
    }

    private void summonAction(BattleAction battleAction) throws Exception {
        SelectedCard selectedCard = GameController.getInstance().getSelectedCard();
        selectedCard.set(GameController.getInstance().getGameBoard().getPlayerHand(battleAction.getPlayerNumber()).get(battleAction.getIndex()));
        selectedCard.setIndex(battleAction.getIndex());
        selectedCard.setPlayer((battleAction.getPlayerNumber() == 1) ? GameController.getPlayerOne() : GameController.getPlayerTwo());
        GameController.getInstance().summon();
    }

    private void setNextPhaseImage() {
        GamePhase current = GameController.getInstance().getPhaseController().getGamePhase();
        Image image = null;
        switch (current) {
            case END_PHASE: {
                image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Battle/6.png")).toExternalForm());
                setCurrentPhase(5);
                break;
            }
            case DRAW_PHASE: {
                image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Battle/1.png")).toExternalForm());
                setCurrentPhase(0);
                break;
            }
            case STANDBY_PHASE: {
                image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Battle/2.png")).toExternalForm());
                setCurrentPhase(1);
                break;
            }
            case BATTLE_PHASE: {
                image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Battle/4.png")).toExternalForm());
                setCurrentPhase(3);
                break;
            }
            case MAIN_PHASE2: {
                image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Battle/5.png")).toExternalForm());
                setCurrentPhase(4);
                break;
            }
            case MAIN_PHASE1: {
                image = new Image(Objects.requireNonNull(getClass().getResource("/Assets/Battle/3.png")).toExternalForm());
                setCurrentPhase(2);
            }
        }
        phase = new ImageView(image);
        mainPane.getChildren().add(phase);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(phase);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
            fadeTransition.setOnFinished(event1 -> mainPane.getChildren().remove(phase));
        });
    }

    private void setCurrentPhase(int i) {
        Rectangle rectangle;
        for (int j = 0; j < 6; j++) {
            rectangle = (Rectangle) nameOfPhases.getChildren().get(j);
            rectangle.getStyleClass().clear();
        }
        rectangle = (Rectangle) nameOfPhases.getChildren().get(i);
        rectangle.getStyleClass().add("butoo");
    }

    public void drawPhaseGraphicActions() {
        int opponentNumber = GameController.getInstance().getOpponentPlayerNumber();
        if (opponentNumber == 2) {
            for (Node child : playerOneHand.getChildren()) {
                ((CardView) child).setImage(true, false);
            }
            for (Node child : playerTwoHand.getChildren()) {
                ((CardView) child).setImage(false, false);
            }
        } else {
            for (Node child : playerOneHand.getChildren()) {
                ((CardView) child).setImage(false, false);
            }
            for (Node child : playerTwoHand.getChildren()) {
                ((CardView) child).setImage(true, false);
            }
        }
    }

    private void setupProfile() {
        profileDetails1 = new StackPane();
        profileDetails2 = new StackPane();
        profileDetails1.setMaxHeight(170);
        profileDetails1.setMaxWidth(120);
        profileDetails1.setTranslateX(-430);
        profileDetails1.setTranslateY(190);
        VBox vBoxPlayer1 = new VBox();
        setBackgroundImage(vBoxPlayer1, "/view/profileTextBackground.png", 120, 170);
        vBoxPlayer1.setAlignment(Pos.CENTER);
        Text username1Txt = new Text();
        username1Txt.setText(GameController.getPlayerOne().getUsername());
        username1Txt.setId("profileText");
        Text nickname1Txt = new Text();
        nickname1Txt.setText(GameController.getPlayerOne().getNickname());
        nickname1Txt.setId("profileText");
        Rectangle profileImage1 = new Rectangle(50, 50, Color.BLACK);
        vBoxPlayer1.getChildren().addAll(profileImage1, username1Txt, nickname1Txt);
        profileDetails1.getChildren().add(vBoxPlayer1);
        profileDetails2.setMaxWidth(120);
        profileDetails2.setMaxHeight(170);
        profileDetails2.setTranslateX(460);
        profileDetails2.setTranslateY(-195);
        VBox vBoxPlayer2 = new VBox();
        setBackgroundImage(vBoxPlayer2, "/view/profileTextBackground.png", 120, 170);
        vBoxPlayer2.setAlignment(Pos.CENTER);
        Text username2Txt = new Text();
        username2Txt.setText(GameController.getPlayerTwo().getUsername());
        username2Txt.setId("profileText");
        Text nickname2Txt = new Text();
        Image image1 = new Image(getClass().getResource("/Assets/ProfileDatabase/Chara001.dds" + GameController.getPlayerOne().getProfile() + ".png").toExternalForm());
        ImagePattern imagePattern = new ImagePattern(image1);
        profileImage1.setFill(imagePattern);
        nickname2Txt.setText(GameController.getPlayerTwo().getNickname());
        nickname2Txt.setId("profileText");
        Rectangle profileImage2 = new Rectangle(50, 50);
        Image image2 = new Image(getClass().getResource("/Assets/ProfileDatabase/Chara001.dds" + GameController.getPlayerTwo().getProfile() + ".png").toExternalForm());
        ImagePattern imagePattern2 = new ImagePattern(image2);
        profileImage2.setFill(imagePattern2);
        vBoxPlayer2.getChildren().addAll(profileImage2, username2Txt, nickname2Txt);
        profileDetails2.getChildren().add(vBoxPlayer2);
    }

    private void setupHandObservables() {
        ObservableList<Card> playerOneLogicHand = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerHand(1);
        ObservableList<Card> playerTwoLogicHand = (ObservableList<Card>) GameController.getInstance().getGameBoard().getPlayerHand(2);
        listenOnHand(playerOneLogicHand, playerOneHand);
        listenOnHand(playerTwoLogicHand, playerTwoHand);
    }

    private void listenOnHand(ObservableList<Card> logicHand, GridPane playerHand) {
        logicHand.addListener((ListChangeListener<Card>) c -> {
            while (c.next()) {
                for (Card card : c.getRemoved()) {
                    System.err.println(card.getName());
                    for (int i = 0; i < playerHand.getChildren().size(); i++) {
                        CardView cardView = (CardView) playerHand.getChildren().get(i);
                        if (cardView.getCard().getName().equals(card.getName())) {
                            FadeTransition fadeTransition = new FadeTransition();
                            fadeTransition.setNode(cardView);
                            fadeTransition.setDuration(Duration.millis(150));
                            fadeTransition.setFromValue(1);
                            fadeTransition.setToValue(0);
                            fadeTransition.play();
                            fadeTransition.setOnFinished(event -> {
                                System.err.println("called for " + cardView.getCard().getName());
                                playerHand.getChildren().remove(cardView);
                            });
                            break;
                        }
                    }
                }
                for (Card card : c.getAddedSubList()) {
                    CardView cardView;
                    if (playerHand == playerOneHand)
                        cardView = new CardView(card, gameController.getGameBoard().getOwnerOfCard(card), false, false);
                    else
                        cardView = new CardView(card, gameController.getGameBoard().getOwnerOfCard(card), false, true);
                    if (GameController.getInstance().controllerNumber != GameController.getInstance().getCurrentPlayerNumber())
                        cardView.setImage(true, true);
                    playerHand.addRow(1, cardView);
                }
            }
        });
    }

    private void setupMusic() {
        Media media = new Media(getClass().getResource("/Assets/Music/main.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        isPlaying = true;
                        mediaPlayer.setAutoPlay(true);
                        mediaPlayer.play();
                    }
                }, 5000
        );
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
//            while (c.next()) {
//                playerOneHand.addRow(1, new CardView(c.getRemoved().get(0), 1, false, false));
//            }
            //TODO: drawAnimations
        });
        playerTwoDrawZone.addListener((ListChangeListener<Card>) c -> {
            draw2.setText(String.valueOf(playerTwoDrawZone.size()));
//            while (c.next()) {
//                playerTwoHand.addRow(1, new CardView(c.getRemoved().get(0), 2, false, true));
//            }
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

        playerOneCardsInBoard.setTranslateY(133 * i);
        playerOneCardsInBoard.setTranslateX(10);
        playerOneCardsInBoard.setMaxWidth(680);
        playerOneCardsInBoard.setMaxHeight(220);
        playerOneCardsInBoard.setAlignment(Pos.CENTER);
        playerOneCardsInBoard.setVgap(25);
        playerOneCardsInBoard.setHgap(40);


        playerTwoCardsInBoard.setTranslateY(-130 * i);
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

    public void setBackgroundImage(Pane pane, String address, int width, int height) {
        Image image = new Image(getClass().getResource(address).toExternalForm());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(width, height, false, false, false, false));
        pane.setBackground(new Background(backgroundimage));
    }

    private void setupHands() {
        playerOneHand = new GridPane();
        playerOneHand.setTranslateX(500);
        playerTwoHand = new GridPane();
        playerTwoHand.setTranslateX(500);
        playerTwoHand.setTranslateY(HEIGHT);
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
            CardView cardView = new CardView(board.getPlayerTwoHand().get(i), 2, true, true);
            playerTwoHand.add(cardView, i, 1);
        }
    }

    public void setupHealthBar() {
        playerOneHealthBar = createStackPane(300, 70, 0, HEIGHT - 117);
        playerOneHealthBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        playerTwoHealthBar = createStackPane(300, 70, 0, 0);
        playerTwoHealthBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        setBackgroundImage(playerOneHealthBar, "/view/lp_background.png", 300, 70);
        setBackgroundImage(playerTwoHealthBar, "/view/lp_background.png", 300, 70);
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
                activateSpell(playerNumber, index, zoneSlot.getCard());
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
        FlipAnimation flipAnimation = new FlipAnimation();
        flipAnimation.setUpsideDown(true);
        flipAnimation.setFrontToBack(false);
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            flipAnimation.setNode(cardView);
            flipAnimation.play();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, false);
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            flipAnimation.setNode(cardView);
            flipAnimation.play();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, true);
        }
        MyMusicPlayer.flip();
    }

    private void removeFromPlayerZone(CardLocation cardLocation, int playerNumber, int index) {
        if (playerNumber == 1) {
            if (cardLocation == CardLocation.MONSTER) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 0 : 1, index - 1, playerOneCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            } else if (cardLocation == CardLocation.SPELL) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 1 : 0, index - 1, playerOneCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            } else if (cardLocation == CardLocation.FIELD) {
                playerFieldZone1.getChildren().clear();
                setFieldBackground(gameController.getGameBoard().getPlayerFieldZone(2).getCard());
            }
        } else {
            if (cardLocation == CardLocation.MONSTER) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 1 : 0, index - 1, playerTwoCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            } else if (cardLocation == CardLocation.SPELL) {
                StackPane zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 0 : 1, index - 1, playerTwoCardsInBoard));
                assert zone != null;
                zone.getChildren().clear();
                zone.getChildren().add(new Rectangle(100, 100, Color.TRANSPARENT));
            } else if (cardLocation == CardLocation.FIELD) {
                playerFieldZone2.getChildren().clear();
                setFieldBackground(gameController.getGameBoard().getPlayerFieldZone(1).getCard());
            }
        }
        MyMusicPlayer.destroyMonster();

    }

    private void changePosition(int playerNumber, int index) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(cardView);
            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setDuration(Duration.millis(1000));
            if (gameController.getZoneSlotSelectedCard().isDefending()) {
                cardView.setViewLocation(ViewLocation.MONSTER_DEFENSIVE);
                rotateTransition.setByAngle(90);
            } else {
                cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
                rotateTransition.setByAngle(-90);
            }
            rotateTransition.play();
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(cardView);
            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.setDuration(Duration.millis(1000));
            if (gameController.getZoneSlotSelectedCard().isDefending()) {
                cardView.setViewLocation(ViewLocation.MONSTER_DEFENSIVE);
                rotateTransition.setByAngle(-90);
            } else {
                cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
                rotateTransition.setByAngle(+90);
            }
            rotateTransition.play();
        }
        MyMusicPlayer.flip();
    }

    private void activateSpell(int playerNumber, int index, Card card) {
        if (playerNumber == 1) {
            StackPane zone;
            if (card instanceof SpellCard && ((SpellCard) card).getProperty() == Property.FIELD) {
                zone = playerFieldZone1;
                setFieldBackground(card);
            } else
                zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 1 : 0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.SPELL_ACTIVATED);
            cardView.setImage(false, i < 0);
        } else {
            StackPane zone;
            if (card instanceof SpellCard && ((SpellCard) card).getProperty() == Property.FIELD) {
                zone = playerFieldZone2;
                setFieldBackground(card);
            } else
                zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 0 : 1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            cardView.setViewLocation(ViewLocation.SPELL_ACTIVATED);
            cardView.setImage(false, i > 0);
        }
        MyMusicPlayer.blip();
    }

    private void setFieldBackground(Card card) {
        if (card == null) {
            setBackgroundImage(mainPane, "/Assets/Field/fie_normal.bmp", 1050, HEIGHT);
            return;
        }
        switch (card.getName()) {
            case "Yami":
                setBackgroundImage(mainPane, "/Assets/Field/fie_yami.bmp", 1050, HEIGHT);
                break;
            case "Forest":
                setBackgroundImage(mainPane, "/Assets/Field/fie_mori.bmp", 1050, HEIGHT);
                break;
            case "Closed Forest":
                setBackgroundImage(mainPane, "/Assets/Field/fie_gaia.bmp", 1050, HEIGHT);
                break;
            case "Umiiruka":
                setBackgroundImage(mainPane, "/Assets/Field/fie_umi.bmp", 1050, HEIGHT);
                break;
        }
    }

    private void flipSummon(int playerNumber, int index) {
        RotateTransition rotateTransition = new RotateTransition();
        FlipAnimation flipAnimation = new FlipAnimation();
        rotateTransition.setDuration(Duration.millis(1));
        flipAnimation.setFrontToBack(false);
        flipAnimation.setUpsideDown(false);
        StackPane zone;
        if (playerNumber == 1) {
            zone = ((StackPane) getNodeByRowColumnIndex(0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            rotateTransition.setNode(cardView);
            flipAnimation.setNode(cardView);
            rotateTransition.setByAngle(-90);
            rotateTransition.play();
            flipAnimation.play();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, false);
            cardView.setRotate(0);
        } else {
            zone = ((StackPane) getNodeByRowColumnIndex(1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            CardView cardView = (CardView) zone.getChildren().get(0);
            rotateTransition.setNode(cardView);
            rotateTransition.setByAngle(90);
            rotateTransition.play();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            cardView.setImage(false, true);
            cardView.setRotate(0);
        }
        MyMusicPlayer.flip();
    }

    private void summonMonsterCard(int playerNumber, int index, Card card) {
        StackPane zone;
        if (playerNumber == 1) {
            zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 0 : 1, index - 1, playerOneCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, false, i <= 0);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            zone.getChildren().add(cardView);
        } else {
            zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 1 : 0, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, false, i > 0);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.MONSTER_OFFENSIVE);
            zone.getChildren().add(cardView);
        }
        MyMusicPlayer.spawn();
    }

    private void setField(int playerNumber) {
        if (playerNumber == 1) {
            ZoneSlot fieldZonePlayerOne = GameController.getInstance().getGameBoard().getPlayerFieldZone(1);
            playerFieldZone1.getChildren().clear();
            CardView cardView = new CardView(fieldZonePlayerOne.getCard(), playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.SPELL_HIDDEN);
            playerFieldZone1.getChildren().add(cardView);
        } else {
            ZoneSlot fieldZonePlayerOne = GameController.getInstance().getGameBoard().getPlayerFieldZone(2);
            playerFieldZone2.getChildren().clear();
            CardView cardView = new CardView(fieldZonePlayerOne.getCard(), playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.SPELL_HIDDEN);
            playerFieldZone2.getChildren().add(cardView);
        }
        MyMusicPlayer.setCard();
    }

    private void setMonster(int index, int playerNumber, Card card) {
        if (playerNumber == 1) {
            StackPane zone = (StackPane) getNodeByRowColumnIndex(i > 0 ? 0 : 1, index - 1, playerOneCardsInBoard);
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.MONSTER_DEFENSIVE);
            zone.getChildren().add(cardView);
            cardView.setRotate(90);
        } else {
            StackPane zone = (StackPane) getNodeByRowColumnIndex(i > 0 ? 1 : 0, index - 1, playerTwoCardsInBoard);
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.MONSTER_DEFENSIVE);
            zone.getChildren().add(cardView);
            cardView.setRotate(90);
        }
        MyMusicPlayer.setCard();
    }

    private void setSpell(int index, int playerNumber, Card card) {
        if (playerNumber == 1) {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 1 : 0, index - 1, playerOneCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.SPELL_HIDDEN);
            zone.getChildren().add(cardView);
        } else {
            StackPane zone = ((StackPane) getNodeByRowColumnIndex(i > 0 ? 0 : 1, index - 1, playerTwoCardsInBoard));
            assert zone != null;
            zone.getChildren().clear();
            CardView cardView = new CardView(card, playerNumber, true, false);
            cardView.setToBoard();
            cardView.setViewLocation(ViewLocation.SPELL_HIDDEN);
            zone.getChildren().add(cardView);
        }
        MyMusicPlayer.setCard();
    }

    public void addTargetCard(CardView cardView) {
        targetCard = cardView;
    }

    public void setupEscPressed() {
        System.out.println("setting pressed ");
        new Setting().show(ViewSwitcher.getStage());
    }

    public void mute() {
        mediaPlayer.muteProperty().setValue(!mediaPlayer.muteProperty().get());
        isPlaying = !isPlaying;
    }

    public void setupCheatScene() {
        CheatPopUp cheatPopUp = new CheatPopUp();
        cheatPopUp.show(ViewSwitcher.getStage());
    }

    public void reset() {
        mediaPlayer.stop();
        isPlaying = false;
    }

}

