package controller;

import controller.exceptions.*;
import model.Deck;
import model.card.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static controller.RegisterController.onlineUser;

public class DeckController {
    private static DeckController instance = null;
    private Object cardSort;

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
            DatabaseController.updatePlayer(onlineUser);
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

    public void addCardToDeck(String cardName, String deckName, boolean isMainDeck) throws Exception{
        Card card = Card.getCardByName(cardName);
        if (card != null) {
            if (DatabaseController.doesDeckExists(deckName)) {
                Deck deck = DatabaseController.getDeckByName(deckName);
                if (isMainDeck) {
                    if (deck.getMainDeck().size() < 60) {
                        if (onlineUser.getPlayerCards().contains(cardName)) {
                            if (deck.checkCardsLimit(card)) {
                              deck.addCardToMainDeck(card);
                              DatabaseController.updateDeck(deck);
                            } else
                               throw new CardNumberLimit(cardName, deckName);
                        } else
                            throw new PlayerCardNotExist();
                    } else
                        throw new MainDeckIsFull();
                } else {
                    if (deck.getSideDeck().size() < 15) {
                        if (onlineUser.getPlayerCards().contains(cardName)) {
                            if (deck.checkCardsLimit(card)) {
                                deck.addCardToSideDeck(card);
                                DatabaseController.updateDeck(deck);
                            } else
                                throw new CardNumberLimit(cardName, deckName);
                        } else
                            throw new PlayerCardNotExist();
                    } else
                        throw new SideDeckIsFull();
                }
            } else
                throw new DeckNotExists(deckName);
        } else
            throw new CardNameNotExists(cardName);
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isMainDeck) throws CardNameNotExists,
            DeckNotExists, CardNotInDeck, IOException {
        Card card = Card.getCardByName(cardName);
        if (card != null) {
            if (DatabaseController.doesDeckExists(deckName)) {
                Deck deck = DatabaseController.getDeckByName(deckName);
                if (isMainDeck) {
                    if (deck.removeCardFromMainDeck(card)) {
                        DatabaseController.updateDeck(deck);
                    } else
                        throw new CardNotInDeck(cardName, "main");
                } else {
                    if (deck.removeCardFromSideDeck(card)) {
                        DatabaseController.updateDeck(deck);
                    } else
                        throw new CardNotInDeck(cardName, "side");
                }
            } else
                throw new DeckNotExists(deckName);
        } else
            throw new CardNameNotExists(cardName);
    }

    public ArrayList<Deck> showAllDecks() throws IOException {
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

    public ArrayList<Card> showDeckByName(String name, boolean isMainDeck) throws DeckNotExists, IOException {
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
        ArrayList<Card> cards = new ArrayList<>();
        for (String name : cardsName) {
            cards.add(Card.getCardByName(name));
        }
        Collections.sort(cards, new cardSort());
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
