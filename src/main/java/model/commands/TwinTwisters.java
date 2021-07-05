package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;
import console.menu.GameView;

import java.util.ArrayList;
import java.util.List;

public class TwinTwisters extends Command implements Activate {
    Board board;
    Card t1 = null;
    Card t2 = null;
    Card shouldRemove;

    public TwinTwisters(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.sendCardFromSpellZoneToGraveyard(t1);
        board.sendCardFromSpellZoneToGraveyard(t2);
        board.sendCardFromHandToGraveYard(board.getOwnerOfCard(myCard),shouldRemove);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();


        boolean canActivate = board.numberOfSpellAndTrapCards(gameController.getOpponentPlayerNumber()) != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;


        if (canActivate && correctPhase) {
            List<Card> cardsInHand = board.getPlayerHand(gameController.getCurrentPlayerNumber());
            cardsInHand.remove(myCard);
            List<Card> selectForRemove = view.GameView.getNeededCards(cardsInHand,1);
            shouldRemove = selectForRemove.get(0);
            ArrayList<Card> spellCards = board.getCardInSpellZone(gameController.getOpponentPlayerNumber());
            if (spellCards.size() == 1) {
                t1 = spellCards.get(0);
            } else {
                List<Card> selectedSpells = view.GameView.getNeededCards(spellCards,2);
                t1 = selectedSpells.get(0);
                t2 = spellCards.get(1);
            }
            return true;
        }
        return false;
    }
}
