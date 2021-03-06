package view;

import Network.Client.Client;
import Network.Requests.Account.AddCardToDeckRequest;
import Network.Requests.Account.RemoveCardFromDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
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
import model.Player;
import model.card.Card;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeckView implements GraphicalView {
    public static Deck currentDeck;
    public static Player player;
    private ShopCardView selectedCard = null;
    public Pane imageBar;
    public ScrollPane sideDeckScroll, mainDeckScroll, cardsScroll;
    public Tab mainDeckTab, sideDeckTab;

    public void init(Pane root) {
        try {
            setupMainDeckTab();
            setupSideDeckTab();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupCardsSection();
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
        ArrayList<Card> mainDeck = currentDeck.getMainDeck();
        for (int i = 0; i < Math.ceil((double) mainDeck.size() / 5); i++) {
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
        ArrayList<Card> sideDeck = currentDeck.getSideDeck();
        for (int i = 0; i < Math.ceil((double) sideDeck.size() / 6); i++) {
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
        for (String card : player.getPlayerCards())
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
            Request request = new RemoveCardFromDeckRequest("main",
                    selectedCard.getCard().getName(), currentDeck.getDeckName(), Client.getInstance().getToken());
            Client.getInstance().sendData(request.toString());
        } else if (selectedCard.getDeckViewLocation() == DeckViewLocation.SIDE_DECK) {
            Request request = new RemoveCardFromDeckRequest("side",
                    selectedCard.getCard().getName(), currentDeck.getDeckName(), Client.getInstance().getToken());
            Client.getInstance().sendData(request.toString());
        }
    }

    @FXML
    private void addCardToMainDeck() {
        if (selectedCard == null || selectedCard.getDeckViewLocation() != DeckViewLocation.CARDS) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        Request request = new AddCardToDeckRequest("main", selectedCard.getCard().getName(),
                currentDeck.getDeckName(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());

    }

    @FXML
    private void addCardToSideDeck() {
        if (selectedCard == null || selectedCard.getDeckViewLocation() != DeckViewLocation.CARDS) {
            new MyAlert(Alert.AlertType.WARNING, "No card is selected!").show();
            return;
        }
        Request request = new AddCardToDeckRequest("side", selectedCard.getCard().getName(),
                currentDeck.getDeckName(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
    }

    public void addCardResponse(Response response, boolean isMainDeck, Card card) {
        try {
            if (response.getException() != null) throw response.getException();
            if (isMainDeck) {
                currentDeck.getMainDeck().add(card);
                setupMainDeckTab();
            } else {
                currentDeck.getSideDeck().add(card);
                setupSideDeckTab();
            }
            new MyAlert(Alert.AlertType.INFORMATION, "Card is added successfully.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.WARNING, expt.getMessage()).show();
        }
    }

    public void removeCardResponse(Response response, String deckType, Card card) {
        try {
            if (response.getException() != null)
                throw response.getException();
            if (deckType.equals("main")) {
                currentDeck.removeCardFromMainDeck(card);
                setupMainDeckTab();
            } else {
                currentDeck.removeCardFromSideDeck(card);
                setupSideDeckTab();
            }
            imageBar.getChildren().clear();
            new MyAlert(Alert.AlertType.INFORMATION, "Card got removed successfully.").show();
        } catch (Exception expt) {
            new MyAlert(Alert.AlertType.WARNING, expt.getMessage()).show();
        }
    }
}
