package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;
import console.menu.GameView;

import java.util.ArrayList;

public class MysticalSpaceTyphoon extends Command implements Activate{
    Board board;
    Card shouldRemove;
    public MysticalSpaceTyphoon(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.sendCardFromSpellZoneToGraveyard(shouldRemove);
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
            ArrayList<Card> spellCards = board.getCardInSpellZone(gameController.getOpponentPlayerNumber());
            GameView.printListOfCardOpponent(spellCards);
            if (spellCards.size() == 1) {
                GameView.showConsole("Card 0 selected.");
                shouldRemove = spellCards.get(0);
            } else {
                int index1 = GameView.getValidNumber(0, spellCards.size() - 1);
                shouldRemove = spellCards.get(index1);
            }
            return true;
        }
        return false;
    }
}
