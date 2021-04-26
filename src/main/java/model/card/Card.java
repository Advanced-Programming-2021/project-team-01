package model.card;

import java.util.ArrayList;

public class Card {
    private static ArrayList<Card> allCards = new ArrayList<>();
    private String name;
    private String description;
    private int price;

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public static Card getCardByName(String name) {
        for (Card card : allCards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public static void addCardToDatabase(Card card){
        allCards.add(card);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
