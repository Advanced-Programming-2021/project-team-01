package model.commands;

import model.Board;
import model.ZoneSlot;

public class Raigeki extends Command implements Activate{
    Board board;
    @Override
    public void run() {
        board = gameController.getGameBoard();
        if (gameController.getCurrentPlayerNumber() == 1) {
            ZoneSlot[] zoneSlot = board.getPlayerTwoMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
        } else {
            ZoneSlot[] zoneSlot = board.getPlayerOneMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
        }
    }
}
