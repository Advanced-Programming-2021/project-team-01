package model.commands;

import model.Board;
import model.ZoneSlot;
import model.card.Card;
import model.card.CardLocation;
import console.menu.GameView;
import model.card.MonsterCard;
import model.card.MonsterType;

import java.util.ArrayList;
import java.util.List;

public class EquipNormal extends Command implements Activate {

    public EquipNormal(Card card) {
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
            monsters.add(monsterZone[i].getCard());
        }
        List<Card> result = view.GameView.getNeededCards(monsters, 1);
        Card selected = result.get(0);
        board.getZoneSlotByCard(selected).setEquippedCard(gameController.getSelectedCard().getCard());
        System.out.println(gameController.getSelectedCard().getCard().getName());
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
