package model.commands;

import model.Board;
import model.State;
import model.ZoneSlot;
import model.card.Card;

public class TorrentialTribute extends Command implements Activate {

    Board board;

    public TorrentialTribute(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
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

    @Override
    public boolean canActivate() throws Exception {
        return gameController.getState() == State.SUMMON;
    }
}
