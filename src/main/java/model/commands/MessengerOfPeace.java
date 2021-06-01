package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;
import view.menu.GameView;

public class MessengerOfPeace extends Command implements Activate{
    Board board;

    public MessengerOfPeace(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
    }

    @Override
    public void runContinuous() throws Exception {
        board = gameController.getGameBoard();
        if (gameController.getGamePhase() == GamePhase.STANDBY_PHASE &&
                gameController.getCurrentPlayerNumber() == board.getOwnerOfCard(myCard)) {
            String response = GameView.prompt("1- Lose 100 of your life point\n2- Destroy the card\nchoose a valid option");
            if (response.equals("1"))
                gameController.decreasePlayerLP(board.getOwnerOfCard(myCard), 100);
            else if (response.equals("2"))
                board.sendCardFromSpellZoneToGraveyard(myCard);
            else
                throw new Exception("Invalid option");
        }
    }

    @Override
    public boolean canActivate() throws Exception {
        return gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
    }
}
