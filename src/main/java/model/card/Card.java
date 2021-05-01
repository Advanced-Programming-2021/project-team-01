package model.card;

import java.util.HashMap;
import java.util.TreeMap;

public class Card {
    private static TreeMap<String, Card> allCards = new TreeMap<>();
    private String name;
    private String description;
    private int price;

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public static Card getCardByName(String name) {
        if (allCards.containsKey(name)){
            return allCards.get(name);
        }
        return null;
    }

    public int getPrice() {
        return price;
    }

    public static TreeMap<String, Card> getAllCards() {
        return allCards;
    }

    public static void addCardToDatabase(Card card){
        allCards.put(card.getName(),card);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
