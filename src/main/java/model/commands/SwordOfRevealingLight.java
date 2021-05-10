package model.commands;

import model.card.Card;

public class SwordOfRevealingLight extends Command implements Activate{
    int counter = 0;

    public SwordOfRevealingLight(Card card) {
        super(card);
    }

    @Override
    public void run() {

    }
    @Override
    public void runContinuous() throws Exception {

    }
}
