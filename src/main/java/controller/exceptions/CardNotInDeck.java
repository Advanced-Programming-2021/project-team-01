package controller.exceptions;

public class CardNotInDeck extends Exception{
    public CardNotInDeck(String cardName, String deckType) {
        super(String.format("card with name %s does not exist in %s deck", cardName, deckType));
    }
}
