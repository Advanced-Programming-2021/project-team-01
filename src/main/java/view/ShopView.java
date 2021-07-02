package view;

import controller.ShopController;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.card.Card;
import model.card.MonsterCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ShopView implements Initializable {
    private ShopCardView selectedCard = null;
    public Tab monsterTab, spellTab;
    public BorderPane mainPane;
    public StackPane imageBar;
    public ScrollPane monsterScroll, spellScroll;


    public void init() {
        setupMonsterTab();
        setupSpellTrapTab();
        imageBar.setPadding(new Insets(40, 40, 40, 40));
        ImageView button = new ImageView(new Image(getClass().getResource("k1.png").toExternalForm()));
        button.setFitWidth(100);
        button.setTranslateX(100);
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
        for (int i = 0; i < Math.round((double) monsters.size() / 6); i++) {
            for (int j = 0; j < (Math.min((monsters.size() - 6 * i), 6)); j++) {
                ShopCardView rectangle = new ShopCardView(monsters.get(6 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.getStyleClass().add("but");
                rectangle.setFill(monsters.get(6 * i + j).getCardImage());
                rectangle.setOnMouseClicked(event -> {
                    selectedCard = rectangle;
                    for (Node child : pane.getChildren()) {
                        child.getStyleClass().remove("butoo");
                    }
                    rectangle.getStyleClass().add("butoo");
                    Rectangle rectangle1 = new Rectangle(250, 450);
                    rectangle1.setFill(selectedCard.getCard().getCardImage());
                    imageBar.getChildren().clear();
                    imageBar.getChildren().add(rectangle1);
                });
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
        for (int i = 0; i < Math.round((double) spells.size() / 6); i++) {
            for (int j = 0; j < (Math.min((spells.size() - 6 * i), 6)); j++) {
                ShopCardView rectangle = new ShopCardView(spells.get(6 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.setFill(spells.get(6 * i + j).getCardImage());
                rectangle.getStyleClass().add("but");
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedCard = rectangle;
                        for (Node child : pane.getChildren()) {
                            child.getStyleClass().remove("butoo");
                        }
                        rectangle.getStyleClass().add("butoo");
                        Rectangle rectangle1 = new Rectangle(250, 450);
                        rectangle1.setFill(selectedCard.getCard().getCardImage());
                        imageBar.getChildren().clear();
                        imageBar.getChildren().add(rectangle1);
                    }
                });
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

    private void buyCard() {
        if (selectedCard == null) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        String cardName = selectedCard.getCard().getName();
        try {
            ShopController.getInstance().buyCard(cardName);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
        new MyAlert(Alert.AlertType.CONFIRMATION, "Card is bought").show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
}