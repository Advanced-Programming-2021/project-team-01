package model.commands;

import model.Board;
import model.card.Card;

public class Suijin extends Command implements Activate {
    boolean actionable = true;
    Board board;

    public Suijin(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        actionable = false;
        gameController.getAttackController().setDamage(-1 * board.getZoneSlotByCard(myCard).getAttack());
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        return actionable && !board.getZoneSlotByCard(myCard).isHidden();
    }
}
