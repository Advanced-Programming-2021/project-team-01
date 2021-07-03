package model;

import controller.RegisterController;
import model.card.Card;
import model.card.SpellCard;
import model.card.TrapCard;

import java.util.ArrayList;

public class Deck {
    private final String deckName;
    private final ArrayList<Card> mainDeck = new ArrayList<>();
    private final ArrayList<Card> sideDeck = new ArrayList<>();

    public Deck(String name) {
        this.deckName = name;
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.add(card);
    }

    public void addCardToSideDeck(Card card) {
        sideDeck.add(card);
    }

    public boolean removeCardFromMainDeck(Card card) {
        for (int i = 0; i < mainDeck.size(); i++) {
            if (mainDeck.get(i).getName().equals(card.getName())) {
                mainDeck.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeCardFromSideDeck(Card card) {
        for (int i = 0; i < sideDeck.size(); i++) {
            if (sideDeck.get(i).getName().equals(card.getName())) {
                sideDeck.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean isDeckValid() {
        return mainDeck.size() >= 25;
    }

    public String getDeckName() {
        return deckName;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public boolean checkCardsLimit(Card card) {
        int counter = 0, cardsOfPlayer = 0, cardsInDeck = 0;
        for (Card iteratorCard : mainDeck)
            if (iteratorCard.getName().equals(card.getName())) counter++;
        for (Card iteratorCard : sideDeck)
            if (iteratorCard.getName().equals(card.getName())) counter++;
        for (String cardName : RegisterController.onlineUser.getPlayerCards())
            if (card.getName().equals(cardName)) cardsOfPlayer++;
        for (Card cardName : getMainDeck())
            if (card.getName().equals(cardName.getName())) cardsInDeck++;
        for (Card cardName : getSideDeck())
            if (card.getName().equals(cardName.getName())) cardsInDeck++;

        if (card instanceof TrapCard) {
            if (((TrapCard) card).getLimitationStatus().equals("Limited"))
                return counter == 0 && (cardsOfPlayer - cardsInDeck >= 0);
        } else if (card instanceof SpellCard) {
            if (((SpellCard) card).getLimitationStatus().equals("Limited"))
                return counter == 0 && (cardsOfPlayer - cardsInDeck >= 0);
        }
        return counter < 3 && (cardsOfPlayer - cardsInDeck >= 0);
    }
}
