package view;

import Network.Client.Client;
import Network.Requests.Account.BuyRequest;
import Network.Requests.Account.ShopInfoRequest;
import Network.Requests.Request;
import Network.Responses.Account.BuyResponse;
import Network.Responses.Account.ShopInfoResponse;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Player;
import model.card.Card;
import model.card.MonsterCard;
import view.transions.ShopCheatPopup;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ShopView implements GraphicalView {
    public Tab playerTab;
    public ScrollPane playerScroll;
    public Button exitButton;
    public Tab monsterTab, spellTab;
    public BorderPane mainPane;
    public Pane imageBar;
    public ScrollPane monsterScroll, spellScroll;
    public Player player;
    private ShopCardView selectedCard = null;
    private Label money;
    private ImagePattern priceImage = new ImagePattern(new Image(getClass().getResource("/view/sb.png").toExternalForm()));
    private Rectangle draggableRectangle = new Rectangle(150, 270);

    public static void setupCheatScene() {
        ShopCheatPopup shopCheatPopup = new ShopCheatPopup();
        shopCheatPopup.show(ViewSwitcher.getStage());
    }

    @Override
    public void init(Pane root) {
        Request request = new ShopInfoRequest(Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
        setupMonsterTab();
        setupSpellTrapTab();
        imageBar.setPadding(new Insets(40, 40, 40, 40));
        ImageView button = new ImageView(new Image(getClass().getResource("k1.png").toExternalForm()));
        button.setFitWidth(100);
        button.setTranslateX(77);
        button.setTranslateY(500);
        button.setFitHeight(100);
        button.setOnMouseClicked(event -> buyCard());
        button.getStyleClass().add("but");
        mainPane.getChildren().addAll(button);
    }

    private void setupMoneyBar() {
        money = new Label(String.valueOf(player.getMoney()));
        money.setTextFill(Color.ORANGE);
        money.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        money.setTranslateX(40);
        money.setTranslateY(410);
        imageBar.getChildren().add(money);
    }

    private void setupMonsterTab() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 20));
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
                    Label label = new Label("Amount: " + player.getNumberOfCards(rectangle.getCard().getName()));
                    label.setTranslateX(30);
                    label.setTranslateY(400);
                    label.setStyle("-fx-text-fill: #fcba03;-fx-font: 30px \"Arial\";");
                    setupPriceLabel();
                    imageBar.getChildren().add(label);
                    imageBar.getChildren().add(money);
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
                    Label label = new Label("Amount: " + player.getNumberOfCards(rectangle.getCard().getName()));
                    label.setTranslateX(30);
                    label.setTranslateY(400);
                    label.setStyle("-fx-text-fill: #fcba03;-fx-font: 30px \"Arial\";");
                    setupPriceLabel();
                    imageBar.getChildren().add(label);
                    imageBar.getChildren().add(money);
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
        for (int i = 0; i < Math.ceil((double) playerCards.size() / 3); i++) {
            for (int j = 0; j < (Math.min((playerCards.size() - 3 * i), 3)); j++) {
                ShopCardView rectangle = new ShopCardView(playerCards.get(3 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                try {
                    rectangle.setFill(playerCards.get(3 * i + j).getCardImage());
                } catch (Exception e) {
                    System.out.println("this");
                }
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
        ArrayList<Card> playerCards = new ArrayList<>();
        for (String cardName : player.getPlayerCards()) {
            playerCards.add(Card.getCardByName(cardName));
        }
        return playerCards;
    }

    private void buyCard() {
        if (selectedCard == null) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        String cardName = selectedCard.getCard().getName();
        Request request = new BuyRequest(cardName, Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
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

    private void setupPriceLabel() {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(priceImage);
        rectangle.setWidth(100);
        rectangle.setHeight(30);
        rectangle.setTranslateY(340);
        rectangle.setTranslateX(50);
        Label label = new Label(String.valueOf(selectedCard.getCard().getPrice()));
        label.setStyle("-fx-text-fill: white;-fx-font: 16px \"Arial\";");
        label.setTranslateX(80);
        label.setTranslateY(345);
        imageBar.getChildren().addAll(rectangle, label);
    }

    public void buyResponse(BuyResponse response, String card) {
        try {
            if (response.getException() != null)
                throw response.getException();
            player.getPlayerCards().add(card);
        } catch (Exception exception) {
            new MyAlert(Alert.AlertType.WARNING, exception.getMessage()).show();
            return;
        }
        setupPlayersCard();
        money.setText(String.valueOf(player.getMoney()));
        new MyAlert(Alert.AlertType.CONFIRMATION, "Card is bought").show();
    }

    public void infoResponse(ShopInfoResponse response) {
        setupPlayersCard();
        setupMoneyBar();
    }
}