package controller;

import model.GamePhase;
import model.card.Card;

public class PhaseController {
    private final GameController gameController;
    private GamePhase gamePhase;

    public PhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public String nextPhase() throws Exception {
        gameController.effectController.doEffects();
        String result = "";
        switch (getGamePhase()) {
            case END_PHASE:
                gameController.setSummonedCard(null);
                gameController.resetChangedCard();
                GameController.currentPlayer = GameController.getOpponent();
                Card card = gameController.getGameBoard().drawCard(GameController.currentPlayer == GameController.playerOne ? 1 : 2);
                setGamePhase(GamePhase.DRAW_PHASE);
                gameController.selectedCard.reset();
                result = String.format("phase: draw phase\nnew card added to the hand : %s", card.getName());
                break;
            case DRAW_PHASE:
                setGamePhase(GamePhase.STANDBY_PHASE);
                result = "phase: standby phase";
                break;
            case STANDBY_PHASE:
                setGamePhase(GamePhase.MAIN_PHASE1);
                result = "phase: main 1 phase";
                gameController.createChain();
                gameController.chain.run();
                break;
            case MAIN_PHASE1:
                setGamePhase(GamePhase.BATTLE_PHASE);
                result = "phase: battle phase";
                gameController.createChain();
                gameController.chain.run();
                break;
            case BATTLE_PHASE:
                setGamePhase(GamePhase.MAIN_PHASE2);
                result = "phase: main 2 phase";
                gameController.createChain();
                gameController.chain.run();
                break;
            case MAIN_PHASE2:
                setGamePhase(GamePhase.END_PHASE);
                result = String.format("phase: end phase\nits %s’s turn", GameController.currentPlayer.getNickname());
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