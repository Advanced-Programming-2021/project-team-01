package controller.exceptions;

public class CardNumberLimit extends Exception {
    public CardNumberLimit(String cardName, String deckName) {
        super(String.format("there are already enough number of cards with name %s in deck %s", cardName, deckName));
    }
}