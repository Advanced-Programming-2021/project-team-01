package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;

import java.util.ArrayList;

public class SupplySquad extends Command implements Activate {
    Board board;
    Card myCard;

    public SupplySquad(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        myCard = gameController.getSelectedCard().getCard();
        board.setSpellFaceUp(myCard);
    }

    @Override
    public void runContinuous() throws Exception {
        if (gameController.getGamePhase() == GamePhase.END_PHASE) {
            ArrayList<Card> cards = gameController.getDestroyedCardsPlayer(board.getOwnerOfCard(myCard));
            if (cards.size() != 0)
                board.addCardFromDeckToHand(board.getOwnerOfCard(myCard));
        }
    }
}
