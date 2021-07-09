package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import model.card.MonsterCard;

public class TrapHole extends Command implements Activate {

    Board board;

    public TrapHole(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        board.sendCardFromMonsterZoneToGraveyard(gameController.getSummonedCard());
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        boolean isStatSummon = gameController.getState() == State.SUMMON || gameController.getState() == State.FLIP_SUMMON;
        if (!isStatSummon)
            return false;
        return isStatSummon && (((MonsterCard)(gameController.getSummonedCard())).getAttack() >= 1000);
    }
}