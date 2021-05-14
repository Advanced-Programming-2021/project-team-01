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
        zoneSlot = board.getPlayerTwoMonsterZone();
        for (int i = 1; i < 6; i++)
            if (zoneSlot[i].getCard() != null)
                board.sendCardFromMonsterZoneToGraveyard(i, 2);
        zoneSlot = board.getPlayerOneMonsterZone();
        for (int i = 1; i < 6; i++)
            if (zoneSlot[i].getCard() != null)
                board.sendCardFromMonsterZoneToGraveyard(i, 1);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    public boolean canActivate() throws Exception {
        int number = gameController.getOpponentPlayerNumber();
        if (number == 1){
            return gameController.getGameBoard().numberOfMonsterCards(2) != 0;
        }
        return gameController.getGameBoard().numberOfMonsterCards(1) != 0;
    }


}
