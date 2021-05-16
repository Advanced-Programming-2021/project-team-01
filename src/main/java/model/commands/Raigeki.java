package model.commands;

import model.Board;
import model.ZoneSlot;
import model.card.Card;

public class Raigeki extends Command implements Activate{
    Board board;

    public Raigeki(Card card) {
        super(card);
    }

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
            board.sendCardFromSpellZoneToGraveyard(myCard);
        } else {
            ZoneSlot[] zoneSlot = board.getPlayerOneMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            board.sendCardFromSpellZoneToGraveyard(myCard);
        }
    }

    @Override
    public boolean canActivate() throws Exception {
        int number = gameController.getOpponentPlayerNumber();
        if (number == 1){
            return gameController.getGameBoard().numberOfMonsterCards(1) != 0;
        }
        return gameController.getGameBoard().numberOfMonsterCards(2) != 0;
    }
}
