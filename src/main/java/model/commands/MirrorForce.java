package model.commands;

import model.Board;
import model.State;
import model.card.Card;

public class MirrorForce extends Command implements Activate {
    Board board;
    Card myCard;

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        myCard = gameController.getSelectedCard().getCard();
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        myCard = gameController.getSelectedCard().getCard();
        return gameController.getState() == State.ATTACK &&
                board.getOwnerOfCard(gameController.getAttackController().getAttacker()) == gameController.getAttackController().getAttackerNumber();
    }
}
