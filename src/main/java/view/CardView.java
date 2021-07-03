package view;

import controller.GameController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.card.Card;

public class CardView extends Rectangle {
    private Card card;
    private ViewLocation viewLocation;
    private int cardOwner;
    private ImagePattern image;
    private boolean isHidden;
    private ContextMenu contextMenu;
    private static ImageView attackView = new ImageView(new Image(CardView.class.getResource("AttackIcon.png").toExternalForm())),
            changePositionView = new ImageView(new Image(CardView.class.getResource("ChangePositionIcon.png").toExternalForm())),
            activateEffectView = new ImageView(new Image(CardView.class.getResource("ActivateEffectIcon.png").toExternalForm())),
            defensiveSetView = new ImageView(new Image(CardView.class.getResource("DefensiveSetIcon.png").toExternalForm())),
            setView = new ImageView(new Image(CardView.class.getResource("SetIcon.png").toExternalForm())),
            flipSummonView = new ImageView(new Image(CardView.class.getResource("FlipSummonIcon.png").toExternalForm())),
            normalSummonView = new ImageView(new Image(CardView.class.getResource("NormalSummonIcon.png").toExternalForm())),
            specialSummonView = new ImageView(new Image(CardView.class.getResource("SpecialSummonIcon.png").toExternalForm()));

    static {
        attackView.setFitWidth(15);
        attackView.setFitHeight(15);
        changePositionView.setFitWidth(15);
        changePositionView.setFitHeight(15);
        activateEffectView.setFitWidth(15);
        activateEffectView.setFitHeight(15);
        activateEffectView.setFitWidth(15);
        activateEffectView.setFitHeight(15);
        defensiveSetView.setFitWidth(15);
        defensiveSetView.setFitHeight(15);
        setView.setFitWidth(15);
        setView.setFitHeight(15);
        flipSummonView.setFitWidth(15);
        flipSummonView.setFitHeight(15);
        normalSummonView.setFitWidth(15);
        normalSummonView.setFitHeight(15);
        specialSummonView.setFitWidth(15);
        specialSummonView.setFitHeight(15);
    }

    public CardView(Card card,int owner){
        super(421.0/3,614.0/3);
        this.card = card;
        this.cardOwner = owner;
        this.setOnMouseExited(event -> {
            setScaleX(1);
            setScaleY(1);
        });
        setContext();
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setImage(boolean isHidden, boolean is180){
        this.isHidden = isHidden;
        if(isHidden)
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

    private void setContext(){
        int currentPlayer = GameController.getInstance().getCurrentPlayerNumber();
        contextMenu.setStyle("-fx-background-color: black;-fx-text-fill: white;");
        if (viewLocation == ViewLocation.MONSTER_OFFENSIVE)
            contextMenu.getItems().addAll(addMenuItem("Attack"), addMenuItem("Change position"));
        if (viewLocation == ViewLocation.MONSTER_DEFENSIVE)
            contextMenu.getItems().addAll(addMenuItem("Change position"), addMenuItem("Flip summon"));
        if (viewLocation == ViewLocation.SPELL_HIDDEN)
            contextMenu.getItems().addAll(addMenuItem("Activate effect"));
        if (viewLocation == ViewLocation.HAND_MONSTER_EFFECT)
            contextMenu.getItems().addAll(addMenuItem("Special summon"), addMenuItem("Normal summon"), addMenuItem("Defensive summon"));
        if (viewLocation == ViewLocation.HAND_MONSTER_NORMAL)
            contextMenu.getItems().addAll(addMenuItem("Defensive summon"), addMenuItem("Normal summon"));
        setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));
    }

    private MenuItem addMenuItem(String menuName) {
        MenuItem menuItem = new MenuItem(menuName);
        menuItem.setStyle("-fx-text-fill: white;");
        switch (menuName) {
            case "Attack":
                menuItem.setGraphic(attackView);
            case "Change position":
                menuItem.setGraphic(changePositionView);
            case "Activate effect":
                menuItem.setGraphic(activateEffectView);
            case "Set":
                menuItem.setGraphic(setView);
            case "Defensive set":
                menuItem.setGraphic(defensiveSetView);
            case "Flip summon":
                menuItem.setGraphic(flipSummonView);
            case "Special summon":
                menuItem.setGraphic(specialSummonView);
            case "Normal summon":
                menuItem.setGraphic(normalSummonView);
        }
        return menuItem;
    }
}
