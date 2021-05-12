package model.commands;

import model.Board;
import model.State;
import model.ZoneSlot;
import model.card.Card;

public class CallOfTheHaunted extends Command implements Activate{
    Board board;

    public CallOfTheHaunted(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        return true;
    }

}
