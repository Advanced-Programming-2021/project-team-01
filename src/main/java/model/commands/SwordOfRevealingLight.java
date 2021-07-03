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
        board.getZoneSlotByCard(myCard).setHidden(false);
        ZoneSlot[] zoneSlots = board.getPlayerMonsterZone(gameController.getOpponentPlayerNumber());
        for (int i = 1; i <= 5; i++) {
            if (zoneSlots[i].getCard() != null && zoneSlots[i].isHidden())
                zoneSlots[i].setHidden(false);
        }
    }

    @Override
    public void runContinuous() throws Exception {
        board = gameController.getGameBoard();
        if (gameController.getGamePhase() == GamePhase.DRAW_PHASE &&
                gameController.getOpponentPlayerNumber() == board.getOwnerOfCard(myCard)) {
            counter++;
        }
        if (counter == 2 && gameController.getGamePhase() == GamePhase.END_PHASE &&
            gameController.getOpponentPlayerNumber() == board.getOwnerOfCard(myCard)) {
            board.sendCardFromSpellZoneToGraveyard(myCard);
        }
    }

    @Override
    public boolean canActivate() throws Exception {
        return gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.END_PHASE ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
    }
}
