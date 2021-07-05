package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import model.card.MonsterCard;
import console.menu.GameView;

import java.util.ArrayList;
import java.util.List;

public class GateGuardian extends Command implements Activate {
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
        monsterCards.remove(myCard);
        List<Card> cards = view.GameView.getNeededCards(monsterCards, 3);
        board.sendCardFromMonsterZoneToGraveyard(cards.get(0));
        board.sendCardFromMonsterZoneToGraveyard(cards.get(1));
        board.sendCardFromMonsterZoneToGraveyard(cards.get(2));
        State temp = gameController.getState();
        gameController.setState(State.SPECIAL_SUMMON);
        board.summonCard((MonsterCard) myCard, player);
        gameController.setState(temp);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        int player = board.getOwnerOfCard(myCard);
        return  (board.numberOfMonsterCards(player) < 3);
    }
}
