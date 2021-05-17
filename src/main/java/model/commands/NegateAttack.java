package model.commands;

import model.Board;
import model.State;
import model.card.Card;

public class NegateAttack extends Command implements Activate {
    Board board;

    public NegateAttack(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        gameController.setState(State.NONE);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        return gameController.getState() == State.ATTACK &&
                board.getOwnerOfCard(gameController.getAttackController().getAttacker()) == gameController.getOpponentPlayerNumber();
    }


}
