package model.commands;

import model.Board;
import model.GamePhase;
import model.ZoneSlot;
import model.card.Card;

public class HarpiesFeatherDuster extends Command implements Activate {
    Board board;

    public HarpiesFeatherDuster(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        ZoneSlot[] zoneSlot = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber());
        for (int i = 1; i < 6; i++) {
            if (zoneSlot[i].getCard() != null) {
                board.sendCardFromSpellZoneToGraveyard(zoneSlot[i].getCard());
                zoneSlot[i].setHidden(false);
            }
        }
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        boolean canActivate = board.numberOfSpellAndTrapCards(gameController.getOpponentPlayerNumber()) != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
        return canActivate && correctPhase;
    }
}
