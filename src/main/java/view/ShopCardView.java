package view;

import javafx.scene.shape.Rectangle;
import model.card.Card;

public class ShopCardView extends Rectangle {
    private Card card;

    public ShopCardView(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
