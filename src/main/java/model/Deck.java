package model;

import com.google.gson.Gson;
import model.card.Card;
import model.card.MonsterCard;

import java.util.ArrayList;

public class Deck {
    private String deckName;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;

    public static void main(String[] args) {
        Gson gson = new Gson();
        Deck deck = new Deck();
        deck.deckName = "";
        deck.mainDeck = null;
        deck.sideDeck = null;
        System.out.println(gson.toJson(deck));
    }
    public Deck() {

    }

    public void addCardToMainDeck(Card card) {

    }

    public void addCardToSideDeck(Card card) {

    }

    public void removeCardFromMainDeck(Card card) {

    }

    public void removeCardFromSideDeck(Card card) {

    }

    public String getDeckName() {
        return deckName;
    }
}
