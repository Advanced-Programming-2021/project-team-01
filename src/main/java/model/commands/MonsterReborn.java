package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.GamePhase;
import model.card.Card;
import console.menu.GameView;

import java.util.ArrayList;

public class MonsterReborn extends Command implements Activate {
    Board board;

    public MonsterReborn(Card card) {
        super(card);
    }

    @Override
    public void run() throws InvalidCommandException, MonsterZoneFull {
        board = gameController.getGameBoard();
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

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        boolean canActivate;
        if (gameController.getCurrentPlayerNumber() == 1)
            canActivate = board.getPlayerOneGraveYard().size() != 0;
        else
            canActivate = board.getPlayerTwoGraveYard().size() != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
        return canActivate && correctPhase;
    }
}
