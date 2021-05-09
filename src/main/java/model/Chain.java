package model;

import model.card.Card;
import model.commands.Command;

import java.util.ArrayList;

public class Chain {
    private ArrayList<Card> chainElements;

    public Chain() {
        chainElements = new ArrayList<>();
    }

    public void run() throws Exception {
        for (Card card: chainElements) {
            card.doActions();
        }
    }

    public boolean doesExistInChain(Card card){
        return chainElements.contains(card);
    }

    public void setNext(Card card) {
        chainElements.add(card);
    }
}
