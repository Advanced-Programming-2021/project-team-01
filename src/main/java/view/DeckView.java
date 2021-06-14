package view;

import controller.DatabaseController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import model.Deck;
import model.card.Card;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeckView implements Initializable {
    public static Deck currentDeck;
    private ShopCardView selectedCard = null;
    public StackPane imageBar;
    public ScrollPane sideDeckScroll, mainDeckScroll;
    public Tab mainDeckTab, sideDeckTab;

    public void init() throws Exception {
        currentDeck = DatabaseController.getDeckByName("xx");
        setupMainDeckTab();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupMainDeckTab() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,20));
        mainDeckScroll.setContent(pane);
        mainDeckTab.setContent(mainDeckScroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> mainDeck = currentDeck.getMainDeck();
        for (int i = 0; i < Math.round((double) mainDeck.size() / 4); i++) {
            for (int j = 0; j < (Math.min((mainDeck.size() - 4 * i), 4)); j++) {
                ShopCardView rectangle = new ShopCardView(mainDeck.get(4 * i + j));
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.getStyleClass().add("but");
                rectangle.setFill(mainDeck.get(4 * i + j).getCardImage());
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

    @FXML
    private void exit() {
        ViewSwitcher.switchTo(View.MAIN);
    }
}
