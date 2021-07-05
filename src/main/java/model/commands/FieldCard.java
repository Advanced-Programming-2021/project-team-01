package model.commands;

import controller.exceptions.SpellZoneFullError;
import model.Board;
import model.card.Card;
import model.card.SpellCard;

public class FieldCard extends Command implements Activate {
    Board board;

    public FieldCard(Card card) {
        super(card);
    }

    @Override
    public void run() throws SpellZoneFullError {
        board = gameController.getGameBoard();
        if (board.getZoneSlotByCard(myCard) == null)
            board.setSpell(gameController.getCurrentPlayerNumber(), (SpellCard) gameController.getSelectedCard().getCard());
        board.setSpellFaceUp(myCard);
    }

    public boolean canActivate() throws Exception {
        return true;
    }
}
