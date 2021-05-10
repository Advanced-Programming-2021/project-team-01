package model.commands;

import model.Board;
import model.ZoneSlot;
import model.card.Card;

public class DarkHole extends Command implements Activate {
    Board board;

    public DarkHole(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        ZoneSlot[] zoneSlot;
        if (gameController.getCurrentPlayerNumber() == 1) {
            zoneSlot = board.getPlayerTwoMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, 2);
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
        } else {
            zoneSlot = board.getPlayerOneMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, 1);
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
        }
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }
}