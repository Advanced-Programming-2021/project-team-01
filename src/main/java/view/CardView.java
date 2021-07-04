package view;

import controller.GameController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.card.Card;
import model.card.CardType;
import model.card.MonsterCard;
import model.card.SpellCard;

import java.util.ArrayList;

public class CardView extends Rectangle {
    private static final ImageView attackView = new ImageView(new Image(CardView.class.getResource("AttackIcon.png").toExternalForm())),
            changePositionView = new ImageView(new Image(CardView.class.getResource("ChangePositionIcon.png").toExternalForm())),
            activateEffectView = new ImageView(new Image(CardView.class.getResource("ActivateEffectIcon.png").toExternalForm())),
            defensiveSetView = new ImageView(new Image(CardView.class.getResource("DefensiveSetIcon.png").toExternalForm())),
            setView = new ImageView(new Image(CardView.class.getResource("SetIcon.png").toExternalForm())),
            flipSummonView = new ImageView(new Image(CardView.class.getResource("FlipSummonIcon.png").toExternalForm())),
            normalSummonView = new ImageView(new Image(CardView.class.getResource("NormalSummonIcon.png").toExternalForm())),
            specialSummonView = new ImageView(new Image(CardView.class.getResource("SpecialSummonIcon.png").toExternalForm()));

    static {
        attackView.setFitWidth(40);
        attackView.setFitHeight(40);
        changePositionView.setFitWidth(40);
        changePositionView.setFitHeight(40);
        activateEffectView.setFitWidth(40);
        activateEffectView.setFitHeight(40);
        activateEffectView.setFitWidth(40);
        activateEffectView.setFitHeight(40);
        defensiveSetView.setFitWidth(40);
        defensiveSetView.setFitHeight(40);
        setView.setFitWidth(40);
        setView.setFitHeight(40);
        flipSummonView.setFitWidth(40);
        flipSummonView.setFitHeight(40);
        normalSummonView.setFitWidth(40);
        normalSummonView.setFitHeight(40);
        specialSummonView.setFitWidth(40);
        specialSummonView.setFitHeight(40);
    }

    private Card card;
    private ViewLocation viewLocation;
    private int cardOwner;
    private ImagePattern image;
    private boolean isHidden;
    private ContextMenu contextMenu = new ContextMenu();

    public CardView(Card card, int owner, boolean isHidden, boolean is180) {
        super(421.0 / 3, 614.0 / 3);
        this.card = card;
        this.cardOwner = owner;
        this.setOnMouseExited(event -> {
            setScaleX(1);
            setScaleY(1);
        });
        setOnMouseEntered(event -> {
            handleOnMouseEntered();
        });
        setOnMouseClicked(event -> {
            handleClicked();
        });
        setImage(isHidden, is180);
        if (getCard() instanceof MonsterCard && ((MonsterCard) getCard()).getCardType() == CardType.EFFECT)
            setViewLocation(ViewLocation.HAND_MONSTER_EFFECT);
        else if (getCard() instanceof MonsterCard && ((MonsterCard) getCard()).getCardType() == CardType.NORMAL)
            setViewLocation(ViewLocation.HAND_MONSTER_NORMAL);
        else if (getCard() instanceof SpellCard)
            setViewLocation(ViewLocation.HAND_SPELL);
    }

    private void handleClicked() {
        GameView.getInstance().addTargetCard(this);


    }

    public void setToBoard() {
        setHeight(95);
        setWidth(75);
    }


    private void handleOnMouseEntered() {
        if (GameController.getInstance().getSelectedCard().isLocked()) {
            return;
        }
        GameController.getInstance().getSelectedCard().set(card);
        GameView.imageCard.getChildren().clear();
        GameView.imageCard.getChildren().add(new Rectangle(300, 400, getImage()));
        setScaleX(1.2);
        setScaleY(1.2);
        StackPane cardText = (StackPane) GameView.cardInformation.getContent();
        cardText.getChildren().clear();
        if (!isHidden() || (cardOwner == GameController.getInstance().getCurrentPlayerNumber())) {
            Text text = new Text();
            String power = "";
            if (card instanceof MonsterCard) {
                int attack;
                int defence;
                try {
                    attack = GameController.getInstance().getGameBoard().getZoneSlotByCard(card).getAttack();
                    defence = GameController.getInstance().getGameBoard().getZoneSlotByCard(card).getDefence();
                } catch (Exception e) {
                    attack = ((MonsterCard) card).getAttack();
                    defence = ((MonsterCard) card).getDefense();
                }
                power = "Attack : " + attack + " Defence : " + defence + '\n';
            }
            text.setFont(Font.font(18));
            text.setWrappingWidth(250);
            text.setText(power + getCard().getDescription());
            text.setFill(Color.WHITE);
            cardText.getChildren().add(text);
        }
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setImage(boolean isHidden, boolean is180) {
        this.isHidden = isHidden;
        if (isHidden)
            image = card.getBackImage();
        else
            image = card.getCardImage();
        if (is180)
            this.rotateProperty().setValue(180);
        setFill(image);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.play();
    }

    public ImagePattern getImage() {
        boolean isOwnerTurn = cardOwner == GameController.getInstance().getCurrentPlayerNumber() || !isHidden;
        if (isOwnerTurn)
            return card.getCardImage();
        else
            return card.getBackImage();
    }

    public Card getCard() {
        return card;
    }

    public ViewLocation getViewLocation() {
        return viewLocation;
    }

    public void setViewLocation(ViewLocation viewLocation) {
        this.viewLocation = viewLocation;
        setContext();
    }

    private void setContext() {
        int currentPlayer = GameController.getInstance().getCurrentPlayerNumber();
        contextMenu.setStyle("-fx-background-color: black;-fx-text-fill: white;");
        contextMenu.getItems().clear();
        if (viewLocation == ViewLocation.MONSTER_OFFENSIVE)
            contextMenu.getItems().addAll(addMenuItem("Attack"), addMenuItem("Change position"));
        else if (viewLocation == ViewLocation.MONSTER_DEFENSIVE)
            contextMenu.getItems().addAll(addMenuItem("Change position"), addMenuItem("Flip summon"));
        else if (viewLocation == ViewLocation.SPELL_HIDDEN)
            contextMenu.getItems().addAll(addMenuItem("Activate effect"));
        else if (viewLocation == ViewLocation.HAND_MONSTER_EFFECT)
            contextMenu.getItems().addAll(addMenuItem("Special summon"), addMenuItem("Normal summon"), addMenuItem("Defensive set"));
        else if (viewLocation == ViewLocation.HAND_MONSTER_NORMAL)
            contextMenu.getItems().addAll(addMenuItem("Defensive set"), addMenuItem("Normal summon"));
        else if (viewLocation == ViewLocation.HAND_SPELL)
            contextMenu.getItems().addAll(addMenuItem("Set"), addMenuItem("Activate effect"));
        setOnContextMenuRequested(event -> {
            if (GameController.getInstance().getCurrentPlayerNumber() == cardOwner) {
                contextMenu.show(this, event.getScreenX(), event.getScreenY() - 100);
            }
        });
    }

    private MenuItem addMenuItem(String menuName) {
        MenuItem menuItem = new MenuItem(menuName);
        menuItem.setStyle("-fx-text-fill: white;");
        switch (menuName) {
            case "Attack":
                menuItem.setGraphic(attackView);
                menuItem.setOnAction(event -> {
                    GameView.getInstance().targetCard = null;
                    new MyAlert(Alert.AlertType.INFORMATION, "select an opponent card").show();
                    Platform.runLater(() -> {
                        GameView.getInstance().attackOnCard();
                    });
                });
                break;
            case "Change position":
                menuItem.setGraphic(changePositionView);
                menuItem.setOnAction(event -> {
                    GameView.changeCardPosition();
                });
                break;
            case "Activate effect":
                menuItem.setGraphic(activateEffectView);
                menuItem.setOnAction(event -> {
                    GameView.activateSpellCard();
                });
                break;
            case "Set":
                menuItem.setGraphic(setView);
                menuItem.setOnAction(event -> {
                    GameView.setCard();
                });
                break;
            case "Defensive set":
                menuItem.setGraphic(defensiveSetView);
                menuItem.setOnAction(event -> {
                    GameView.setCard();
                });
                break;
            case "Flip summon":
                menuItem.setGraphic(flipSummonView);
                menuItem.setOnAction(event -> {
                    GameView.flipSummonCard();
                });
                break;
            case "Special summon":
                menuItem.setGraphic(specialSummonView);
                break;
            case "Normal summon":
                menuItem.setGraphic(normalSummonView);
                menuItem.setOnAction(event -> {
                    GameView.summonCard();
                });
                break;
        }
        return menuItem;
    }
}
