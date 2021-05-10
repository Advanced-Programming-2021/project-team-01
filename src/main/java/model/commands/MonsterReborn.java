package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.card.Card;
import view.menu.GameView;

import java.util.ArrayList;

public class MonsterReborn extends Command implements Activate{

    public MonsterReborn(Card card) {
        super(card);
    }

    @Override
    public void run() throws InvalidCommandException, MonsterZoneFull {
        Board board = gameController.getGameBoard();
        String grave = GameView.prompt("player/opponent");
        ArrayList<Card> graveYard;
        if (grave.equals("player")) {
            if (gameController.getCurrentPlayerNumber() == 1) graveYard = board.getPlayerOneGraveYard();
            else graveYard = board.getPlayerTwoGraveYard();
        } else if (grave.equals("opponent")) {
            if (gameController.getCurrentPlayerNumber() == 1) graveYard = board.getPlayerTwoGraveYard();
            else graveYard = board.getPlayerOneGraveYard();
        } else {
            throw new InvalidCommandException();
        }
        int index = 0;
        GameView.printListOfCard(graveYard);
        System.out.println();
        int cardIndex = Integer.parseInt(GameView.prompt("chose specified index"));
        if (cardIndex > index) throw new InvalidCommandException();
        Card card = graveYard.get(cardIndex);
        board.addCardFromGraveYardToField(gameController.getCurrentPlayerNumber(), card);
        graveYard.remove(card);
        board.sendCardFromSpellZoneToGraveyard(myCard);

    }
}
