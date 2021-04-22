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
        mainDeck.add(card);//todo remeove
    }

    public void addCardToSideDeck(Card card) {

    }

    public void removeCardFromMainDeck(Card card) {

    }

    public void removeCardFromSideDeck(Card card) {

    }

    public boolean isDeckValid(){
        return true;
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
}
