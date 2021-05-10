package model.commands;

import controller.exceptions.NoFieldSpellInDeck;
import model.Board;
import model.card.Card;

public class FieldCard extends Command implements Activate{
    Board board;

    public FieldCard(Card card) {
        super(card);
    }

    @Override
    public void run(){
        board = gameController.getGameBoard();
        board.setCardFromHandToFieldZone(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
    }
}
