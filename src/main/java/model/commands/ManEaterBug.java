package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import console.menu.GameView;

import java.util.ArrayList;

public class ManEaterBug extends Command implements Activate{
    Board board;
    public ManEaterBug(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        ArrayList<Card> monsterCards = board.getCardInMonsterZone(gameController.getOpponentPlayerNumber());
        GameView.printListOfCardOpponent(monsterCards);
        Card card = view.GameView.getNeededCards(monsterCards, 1).get(0);
        board.sendCardFromMonsterZoneToGraveyard(card);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        ArrayList<Card> monsterCards = board.getCardInMonsterZone(gameController.getOpponentPlayerNumber());
        boolean canActivate = monsterCards.size() != 0;
        return gameController.getState() == State.FLIP_SUMMON && canActivate;
    }
}
