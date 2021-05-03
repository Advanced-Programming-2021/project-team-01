package controller;

import controller.exceptions.*;
import model.Deck;
import model.card.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static controller.RegisterController.onlineUser;

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
            onlineUser.addDeck(name);
        } else
            throw new DeckExists(name);
    }

    public void deleteDeck(String name) throws DeckNotExists {
        if (DatabaseController.doesDeckExists(name)) {
            DatabaseController.deleteDeckFile(name);
            onlineUser.deleteDeck(name);
        } else
            throw new DeckNotExists(name);
    }

    public void activateDeck(String name) throws DeckNotExists {
        if (DatabaseController.doesDeckExists(name)) {
            onlineUser.setActiveDeck(name);
        } else
            throw new DeckNotExists(name);
    }

    public void addCardToDeck(String cardName, String deckName, boolean isMainDeck) throws CardNameNotExists,
            DeckNotExists, MainDeckIsFull, SideDeckIsFull, CardNumberLimit {
        Card card = Card.getCardByName(cardName);
        if (card != null) {
            if (DatabaseController.doesDeckExists(deckName)) {
                Deck deck = DatabaseController.getDeckByName(deckName);
                if (isMainDeck) {
                    if (deck.getMainDeck().size() < 60) {
                        if (deck.checkCardsLimit(card)) {
                            deck.addCardToMainDeck(card);
                            DatabaseController.updateDeck(deck);
                        } else
                            throw new CardNumberLimit(cardName, deckName);
                    } else
                        throw new MainDeckIsFull();
                } else {
                    if (deck.getSideDeck().size() < 15) {
                        if (deck.checkCardsLimit(card)) {
                            deck.addCardToSideDeck(card);
                            DatabaseController.updateDeck(deck);
                        } else
                            throw new CardNumberLimit(cardName, deckName);
                    } else
                        throw new SideDeckIsFull();
                }
            } else
                throw new DeckNotExists(deckName);
        } else
            throw new CardNameNotExists(cardName);
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isMainDeck) throws CardNameNotExists,
            DeckNotExists, CardNotInDeck {
        Card card = Card.getCardByName(cardName);
        if (card != null) {
            if (DatabaseController.doesDeckExists(deckName)) {
                Deck deck = DatabaseController.getDeckByName(deckName);
                if (isMainDeck) {
                    if (deck.getMainDeck().contains(card)) {
                        deck.removeCardFromMainDeck(card);
                        DatabaseController.updateDeck(deck);
                    }
                    else
                        throw new CardNotInDeck(cardName, "main");
                } else {
                    if (deck.getSideDeck().contains(card)) {
                        deck.removeCardFromSideDeck(card);
                        DatabaseController.updateDeck(deck);
                    }
                    else
                        throw new CardNotInDeck(cardName, "side");
                }
            } else
                throw new DeckNotExists(deckName);
        } else
            throw new CardNameNotExists(cardName);
    }

    public ArrayList<Deck> showAllDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        ArrayList<Deck> activeDeck = new ArrayList<>();
        if (RegisterController.onlineUser.getActiveDeck() == null) {
            activeDeck.add(null);
        } else
            activeDeck.add(DatabaseController.getDeckByName(RegisterController.onlineUser.getActiveDeck()));
        for (String deck : RegisterController.onlineUser.getPlayerDecks()) {
            decks.add(DatabaseController.getDeckByName(deck));
        }
        decks.sort(new deckSort());
        activeDeck.addAll(decks);
        return activeDeck;
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
        ArrayList<String> cardsName = onlineUser.getPlayerCards();
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

    static class deckSort implements Comparator<Deck> {
        public int compare(Deck deck1, Deck deck2) {
            return deck1.getDeckName().compareTo(deck2.getDeckName());
        }
    }
}
