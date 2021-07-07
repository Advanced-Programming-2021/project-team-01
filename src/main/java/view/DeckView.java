package view;

import controller.DatabaseController;
import controller.DeckController;
import controller.RegisterController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Deck;
import model.card.Card;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeckView implements Initializable {
    public static Deck currentDeck;
    private ShopCardView selectedCard = null;
    public Pane imageBar;
    public ScrollPane sideDeckScroll, mainDeckScroll, cardsScroll;
    public Tab mainDeckTab, sideDeckTab;

    public void init() throws Exception {
        setupMainDeckTab();
        setupSideDeckTab();
        setupCardsSection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupMainDeckTab() throws Exception {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        mainDeckScroll.setContent(pane);
        mainDeckTab.setContent(mainDeckScroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> mainDeck = DatabaseController.getDeckByName(currentDeck.getDeckName()).getMainDeck();
        for (int i = 0; i < Math.round((double) mainDeck.size() / 5); i++) {
            for (int j = 0; j < (Math.min((mainDeck.size() - 5 * i), 5)); j++) {
                ShopCardView rectangle = new ShopCardView(mainDeck.get(5 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.getStyleClass().add("but");
                rectangle.setFill(mainDeck.get(5 * i + j).getCardImage());
                rectangle.setOnMouseClicked(event -> {
                    selectedCard = rectangle;
                    selectedCard.setDeckViewLocation(DeckViewLocation.MAIN_DECK);
                    for (Node child : pane.getChildren()) {
                        child.getStyleClass().remove("butoo");
                    }
                    rectangle.getStyleClass().add("butoo");
                    Rectangle rectangle1 = new Rectangle(200, 360);
                    rectangle1.setFill(selectedCard.getCard().getCardImage());
                    imageBar.getChildren().clear();
                    imageBar.getChildren().add(rectangle1);
                });
                pane.add(rectangle, j, i);
            }
        }
    }

    private void setupSideDeckTab() throws Exception {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        sideDeckScroll.setContent(pane);
        sideDeckTab.setContent(sideDeckScroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> sideDeck = DatabaseController.getDeckByName(currentDeck.getDeckName()).getSideDeck();
        for (int i = 0; i < Math.round((double) sideDeck.size() / 6); i++) {
            for (int j = 0; j < (Math.min((sideDeck.size() - 6 * i), 6)); j++) {
                ShopCardView rectangle = new ShopCardView(sideDeck.get(6 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.getStyleClass().add("but");
                rectangle.setFill(sideDeck.get(6 * i + j).getCardImage());
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedCard = rectangle;
                        selectedCard.setDeckViewLocation(DeckViewLocation.SIDE_DECK);
                        for (Node child : pane.getChildren()) {
                            child.getStyleClass().remove("butoo");
                        }
                        rectangle.getStyleClass().add("butoo");
                        Rectangle rectangle1 = new Rectangle(200, 360);
                        rectangle1.setFill(selectedCard.getCard().getCardImage());
                        imageBar.getChildren().clear();
                        imageBar.getChildren().add(rectangle1);
                    }
                });
                pane.add(rectangle, j, i);
            }
        }
    }

    private void setupCardsSection() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        cardsScroll.setContent(pane);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> cards = new ArrayList<>();
        for (String card : RegisterController.onlineUser.getPlayerCards())
            cards.add(Card.getCardByName(card));
        for (int i = 0; i < Math.round((double) cards.size()); i++) {
            for (int j = 0; j < (Math.min((cards.size() - 1 * i), 1)); j++) {
                ShopCardView rectangle = new ShopCardView(cards.get(i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.getStyleClass().add("but");
                rectangle.setFill(cards.get(i + j).getCardImage());
                rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedCard = rectangle;
                        selectedCard.setDeckViewLocation(DeckViewLocation.CARDS);
                        for (Node child : pane.getChildren()) {
                            child.getStyleClass().remove("butoo");
                        }
                        rectangle.getStyleClass().add("butoo");
                        Rectangle rectangle1 = new Rectangle(200, 360);
                        rectangle1.setFill(selectedCard.getCard().getCardImage());
                        imageBar.getChildren().clear();
                        imageBar.getChildren().add(rectangle1);
                    }
                });
                pane.add(rectangle, j, i);
            }
        }
    }

    @FXML
    private void exit() {
        ViewSwitcher.switchTo(View.MAIN);
    }

    @FXML
    private void removeCardFromDeck() throws Exception {
        if (selectedCard == null || selectedCard.getDeckViewLocation() == DeckViewLocation.CARDS) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        } else if (selectedCard.getDeckViewLocation() == DeckViewLocation.MAIN_DECK) {
            DeckController.getInstance().removeCardFromDeck(selectedCard.getCard().getName(), currentDeck.getDeckName(), true);
            setupMainDeckTab();
        } else if (selectedCard.getDeckViewLocation() == DeckViewLocation.SIDE_DECK) {
            DeckController.getInstance().removeCardFromDeck(selectedCard.getCard().getName(), currentDeck.getDeckName(), false);
            setupSideDeckTab();
        }
        imageBar.getChildren().clear();
        new MyAlert(Alert.AlertType.INFORMATION, "Card got removed successfully.").show();
    }

    @FXML
    private void addCardToMainDeck() {
        if (selectedCard == null || selectedCard.getDeckViewLocation() != DeckViewLocation.CARDS) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        try {
            DeckController.getInstance().addCardToDeck(
                    selectedCard.getCard().getName(), currentDeck.getDeckName(), true);
            setupMainDeckTab();
            new MyAlert(Alert.AlertType.INFORMATION, "Card is added successfully.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.WARNING, expt.getMessage()).show();
        }
    }

    @FXML
    private void addCardToSideDeck() {
        if (selectedCard == null || selectedCard.getDeckViewLocation() != DeckViewLocation.CARDS) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        try {
            DeckController.getInstance().addCardToDeck(
                    selectedCard.getCard().getName(), currentDeck.getDeckName(), false);
            setupSideDeckTab();
            new MyAlert(Alert.AlertType.INFORMATION, "Card is added successfully.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.WARNING, expt.getMessage()).show();
        }
    }
}
