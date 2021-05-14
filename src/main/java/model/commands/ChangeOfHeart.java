package model.commands;

import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.GamePhase;
import model.card.Card;
import model.card.MonsterCard;
import view.menu.GameView;

import java.util.ArrayList;

public class ChangeOfHeart extends Command implements Activate {
    Card target;
    Board board;

    public ChangeOfHeart(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0)
            throw new MonsterZoneFull();
        if (board.numberOfMonsterCards(gameController.getOpponentPlayerNumber()) == 0)
            throw new Exception("Monster Zone of your opponent is Empty");
        ArrayList<Card> monsterCards = board.getCardInMonsterZone(gameController.getOpponentPlayerNumber());
        GameView.printListOfCardOpponent(monsterCards);
        int index = GameView.getValidNumber(0, monsterCards.size() - 1);
        target = monsterCards.get(index);
        board.sendCardFromMonsterZoneToAnother(target, gameController.getOpponentPlayerNumber()
                , gameController.getCurrentPlayerNumber());
    }

    public void runContinuous() {
        GamePhase gamePhase = gameController.getGamePhase();
        if (gamePhase == GamePhase.END_PHASE) {
            if (board.isCardInMonsterZone((MonsterCard) target))
                board.sendCardFromMonsterZoneToAnother(target, gameController.getCurrentPlayerNumber(),
                        gameController.getOpponentPlayerNumber());
            board.sendCardFromSpellZoneToGraveyard(myCard);
        }
    }

    @Override
    public boolean canActivate() throws Exception {
        return true;
    }
}
