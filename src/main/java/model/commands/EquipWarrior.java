package model.commands;

import model.Board;
import model.ZoneSlot;
import model.card.CardLocation;
import model.card.MonsterCard;
import model.card.MonsterType;
import view.menu.GameView;

public class EquipWarrior extends Command implements Activate{
    Board board = gameController.getGameBoard();
    @Override
    public void run() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0) {
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER, indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        if (!((MonsterCard) zoneSlot.getCard()).getMonsterTypes().contains(MonsterType.WARRIOR)) {
            throw new Exception("only warrior type monsters are allowed");
        }
        zoneSlot.setEquippedCard(gameController.getSelectedCard().getCard());
        board.setSpellFaceUp(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
    }
    @Override
    public void runContinuous() throws Exception {

    }
}
