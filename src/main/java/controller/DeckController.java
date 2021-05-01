package controller;

import controller.exceptions.DeckExists;
import controller.exceptions.DeckNotExists;
import model.Deck;
import model.card.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DeckController {
    private static DeckController instance = null;

    public static DeckController getInstance() {
        if (instance == null) {
            instance = new DeckController();
        }
        return instance;
    }

    public void createDeck(String name) throws DeckExists {
        if (!DatabaseController.doesDeckExists(name)) {
            Deck deck = new Deck(name);
            DatabaseController.updateDeck(deck);
            RegisterController.getInstance().onlineUser.addDeck(name);
        } else
            throw new DeckExists(name);
    }

    public void deleteDeck(String name) throws DeckNotExists {
        if (DatabaseController.doesDeckExists(name)) {
            DatabaseController.deleteDeckFile(name);
            RegisterController.getInstance().onlineUser.deleteDeck(name);
        } else
            throw new DeckNotExists(name);
    }

    public void activateDeck(String name) throws DeckNotExists {
        if (DatabaseController.doesDeckExists(name)) {
            RegisterController.getInstance().onlineUser.setActiveDeck(name);
        } else
            throw new DeckNotExists(name);
    }

    public void addCardToDeck(String cardName, String deckName, boolean isMainDeck) {

    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isMainDeck) {

    }

    public ArrayList<Deck> showAllDecks() {
        return null;
    }

    public ArrayList<Card> showDeckByName(String name ,boolean isMainDeck) throws DeckNotExists {
        if (DatabaseController.doesDeckExists(name)) {
            Deck deck = DatabaseController.getDeckByName(name);
            ArrayList<Card> cards = new ArrayList<>();
            if (isMainDeck) {
                cards = deck.getMainDeck();
            } else
                cards = deck.getSideDeck();
            Collections.sort(cards, new cardSort());
            return cards;
        } else
            throw new DeckNotExists(name);
    }

    public ArrayList<Card> showPlayersAllCards() {
        ArrayList<String> cardsName = RegisterController.getInstance().onlineUser.getPlayerCards();
        Collections.sort(cardsName);
        ArrayList<Card> cards = new ArrayList<>();
        for (String name : cardsName) {
            cards.add(Card.getCardByName(name));
        }
        return cards;
    }

    static class cardSort implements Comparator<Card> {
        public int compare(Card card1, Card card2) {
            return card1.getName().compareTo(card2.getName());
        }
    }
}
