package model.commands;

import model.Board;
import model.ZoneSlot;
import model.card.CardLocation;
import view.menu.GameView;

public class EquipNormal extends Command implements Activate {

    @Override
    public void run() throws Exception {
        Board board = gameController.getGameBoard();
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0) {
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER, indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        zoneSlot.setEquippedCard(gameController.getSelectedCard().getCard());
        board.setSpellFaceUp(gameController.getSelectedCard().getCard());
    }
}
