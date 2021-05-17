package model.commands;

import controller.GameController;
import model.Board;
import model.card.Card;
import model.card.MonsterCard;
import view.menu.GameView;

import java.util.ArrayList;

public class TheTricky extends Command implements Activate{

    public TheTricky(Card card) {
        super(card);
    }

    public void run() throws Exception {
        if (!canActivate()){
            throw new Exception("You cant activate this card");
        }
        Board board = GameController.getInstance().getGameBoard();
        ArrayList<Card> hand;
        if (gameController.getCurrentPlayerNumber() == 1){
            hand = board.getPlayerOneHand();
        }else {
            hand = board.getPlayerTwoHand();
        }
        while (true) {
            int input = Integer.parseInt(GameView.prompt("Choose a monster from your hand"));
            if (input > hand.size()){
                GameView.showConsole("invalid selection");
                continue;
            }
            if (!(hand.get(input - 1) instanceof MonsterCard)){
                GameView.showConsole("Please choose a monster!");
                continue;
            }
            board.sendCardFromHandToGraveYard(gameController.getCurrentPlayerNumber(),hand.get(input - 1));
            board.summonCard((MonsterCard) myCard, gameController.getCurrentPlayerNumber());
            break;
        }
    }

    public boolean canActivate(){
        Board board = GameController.getInstance().getGameBoard();
        ArrayList<Card> hand;
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
