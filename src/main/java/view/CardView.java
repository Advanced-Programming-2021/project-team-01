package view;

import controller.GameController;
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
import model.card.Card;
import model.card.CardType;
import model.card.MonsterCard;
import model.card.SpellCard;

public class CardView extends Rectangle {
    private Card card;
    private ViewLocation viewLocation;
    private int cardOwner;
    private ImagePattern image;
    private boolean isHidden;
    private ContextMenu contextMenu = new ContextMenu();
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

    public CardView(Card card, int owner,boolean isHidden, boolean is180) {
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
        setImage(isHidden, is180);
        if (getCard() instanceof MonsterCard && ((MonsterCard) getCard()).getCardType() == CardType.EFFECT)
            setViewLocation(ViewLocation.HAND_MONSTER_EFFECT);
        else if (getCard() instanceof MonsterCard && ((MonsterCard) getCard()).getCardType() == CardType.NORMAL)
            setViewLocation(ViewLocation.HAND_MONSTER_NORMAL);
        else if (getCard() instanceof SpellCard)
            setViewLocation(ViewLocation.HAND_SPELL);
    }

    private void handleOnMouseEntered() {
        GameView.imageCard.getChildren().clear();
        GameView.imageCard.getChildren().add(new Rectangle(300, 400, getImage()));
        setScaleX(1.2);
        setScaleY(1.2);
        StackPane cardText = (StackPane) GameView.cardInformation.getContent();
        cardText.getChildren().clear();
        if (!isHidden()) {
            Text text = new Text();
            text.setFont(Font.font(20));
            text.setWrappingWidth(250);
            text.setText(getCard().getDescription());
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
    }

    public ImagePattern getImage() {
        return image;
    }

    public Card getCard() {
        return card;
    }

    public void setViewLocation(ViewLocation viewLocation) {
        this.viewLocation = viewLocation;
        setContext();
    }

    public ViewLocation getViewLocation() {
        return viewLocation;
    }

    private void setContext() {
        int currentPlayer = GameController.getInstance().getCurrentPlayerNumber();
        contextMenu.setStyle("-fx-background-color: black;-fx-text-fill: white;");
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
            contextMenu.getItems().addAll(addMenuItem("Set"));
        setOnContextMenuRequested(event -> {
            if (GameController.getInstance().getCurrentPlayerNumber() == cardOwner)
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        });
    }

    private MenuItem addMenuItem(String menuName) {
        MenuItem menuItem = new MenuItem(menuName);
        menuItem.setStyle("-fx-text-fill: white;");
        switch (menuName) {
            case "Attack":
                menuItem.setGraphic(attackView);
                break;
            case "Change position":
                menuItem.setGraphic(changePositionView);
                break;
            case "Activate effect":
                menuItem.setGraphic(activateEffectView);
                break;
            case "Set":
                menuItem.setGraphic(setView);
                break;
            case "Defensive set":
                menuItem.setGraphic(defensiveSetView);
                break;
            case "Flip summon":
                menuItem.setGraphic(flipSummonView);
                break;
            case "Special summon":
                menuItem.setGraphic(specialSummonView);
                break;
            case "Normal summon":
                menuItem.setGraphic(normalSummonView);
                break;
        }
        return menuItem;
    }
}
