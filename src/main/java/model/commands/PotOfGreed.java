package model.commands;

import model.Board;
import model.card.Card;

public class PotOfGreed extends Command implements Activate{
    Board board;

    public PotOfGreed(Card card) {
        super(card);
    }

    @Override
    public void run(){
        board = gameController.getGameBoard();
        int playerNumber = gameController.getCurrentPlayerNumber();
        board.addCardFromDeckToHand(playerNumber);
        board.addCardFromDeckToHand(playerNumber);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }
}

