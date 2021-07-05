package model.commands;

import controller.GameController;
import model.card.Card;

public abstract class
Command implements Activate { //Constructor:
    GameController gameController = GameController.getInstance();
    Card myCard;

    public Command(Card card){
        this.myCard = card;
    }

    public void run() throws Exception {

    }

    public void runContinuous() throws Exception {

    }

    public boolean canActivate() throws Exception {
        return false;
    }

}
