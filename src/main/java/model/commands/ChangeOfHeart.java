package model.commands;

import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.GamePhase;
import model.card.Card;
import model.card.MonsterCard;
import console.menu.GameView;

import java.util.ArrayList;
import java.util.List;

public class ChangeOfHeart extends Command implements Activate {
    Card target;
    Board board;

    public ChangeOfHeart(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 5)
            throw new MonsterZoneFull();
        if (board.numberOfMonsterCards(gameController.getOpponentPlayerNumber()) == 0)
            throw new Exception("Monster Zone of your opponent is Empty");
        board.getZoneSlotByCard(myCard).setHidden(false);
        ArrayList<Card> monsterCards = board.getCardInMonsterZone(gameController.getOpponentPlayerNumber());
        List<Card> result = view.GameView.getNeededCards(monsterCards,1);
        target = result.get(0);
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
        board = gameController.getGameBoard();
        boolean canActivate = board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) != 5 &&
                board.numberOfMonsterCards(gameController.getOpponentPlayerNumber()) != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;

        return correctPhase && canActivate;
    }
}
