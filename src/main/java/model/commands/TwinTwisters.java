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
            GameView.printListOfCard(cardsInHand);
            int indexCardInHand = GameView.getValidNumber(0, cardsInHand.size() - 1);
            shouldRemove = cardsInHand.get(indexCardInHand);
            ArrayList<Card> spellCards = board.getCardInSpellZone(gameController.getOpponentPlayerNumber());
            GameView.printListOfCardOpponent(spellCards);
            if (spellCards.size() == 1) {
                GameView.showConsole("Card 0 selected.");
                t1 = spellCards.get(0);
            } else {
                int index1 = GameView.getValidNumber(0, spellCards.size() - 1);
                t1 = spellCards.get(index1);
                int index2;
                do {
                    index2 = GameView.getValidNumber(0, spellCards.size() - 1);
                } while (index1 == index2);
                t2 = spellCards.get(index2);
            }
            return true;
        }
        return false;
    }
}
