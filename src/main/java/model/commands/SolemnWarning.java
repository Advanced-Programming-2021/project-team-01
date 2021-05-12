package model.commands;

import model.Board;
import model.State;
import model.card.Card;

public class SolemnWarning extends Command implements Activate {
    Board board;

    public SolemnWarning(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        //fixme : special summon
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        board.sendCardFromMonsterZoneToGraveyard(gameController.getSummonedCard());
        if (board.getOwnerOfCard(myCard) == 1)
            gameController.decreasePlayerLP(1, 2000);
        else
            gameController.decreasePlayerLP(2, 2000);
        gameController.setState(State.NONE);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        return gameController.getState() == State.SUMMON ||
                gameController.getState() == State.SPECIAL_SUMMON;
    }
}
