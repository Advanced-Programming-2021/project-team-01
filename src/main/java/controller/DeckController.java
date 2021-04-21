package controller;

public class DeckController {
    private static DeckController instance = null;

    public static DeckController getInstance() {
        if (instance == null) {
            instance = new DeckController();
        }
        return instance;
    }

    public void createDeck(String name) {

    }

    public void deleteDeck(String name) {

    }

    public void activateDeck(String name) {

    }

    public void addCardToDeck(String cardName, String deckName, boolean isMainDeck) {

    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isMainDeck) {

    }

    public void showAllDecks() {

    }

    public void showDeckByName(String name, boolean isMainDeck) {

    }

    public void showPlayersAllCards() {

    }
}
