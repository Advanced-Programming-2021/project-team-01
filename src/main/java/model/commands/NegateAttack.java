package model.commands;

import controller.ChainController;
import model.Board;
import model.State;
import model.card.Card;

public class NegateAttack extends Command implements Activate {
    Board board;
    Card myCard;
    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        myCard = gameController.getSelectedCard().getCard();
        board.setSpellFaceUp(myCard);
        gameController.setState(State.NONE);
        board.sendCardFromSpellZoneToGraveyard(board.getOwnerOfCard(myCard),
                myCard);
    }

    @Override
    public void runContinuous() throws Exception {

    }

    @Override
    public boolean canActivate() throws Exception {
        return gameController.getState() == State.ATTACK &&
                board.getOwnerOfCard(gameController.getAttackController().getAttacker()) == gameController.getOpponentPlayerNumber();
    }
}
