package controller;

import model.GamePhase;
import model.card.Card;

public class PhaseController {
    private final GameController gameController;
    private GamePhase gamePhase;

    public PhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public String nextPhase() {
        String result = "";
        switch (getGamePhase()) {
            case DRAW_PHASE:
                Card card = gameController.getGameBoard().drawCard(GameController.currentPlayer == GameController.playerOne ? 1 : 2);
                setGamePhase(GamePhase.STANDBY_PHASE);
                result = String.format("phase: draw phase\nnew card added to the hand : %s", card.getName());
                break;
            case STANDBY_PHASE:
                setGamePhase(GamePhase.MAIN_PHASE1);
                result = "phase: standby phase";
                break;
            case MAIN_PHASE1:
                setGamePhase(GamePhase.BATTLE_PHASE);
                result = "phase: main 1 phase";
                break;
            case BATTLE_PHASE:
                setGamePhase(GamePhase.MAIN_PHASE2);
                result = "phase: battle phase";
                break;
            case MAIN_PHASE2:
                setGamePhase(GamePhase.END_PHASE);
                result = "phase: main 2 phase";
                break;
            case END_PHASE:
                setGamePhase(GamePhase.DRAW_PHASE);
                GameController.currentPlayer = GameController.getOpponent();
                result = String.format("phase: end phase\nits %s’s turn", GameController.currentPlayer.getNickname());
                gameController.setSummoned(false);
                break;
        }
        return result;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }
}