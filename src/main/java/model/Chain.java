package model;

import controller.GameController;
import model.card.Card;

import java.util.ArrayList;

public class Chain {
    private final ArrayList<Card> chainElements;
    int counter;

    public Chain() {
        chainElements = new ArrayList<>();
        counter = 0;
    }

    public void run() throws Exception {
        for (int i = chainElements.size() - 1; i >= 0; i--) {
            chainElements.get(i).doActions();
        }
        GameController.getInstance().resetChainController();
    }

    public boolean doesExistInChain(Card card){
        return chainElements.contains(card);
    }

    public void setNext(Card card) {
        chainElements.add(card);
        counter++;
    }

    public ArrayList<Card> getChainElements() {
        return chainElements;
    }

    public Card getPrev(){
        return chainElements.get(counter - 1);
    }


}
