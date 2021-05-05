package controller;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.card.Card;
import model.card.CardLocation;
import view.menu.GameView;

import java.util.ArrayList;

public class EffectController {
    GameController gameController;
    Board board;
    public EffectController(GameController gameController) {
        this.gameController = gameController;
        board = gameController.gameBoard;
    }

    protected void run(Spell spell) throws Exception {
        if (spell == Spell.MONSTER_REBORN){
            monsterReborn();
        }else if (spell == Spell.POT_OF_GREED){
            potOfGreed();
        }else if (spell == Spell.RAIGEKI){
            raigeki();
        }else if (spell == Spell.TERRAFORMING){
            terraforming();
        }

    }


    protected void monsterReborn() throws InvalidCommandException, MonsterZoneFull {
        String grave = GameView.prompt("player/opponent");
        ArrayList<Card> graveYard;
        if (grave.equals("player")){
            if (gameController.getCurrentPlayerNumber() == 1) graveYard = board.getPlayerOneGraveYard();
            else graveYard = board.getPlayerTwoGraveYard();
        }else if (grave.equals("opponent")){
            if (gameController.getCurrentPlayerNumber() == 1) graveYard = board.getPlayerTwoGraveYard();
            else graveYard = board.getPlayerOneGraveYard();
        }else {
            throw new InvalidCommandException();
        }
        int index = 0;
        for (Card card : graveYard) { //fixme : mvc bam
            System.out.print(index + "|" + card.getName() + " : " + card.getDescription());
            index++;
        }
        System.out.println();
        int cardIndex = Integer.parseInt(GameView.prompt("chose specified index"));
        if (cardIndex > index) throw new InvalidCommandException();
        Card card = graveYard.get(cardIndex);
        board.addCardFromGraveYardToField(gameController.getCurrentPlayerNumber(), card);
        graveYard.remove(card);
        board.sendCardFromHandToGraveYard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    protected void potOfGreed(){
        int playerNumber = gameController.getCurrentPlayerNumber();
        board.addCardFromDeckToHand(playerNumber);
    }
}

/*
    1- battle ox
    2- dadbeh  *|\+2+/|*
    3- erfan
    4-....
 */

