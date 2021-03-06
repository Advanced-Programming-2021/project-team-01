package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;
import view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MindCrush extends Command implements Activate {

    Board board;

    public MindCrush(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        board.setSpellFaceUp(myCard);
        List<String> nameOfAllCards = new ArrayList<>();
        for (Card card : Card.getAllCards().values()) {
             nameOfAllCards.add(card.getName());
        }
        String nameOfCard = GameView.getChoiceBox(nameOfAllCards,"Choose a name Card");
        int currentPlayer = board.getOwnerOfCard(myCard);
        List<Card> cardsInHand;
        if (currentPlayer == 1)
            cardsInHand = board.getPlayerTwoHand();
        else
            cardsInHand = board.getPlayerOneHand();
        ArrayList<Card> cardsShouldBeRemove = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card.getName().equals(nameOfCard))
                cardsShouldBeRemove.add(card);
        }
        if (!cardsShouldBeRemove.isEmpty()) {
            for (Card card : cardsShouldBeRemove) {
                board.sendCardFromHandToGraveYard(board.getOwnerOfCard(card), card);
            }
        } else {
            if (currentPlayer == 1)
                cardsInHand = board.getPlayerOneHand();
            else
                cardsInHand = board.getPlayerTwoHand();
            Random random = new Random();
            int upperBound = cardsInHand.size();
            int index = random.nextInt(upperBound);
            Card card = cardsInHand.get(index);
            board.sendCardFromHandToGraveYard(board.getOwnerOfCard(card), card);
            GameView.showAlert(card.getName() + " send to graveyard.");
        }
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() {
        return gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
    }
}
