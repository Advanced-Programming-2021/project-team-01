package model.commands;

import model.Board;
import model.ZoneSlot;

public class HarpiesFeatherDuster extends Command implements Activate {
    Board board;
    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        ZoneSlot[] zoneSlot = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber());
        for (int i = 1; i < 6; i++) {
            if (zoneSlot[i].getCard() != null) {
                board.sendCardFromSpellZoneToGraveyard(i, zoneSlot[i].getCard());
                zoneSlot[i].setHidden(false);
            }
        }
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
    }
    @Override
    public void runContinuous() throws Exception {

    }
}
