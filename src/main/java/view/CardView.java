package view;

import controller.GameController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.card.*;


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
        if (GameController.getInstance().isDone() && GameController.getInstance().controllerNumber == 2) //fixme: do we new CardView()
            this.cardOwner = (owner == 1 ? 2 : 1);
        this.setOnMouseExited(event -> {
            setScaleX(1);
            setScaleY(1);
        });
        setOnMouseEntered(event -> {
            handleOnMouseEntered();
        });
        setOnMouseClicked(this::handleClicked);
        setImage(isHidden, is180);
        if (getCard() instanceof MonsterCard && ((MonsterCard) getCard()).getCardType() == CardType.EFFECT)
            setViewLocation(ViewLocation.HAND_MONSTER_EFFECT);
        else if (getCard() instanceof MonsterCard && ((MonsterCard) getCard()).getCardType() == CardType.NORMAL)
            setViewLocation(ViewLocation.HAND_MONSTER_NORMAL);
        else if (getCard() instanceof SpellCard || getCard() instanceof TrapCard)
            setViewLocation(ViewLocation.HAND_SPELL);
    }

    private void handleClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && GameView.getInstance().isAttacking) {
            GameView.getInstance().addTargetCard(this);
            GameView.getInstance().attackOnCard();
        }
    }

    public void setToBoard() {
        setHeight(95);
        setWidth(75);
    }


    private void handleOnMouseEntered() {
        if (!GameController.getInstance().getSelectedCard().isLocked()) {
            GameController.getInstance().getSelectedCard().set(card);
        }
        GameView.imageCard.getChildren().clear();
        GameView.imageCard.getChildren().add(new Rectangle(300, 400, getImage()));
        setScaleX(1.2);
        setScaleY(1.2);
        StackPane cardText = (StackPane) GameView.cardInformation.getContent();
        cardText.getChildren().clear();
        if (!isHidden() || (cardOwner == GameController.getInstance().controllerNumber)) {
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
        boolean shouldShow = cardOwner == GameController.getInstance().controllerNumber || !isHidden;
        if (shouldShow)
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
            if (GameController.getInstance().getCurrentPlayerNumber() == cardOwner &&
            GameController.getInstance().controllerNumber == GameController.getInstance().getCurrentPlayerNumber()) {
                contextMenu.show(this, event.getScreenX(), event.getScreenY() - 100);
            }
        });
    }

    private MenuItem addMenuItem(String menuName) {
        MenuItem menuItem = new MenuItem(menuName);
        menuItem.setStyle("-fx-text-fill: white;");
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), this);
        translateTransition.setByX(15);
        translateTransition.setCycleCount(8);
        translateTransition.setAutoReverse(true);
        switch (menuName) {
            case "Attack":
                menuItem.setGraphic(attackView);
                menuItem.setOnAction(event -> {
                    if(GameController.getInstance().getGameBoard().getCardInMonsterZone(GameController.getInstance().getOpponentPlayerNumber()).size() == 0) {
                        GameController.getInstance().getSelectedCard().lock();
                        try {
                            GameView.getInstance().showDirectAttack();
                            GameController.getInstance().directAttack();
                        } catch (Exception exception) {
                            new MyAlert(Alert.AlertType.ERROR, exception.getMessage()).show();
                        }
                        GameController.getInstance().getSelectedCard().unlock();
                    } else {
                        translateTransition.setOnFinished(e -> {
                            GameController.getInstance().getSelectedCard().lock();
                            GameView.getInstance().targetCard = null;
                            new MyAlert(Alert.AlertType.INFORMATION, "select an opponent card").show();
                            GameView.getInstance().isAttacking = true;
                        });
                        translateTransition.play();
                    }
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
                    GameView.getInstance().activateSpellCard();
                });
                break;
            case "Set":
                menuItem.setGraphic(setView);
                menuItem.setOnAction(event -> {
                    GameView.getInstance().setCard();
                });
                break;
            case "Defensive set":
                menuItem.setGraphic(defensiveSetView);
                menuItem.setOnAction(event -> {
                    GameView.getInstance().setCard();
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
                menuItem.setOnAction(event -> {
                    GameView.specialSummon();
                });
                break;
            case "Normal summon":
                menuItem.setGraphic(normalSummonView);
                menuItem.setOnAction(event -> {
                    GameView.getInstance().summonCard();
                });
                break;
        }
        return menuItem;
    }
}
