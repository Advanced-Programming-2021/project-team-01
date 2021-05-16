package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import view.menu.GameView;

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
        int index = GameView.getValidNumber(0, monsterCards.size() - 1);
        Card card = monsterCards.get(index);
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
