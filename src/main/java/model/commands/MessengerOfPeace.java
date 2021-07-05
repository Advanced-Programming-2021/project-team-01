package model.commands;

import model.Board;
import model.GamePhase;
import model.card.Card;
import console.menu.GameView;

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
            String response = view.GameView.getChoiceBox("Hi i am the messenger of Peace choose your fate!",
                    "Lose 100 of your life point","Destroy the card");
            if (response.equals("Lose 100 of your life point"))
                gameController.decreasePlayerLP(board.getOwnerOfCard(myCard), 100);
            else if (response.equals("Destroy the card"))
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
