package model.commands;

import model.Board;
import model.ZoneSlot;
import model.card.Card;
import model.card.CardLocation;
import model.card.MonsterCard;
import model.card.MonsterType;
import console.menu.GameView;

import java.util.ArrayList;
import java.util.List;

public class EquipWarrior extends Command implements Activate{
    Board board = gameController.getGameBoard();

    public EquipWarrior(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        Board board = gameController.getGameBoard();
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0) {
            throw new Exception("no monster card");
        }
        ArrayList<Card> monsters = new ArrayList<>();
        ZoneSlot[] monsterZone = board.getPlayerMonsterZone(gameController.getCurrentPlayerNumber());
        for (int i = 1; i < 6; i++) {
            if (monsterZone[i].getCard() == null)
                continue;
            if (((MonsterCard) monsterZone[i].getCard()).getMonsterTypes().contains(MonsterType.WARRIOR))
                monsters.add(monsterZone[i].getCard());
        }
        List<Card> result = view.GameView.getNeededCards(monsters, 1);
        Card selected = result.get(0);
        board.getZoneSlotByCard(selected).setEquippedCard(gameController.getSelectedCard().getCard());
        board.setSpellFaceUp(gameController.getSelectedCard().getCard());
    }

    public boolean canActivate() throws Exception {
        int number = gameController.getCurrentPlayerNumber();
        if (number == 1){
            return gameController.getGameBoard().numberOfMonsterCards(1) != 0;
        }
        return gameController.getGameBoard().numberOfMonsterCards(2) != 0;
    }
}
