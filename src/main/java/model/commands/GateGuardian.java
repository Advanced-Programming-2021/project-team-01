package model.commands;

import model.Board;
import model.card.Card;
import model.card.MonsterCard;
import view.menu.GameView;

import java.util.ArrayList;

public class GateGuardian extends Command implements Activate{
    Board board;
    public GateGuardian(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        int player = board.getOwnerOfCard(myCard);
        if (board.numberOfMonsterCards(player) < 3)
            throw new Exception("You don't have enough Monster!");
        ArrayList<Card> monsterCards = board.getCardInMonsterZone(player);
        GameView.printListOfCard(monsterCards);
        int index1 = -1;
        int index2 = -1;
        int index3 = -1;
        index1 = GameView.getValidNumber(0,monsterCards.size());
        do {
            index2 = GameView.getValidNumber(0, monsterCards.size());
        } while (index2 == index1);
        do {
            index3 = GameView.getValidNumber(0, monsterCards.size());
        } while (index3 == index1 || index3 == index2);
        board.sendCardFromMonsterZoneToGraveyard(index1,player);
        board.sendCardFromMonsterZoneToGraveyard(index2,player);
        board.sendCardFromMonsterZoneToGraveyard(index3,player);
        board.summonCard((MonsterCard) myCard,player);
    }
}