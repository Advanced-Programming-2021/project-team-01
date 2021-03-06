package model.commands;

import controller.GameController;
import javafx.collections.FXCollections;
import model.Board;
import model.card.Card;
import model.card.MonsterCard;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class TheTricky extends Command implements Activate{

    public TheTricky(Card card) {
        super(card);
    }

    public void run() throws Exception {
        Board board = GameController.getInstance().getGameBoard();
        List<Card> hand;
        if (gameController.getCurrentPlayerNumber() == 1){
            hand = board.getPlayerOneHand();
        }else {
            hand = board.getPlayerTwoHand();
        }
        hand.remove(myCard);
        List<Card> appropriateCards = FXCollections.observableArrayList();
        for (Card card : hand) {
            if (card instanceof MonsterCard)
                appropriateCards.add(card);
        }
        List<Card> selected = GameView.getNeededCards(appropriateCards,1);
        board.sendCardFromHandToGraveYard(gameController.getCurrentPlayerNumber(),selected.get(0));
        GameController.getInstance().resetSummon();
        board.summonCard((MonsterCard) myCard, gameController.getCurrentPlayerNumber());
    }

    public boolean canActivate(){
        Board board = GameController.getInstance().getGameBoard();
        List<Card> hand;
        if (gameController.getCurrentPlayerNumber() == 1){
            hand = board.getPlayerOneHand();
        }else {
            hand = board.getPlayerTwoHand();
        }
        for (Card card : hand) {
            if (card instanceof MonsterCard && card != myCard){
                return true;
            }
        }
        return false;
    }
}
