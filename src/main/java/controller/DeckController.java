package controller;

import model.Deck;
import model.Player;
import model.card.Card;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Deck> showAllDecks() {
        return null;
    }

    public ArrayList<Card> showDeckByName(String name, boolean isMainDeck) {
        return null;
    }

    public ArrayList<Card> showPlayersAllCards() {
        return null;
    }
}
