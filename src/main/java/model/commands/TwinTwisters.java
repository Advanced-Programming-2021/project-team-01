package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.GamePhase;
import model.ZoneSlot;
import model.card.Card;
import view.menu.GameView;

import java.util.ArrayList;

public class TwinTwisters extends Command implements Activate {
    Board board;

    public TwinTwisters(Card card) {
        super(card);
    }
    Card t1;
    Card t2;

    @Override
    public void run() throws InvalidCommandException, MonsterZoneFull, Exception {
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        ArrayList<Card> cardsInHand = new ArrayList<>();





        boolean canActivate = board.numberOfSpellAndTrapCards(gameController.getOpponentPlayerNumber()) != 0 ||
                board.getNumberOfCardsInHand(gameController.getCurrentPlayerNumber()) != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
        //TODO check me
        return canActivate && correctPhase;
    }
}
