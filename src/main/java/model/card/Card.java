package model.card;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    private static HashMap<String, Card> allCards = new HashMap<>();
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
