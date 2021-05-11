package model;

import model.card.Card;

import java.util.ArrayList;

public class Chain {
    private final ArrayList<Card> chainElements;

    public Chain() {
        chainElements = new ArrayList<>();
    }

    public void run() throws Exception {
        for (int i = chainElements.size() - 1; i >= 0; i--) {
            chainElements.get(i).doActions();
        }
    }

    public boolean doesExistInChain(Card card){
        return chainElements.contains(card);
    }

    public void setNext(Card card) {
        chainElements.add(card);
    }
}
