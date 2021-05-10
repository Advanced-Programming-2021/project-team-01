package model.commands;

import model.Board;
import model.State;
import model.ZoneSlot;
import model.card.Card;

public class MirrorForce extends Command implements Activate {
    Board board;

    public MirrorForce(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        gameController.setState(State.NONE);
        ZoneSlot[] monsterCardsOpponent;
        int player = gameController.getAttackController().getAttackerNumber();
        if (player == 1)
            monsterCardsOpponent = board.getPlayerOneMonsterZone();
        else
            monsterCardsOpponent = board.getPlayerTwoMonsterZone();
        for (int i = 1; i < 6; i++) {
            if (!monsterCardsOpponent[i].isHidden() && monsterCardsOpponent[i].getCard() != null &&
                    !monsterCardsOpponent[i].isDefending())
                board.sendCardFromMonsterZoneToGraveyard(i, player);
        }
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate(){
        board = gameController.getGameBoard();
        return gameController.getState() == State.ATTACK &&
                board.getOwnerOfCard(gameController.getAttackController().getAttacker()) == gameController.getAttackController().getAttackerNumber();
    }
}
