package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.GamePhase;
import model.card.Card;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class MonsterReborn extends Command implements Activate {
    Board board;

    public MonsterReborn(Card card) {
        super(card);
    }

    @Override
    public void run() throws InvalidCommandException, MonsterZoneFull {
        board = gameController.getGameBoard();
        List<Card> graveYard = new ArrayList<>();
        graveYard.addAll(board.getPlayerOneGraveYard());
        graveYard.addAll(board.getPlayerTwoGraveYard());
        Card card = GameView.getNeededCards(graveYard, 1).get(0);
        board.addCardFromGraveYardToField(gameController.getCurrentPlayerNumber(), card);
        board.getGraveyard(1).remove(card);
        board.getGraveyard(2).remove(card);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        boolean canActivate;
        canActivate = board.getPlayerTwoGraveYard().size() + board.getPlayerOneGraveYard().size() != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
        return canActivate && correctPhase;
    }
}
