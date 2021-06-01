package model.commands;

import model.Board;
import model.GamePhase;
import model.State;
import model.ZoneSlot;
import model.card.Card;

public class SwordOfRevealingLight extends Command implements Activate{
    int counter = 0;
    Board board;

    public SwordOfRevealingLight(Card card) {
        super(card);
    }

    @Override
    public void run() {
        board = gameController.getGameBoard();
        ZoneSlot[] zoneSlots = board.getPlayerMonsterZone(gameController.getOpponentPlayerNumber());
        for (int i = 1; i <= 5; i++) {
            if (zoneSlots[i].getCard() != null && zoneSlots[i].isHidden())
                zoneSlots[i].setHidden(false);
        }
    }

    @Override
    public void runContinuous() throws Exception {
        board = gameController.getGameBoard();
        if (counter == 3 && gameController.getGamePhase() == GamePhase.END_PHASE) {
            board.sendCardFromSpellZoneToGraveyard(myCard);
            return;
        }
        if (gameController.getGamePhase() == GamePhase.DRAW_PHASE &&
                gameController.getCurrentPlayerNumber() != board.getOwnerOfCard(myCard))
            counter++;
    }

    @Override
    public boolean canActivate() throws Exception {
        return gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
    }
}
