package controller;

import model.GamePhase;
import model.card.Card;
import model.card.MonsterType;

public class PhaseController {
    private final GameController gameController;
    private GamePhase gamePhase;

    public PhaseController(GameController gameController) {
        this.gameController = gameController;
    }

    public String nextPhase() {
        String result = "";
        switch (getGamePhase()) {
            case END_PHASE:
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
                deActiveFieldEffect();
                setGamePhase(GamePhase.MAIN_PHASE1);
                result = "phase: main 1 phase";
                break;
            case MAIN_PHASE1:
                activeFieldEffect();
                setGamePhase(GamePhase.BATTLE_PHASE);
                result = "phase: battle phase";
                break;
            case BATTLE_PHASE:
                deActiveFieldEffect();
                setGamePhase(GamePhase.MAIN_PHASE2);
                result = "phase: main 2 phase";
                break;
            case MAIN_PHASE2:
                activeFieldEffect();
                setGamePhase(GamePhase.END_PHASE);
                GameController.currentPlayer = GameController.getOpponent();
                result = String.format("phase: end phase\nits %s’s turn", GameController.currentPlayer.getNickname());
                gameController.setSummonedCard(null);
                gameController.resetChangedCard();
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

    public void activeFieldEffect(){
        if (gameController.gameBoard.getPlayerOneFieldZone().getCard() == null &&
                gameController.gameBoard.getPlayerOneFieldZone().getCard() == null)
            return;
        if (gameController.gameBoard.getPlayerOneFieldZone().getCard() != null){
            switch (gameController.gameBoard.getPlayerOneFieldZone().getCard().getName()){
                case "Umiiruka":
                    gameController.gameBoard.changePowerMonster(500,-400, MonsterType.AQUA);
                    break;
                //todo : fill it


            }
        }
    }
    public void deActiveFieldEffect(){
        if (gameController.gameBoard.getPlayerOneFieldZone().getCard() == null &&
                gameController.gameBoard.getPlayerOneFieldZone().getCard() == null)
            return;
        if (gameController.gameBoard.getPlayerOneFieldZone().getCard() != null){
            switch (gameController.gameBoard.getPlayerOneFieldZone().getCard().getName()){
                case "Umiiruka":
                    gameController.gameBoard.changePowerMonster(-500,400, MonsterType.AQUA);
                    break;
                //todo : fill it


            }
        }
    }
}