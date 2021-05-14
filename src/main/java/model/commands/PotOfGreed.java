package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;

public class PotOfGreed extends Command implements Activate{
    Board board;

    public PotOfGreed(Card card) {
        super(card);
    }

    @Override
    public void run(){
        board = gameController.getGameBoard();
        int playerNumber = gameController.getCurrentPlayerNumber();
        board.addCardFromDeckToHand(playerNumber);
        board.addCardFromDeckToHand(playerNumber);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        if(!(gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE2)||
        gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE1))){
            return false;
        }
        if (gameController.getCurrentPlayerNumber() == 1){
            return board.getPlayerOneHand().size() == 9;
        }
        return board.getPlayerTwoHand().size() == 9;


    }
}

