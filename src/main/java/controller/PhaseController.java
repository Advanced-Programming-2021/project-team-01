package controller;

import model.GamePhase;
import model.card.Card;

public class PhaseController {
    private final GameController gameController;
    private GamePhase gamePhase;
    public boolean canDraw = true;
    public PhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public String nextPhase() throws Exception {
        String result = "";
        switch (getGamePhase()) {
            case END_PHASE:
                setGamePhase(GamePhase.DRAW_PHASE);
                if (canDraw) {
                    result = drawPhaseActions();
                    break;
                }
            case DRAW_PHASE:
                setGamePhase(GamePhase.STANDBY_PHASE);
                gameController.effectController.doEffects();
                result = "phase: standby phase";
                break;
            case STANDBY_PHASE:
                setGamePhase(GamePhase.MAIN_PHASE1);
                gameController.effectController.doEffects();
                result = "phase: main 1 phase";
                gameController.createChain();
                gameController.chain.run();
                break;
            case MAIN_PHASE1:
                setGamePhase(GamePhase.BATTLE_PHASE);
                gameController.effectController.doEffects();
                result = "phase: battle phase";
                gameController.createChain();
                gameController.chain.run();
                break;
            case BATTLE_PHASE:
                setGamePhase(GamePhase.MAIN_PHASE2);
                gameController.effectController.doEffects();
                result = "phase: main 2 phase";
                gameController.createChain();
                gameController.chain.run();
                break;
            case MAIN_PHASE2:
                setGamePhase(GamePhase.END_PHASE);
                gameController.effectController.doEffects();
                result = String.format("phase: end phase\nits %s’s turn", GameController.currentPlayer.getNickname());
                break;
        }
        return result;
    }

    private String drawPhaseActions() throws Exception {
        String result = "";
        gameController.effectController.doEffects();
        gameController.setSummonedCard(null);
        gameController.resetChangedCard();
        GameController.currentPlayer = GameController.getOpponent();
        if (canDraw) {
            Card card = gameController.getGameBoard().drawCard(GameController.currentPlayer == GameController.playerOne ? 1 : 2);
            result = String.format("phase: draw phase\nnew card added to the hand : %s", card.getName());
        }
        gameController.selectedCard.reset();
        return result;
    }

    public void setGamePhase(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }


}