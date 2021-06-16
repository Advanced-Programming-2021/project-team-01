package model.commands;

import controller.exceptions.NoFieldSpellInDeck;
import controller.exceptions.SpellZoneFullError;
import model.Board;
import model.card.Card;
import model.card.SpellCard;

public class FieldCard extends Command implements Activate{
    Board board;

    public FieldCard(Card card) {
        super(card);
    }

    @Override
    public void run() throws SpellZoneFullError {
        board = gameController.getGameBoard();
        board.setSpell(gameController.getCurrentPlayerNumber(), (SpellCard) gameController.getSelectedCard().getCard());
    }

    public boolean canActivate() throws Exception {
        return true;
    }
}
