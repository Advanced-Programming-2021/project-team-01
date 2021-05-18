package model.commands;

import model.Board;
import model.GamePhase;
import model.State;
import model.card.Card;

import java.util.ArrayList;

public class SpellAbsorption extends Command implements Activate {
    Board board;

    public SpellAbsorption(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        myCard = gameController.getSelectedCard().getCard();
        board.setSpellFaceUp(myCard);
    }

    @Override
    public void runContinuous() throws Exception {
        if (gameController.getState() == State.ACTIVE_SPELL) {
            gameController.decreasePlayerLP(board.getOwnerOfCard(myCard), -500);
        }
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        return gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
    }
}