package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.card.Card;
import model.card.MonsterCard;
import model.card.Property;
import model.card.TrapCard;

import java.util.ArrayList;

public class Deck {
    private String deckName;
    private ArrayList<Card> mainDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();

    public Deck(String name) {
        this.deckName = name;
    }

    public void addCardToMainDeck(Card card) {
        mainDeck.add(card);
    }

    public void addCardToSideDeck(Card card) {
        sideDeck.add(card);
    }

    public void removeCardFromMainDeck(Card card) {
        mainDeck.remove(card);
    }

    public void removeCardFromSideDeck(Card card) {
        sideDeck.remove(card);
    }

    public boolean isDeckValid(){
        return mainDeck.size() >= 40;
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
        int counter = 0;
        for (Card iteratorCard : mainDeck) {
            if (iteratorCard.equals(card))
                counter++;
        }
        for (Card iteratorCard : sideDeck) {
            if (iteratorCard.equals(card))
                counter++;
        }
        return counter < 3;
    }
}
