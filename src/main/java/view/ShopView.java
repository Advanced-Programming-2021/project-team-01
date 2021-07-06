package view;

import controller.DatabaseController;
import controller.RegisterController;
import controller.ShopController;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.card.Card;
import model.card.MonsterCard;
import view.transions.ShopCheatPopup;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ShopView implements Initializable {
    public Tab playerTab;
    public ScrollPane playerScroll;
    public Button exitButton;
    private ShopCardView selectedCard = null;
    public Tab monsterTab, spellTab;
    public BorderPane mainPane;
    public Pane imageBar;
    public ScrollPane monsterScroll, spellScroll;
    private Rectangle draggableRectangle = new Rectangle(150, 270);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void init() {
        RegisterController.onlineUser = DatabaseController.getUserByName("username");
        setupMonsterTab();
        setupSpellTrapTab();
        setupPlayersCard();
        imageBar.setPadding(new Insets(40, 40, 40, 40));
        ImageView button = new ImageView(new Image(getClass().getResource("k1.png").toExternalForm()));
        button.setFitWidth(100);
        button.setTranslateX(77);
        button.setTranslateY(500);
        button.setFitHeight(100);
        button.setOnMouseClicked(event -> buyCard());
        button.getStyleClass().add("but");
        mainPane.getChildren().add(button);
    }

    private void setupMonsterTab() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        monsterScroll.setContent(pane);
        monsterTab.setContent(monsterScroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> monsters = getMonsters();
        for (int i = 0; i < Math.round((double) monsters.size() / 3); i++) {
            for (int j = 0; j < (Math.min((monsters.size() - 3 * i), 3)); j++) {
                ShopCardView rectangle = new ShopCardView(monsters.get(3 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.getStyleClass().add("but");
                rectangle.setFill(monsters.get(3 * i + j).getCardImage());
                rectangle.setOnMouseClicked(event -> {
                    selectedCard = rectangle;
                    for (Node child : pane.getChildren()) {
                        child.getStyleClass().remove("butoo");
                    }
                    rectangle.getStyleClass().add("butoo");
                    Rectangle rectangle1 = new Rectangle(200, 360);
                    rectangle1.setFill(selectedCard.getCard().getCardImage());
                    imageBar.getChildren().clear();
                    imageBar.getChildren().add(rectangle1);
                    Label label = new Label("Amount: " + RegisterController.onlineUser.getNumberOfCards(rectangle.getCard().getName()));
                    label.setTranslateX(30);
                    label.setTranslateY(400);
                    label.setStyle("-fx-text-fill: #fcba03;-fx-font: 30px \"Arial\";");
                    imageBar.getChildren().add(label);
                });
                setupDragAndDrop(rectangle);
                pane.add(rectangle, j, i);
            }
        }
    }

    private void setupSpellTrapTab() {
        GridPane pane = new GridPane();
        spellScroll.setContent(pane);
        spellTab.setContent(spellScroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> spells = getSpell();
        for (int i = 0; i < Math.round((double) spells.size() / 3); i++) {
            for (int j = 0; j < (Math.min((spells.size() - 3 * i), 3)); j++) {
                ShopCardView rectangle = new ShopCardView(spells.get(3 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.setFill(spells.get(3 * i + j).getCardImage());
                rectangle.getStyleClass().add("but");
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedCard = rectangle;
                        for (Node child : pane.getChildren()) {
                            child.getStyleClass().remove("butoo");
                        }
                        rectangle.getStyleClass().add("butoo");
                        Rectangle rectangle1 = new Rectangle(200, 360);
                        rectangle1.setFill(selectedCard.getCard().getCardImage());
                        imageBar.getChildren().clear();
                        imageBar.getChildren().add(rectangle1);
                        Label label = new Label("Amount: " + RegisterController.onlineUser.getNumberOfCards(rectangle.getCard().getName()));
                        label.setTranslateX(30);
                        label.setTranslateY(400);
                        label.setStyle("-fx-text-fill: #fcba03;-fx-font: 30px \"Arial\";");
                        imageBar.getChildren().add(label);
                    }
                });
                setupDragAndDrop(rectangle);
                pane.add(rectangle, j, i);
            }
        }
    }

    private void setupPlayersCard() {
        GridPane pane = new GridPane();
        playerScroll.setContent(pane);
        playerTab.setContent(playerScroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> playerCards = getPlayerCards();
        for (int i = 0; i < Math.round((double) playerCards.size() / 3); i++) {
            for (int j = 0; j < (Math.min((playerCards.size() - 3 * i), 3)); j++) {
                ShopCardView rectangle = new ShopCardView(playerCards.get(3 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.setFill(playerCards.get(3 * i + j).getCardImage());
                rectangle.getStyleClass().add("but");
                pane.add(rectangle, j, i);
            }
        }
    }

    private ArrayList<Card> getMonsters() {
        ArrayList<Card> temp = new ArrayList<>();
        TreeMap<String, Card> treeMap = Card.getAllCards();
        for (Map.Entry<String, Card> entry : treeMap.entrySet()) {
            if (entry.getValue() instanceof MonsterCard) {
                temp.add(entry.getValue());
            }
        }
        return temp;
    }

    private ArrayList<Card> getSpell() {
        ArrayList<Card> temp = new ArrayList<>();
        TreeMap<String, Card> treeMap = Card.getAllCards();
        for (Map.Entry<String, Card> entry : treeMap.entrySet()) {
            if (!(entry.getValue() instanceof MonsterCard)) {
                temp.add(entry.getValue());
            }
        }
        return temp;
    }

    private ArrayList<Card> getPlayerCards() {
        ArrayList<Card> temp = new ArrayList<>();
        for (String cardName : RegisterController.onlineUser.getPlayerCards()) {
            temp.add(Card.getCardByName(cardName));
        }
        return temp;
    }

    private void buyCard() {
        if (selectedCard == null) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        String cardName = selectedCard.getCard().getName();
        try {
            ShopController.getInstance().buyCard(cardName);
        } catch (Exception exception) {
            new MyAlert(Alert.AlertType.WARNING, exception.getMessage()).show();
            return;
        }
        setupPlayersCard();
        new MyAlert(Alert.AlertType.CONFIRMATION, "Card is bought").show();
    }

    public void exitMenu(MouseEvent mouseEvent) {
        ViewSwitcher.switchTo(View.MAIN);
    }

    public void setupDragAndDrop(ShopCardView cardView) {
        cardView.setOnMouseDragged(event -> {
            mainPane.getChildren().remove(draggableRectangle);
            draggableRectangle.setFill(cardView.getCard().getCardImage());
            draggableRectangle.setTranslateX(event.getSceneX());
            draggableRectangle.setTranslateY(event.getSceneY());
            mainPane.getChildren().add(draggableRectangle);
        });
        cardView.setOnMouseReleased(event -> {
            mainPane.getChildren().remove(draggableRectangle);
            if (event.getSceneX() >= 325 && event.getSceneX() <= 760 &&
                event.getSceneY() >= 35 && event.getSceneY() <= 600) {
                selectedCard = cardView;
                buyCard();
            }
        });
    }

    public static void setupCheatScene() {
        ShopCheatPopup shopCheatPopup = new ShopCheatPopup();
        shopCheatPopup.show(ViewSwitcher.getStage());
    }
}