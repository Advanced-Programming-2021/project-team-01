package view;

import javafx.scene.shape.Rectangle;
import model.card.Card;

public class ShopCardView extends Rectangle {
    private Card card;
    private DeckViewLocation deckViewLocation;

    public ShopCardView(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public DeckViewLocation getDeckViewLocation() {
        return deckViewLocation;
    }

    public void setDeckViewLocation(DeckViewLocation deckViewLocation) {
        this.deckViewLocation = deckViewLocation;
    }
}

enum DeckViewLocation {
    SIDE_DECK,
    MAIN_DECK,
    CARDS;
}
