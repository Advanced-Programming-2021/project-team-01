package model.commands;

import model.Board;

public class PotOfGreed extends Command implements Activate{
    Board board;
    @Override
    public void run(){
        board = gameController.getGameBoard();
        int playerNumber = gameController.getCurrentPlayerNumber();
        board.addCardFromDeckToHand(playerNumber);
        board.addCardFromDeckToHand(playerNumber);
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(),
                gameController.getSelectedCard().getCard());
    }
}

