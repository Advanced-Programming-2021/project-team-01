package model.commands;

import controller.exceptions.NoFieldSpellInDeck;
import model.Board;

public class FieldCard extends Command implements Activate{
    Board board;
    @Override
    public void run(){
        board = gameController.getGameBoard();
        board.setCardFromHandToFieldZone(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
    }
}
