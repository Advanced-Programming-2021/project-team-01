package controller;

import model.GamePhase;
import model.card.Card;

public class phaseController {
    private final GameController gameController;

    public phaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public String nextPhase() {
        String result = "";
        switch (gameController.getGamePhase()) {
            case DRAW_PHASE:
                Card card = gameController.getGameBoard().drawCard(GameController.currentPlayer == GameController.playerOne ? 1 : 2);
                gameController.setGamePhase(GamePhase.STANDBY_PHASE);
                result = String.format("phase: draw phase\nnew card added to the hand : %s", card.getName());
                break;
            case STANDBY_PHASE:
                gameController.setGamePhase(GamePhase.MAIN_PHASE1);
                result = "phase: standby phase";
                break;
            case MAIN_PHASE1:
                gameController.setGamePhase(GamePhase.BATTLE_PHASE);
                result = "phase: main 1 phase";
                break;
            case BATTLE_PHASE:
                gameController.setGamePhase(GamePhase.MAIN_PHASE2);
                result = "phase: battle phase";
                break;
            case MAIN_PHASE2:
                gameController.setGamePhase(GamePhase.END_PHASE);
                result = "phase: main 2 phase";
                break;
            case END_PHASE:
                gameController.setGamePhase(GamePhase.DRAW_PHASE);
                GameController.currentPlayer = GameController.getOpponent();
                result = String.format("phase: end phase\nits %s’s turn", GameController.currentPlayer.getNickname());
                gameController.setSummoned(false);
                break;
        }
        return result;
    }
}