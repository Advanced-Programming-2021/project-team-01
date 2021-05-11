package model.commands;

import controller.Effect;
import controller.GameController;
import model.Board;
import model.GamePhase;
import model.card.Card;
import view.menu.GameView;

import java.util.ArrayList;
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
        String input = GameView.prompt("Enter a Card Name: ");
        while (Card.getCardByName(input) == null)
            input = GameView.prompt("Enter a valid Card Name: ");
        int currentPlayer = board.getOwnerOfCard(myCard);
        ArrayList<Card> cardsInHand;
        if (currentPlayer == 1)
            cardsInHand = board.getPlayerTwoHand();
        else
            cardsInHand = board.getPlayerOneHand();
        ArrayList<Card> cardsShouldBeRemove = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card.getName().equals(input))
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
            GameView.showConsole(card.getName() + " send to graveyard.");
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
