package controller;

import model.GamePhase;
import model.card.Card;
import model.card.MonsterCard;
import model.card.SpellCard;
import view.menu.GameView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AiBasicController {
    GameController gameController;

    public AiBasicController(GameController gameController) {
        this.gameController = gameController;
    }

    public String handleAiMoves() throws Exception {
        if (gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE1)) {
            summonActions();
            activateActions();
            return phaseChangerActions();
        } else if (gameController.getPhaseController().getGamePhase().equals(GamePhase.BATTLE_PHASE)) {
            attackActions();
            return phaseChangerActions();
        } else if (gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE2)) {
            activateActions();
            return phaseChangerActions();
        } else if (gameController.getPhaseController().getGamePhase().equals(GamePhase.DRAW_PHASE)){
            return phaseChangerActions();
        } else if (gameController.getPhaseController().getGamePhase().equals(GamePhase.END_PHASE)){
            return phaseChangerActions();
        } else if (gameController.getPhaseController().getGamePhase().equals(GamePhase.STANDBY_PHASE)){
            return phaseChangerActions();
        }
        if (gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE1) ||
                gameController.getPhaseController().getGamePhase().equals(GamePhase.BATTLE_PHASE) ||
                gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE2)){
            gameController.gameBoard.showBoard();
        }
        return "";

    }

    private void summonActions() throws Exception {
        ArrayList<Card> hand = gameController.getGameBoard().getPlayerTwoHand();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) instanceof MonsterCard) {
                if (gameController.gameBoard.numberOfMonsterCards(2) == 5){
                    break;
                }
                gameController.selectPlayerCard("hand", i + 1);
                GameView.prompt(hand.get(i).getName() + " summoned");
                TimeUnit.SECONDS.sleep(1);
                gameController.summon();
                break;
            }
        }
    }

    private void activateActions() throws Exception {
        ArrayList<Card> hand = gameController.getGameBoard().getPlayerTwoHand();
        for (int i = 0; i < hand.size(); i++) {
            if(hand.get(i).getName().equals("Pot of Greed")){
                int x = 3;
            }
            if (hand.get(i) instanceof SpellCard) {
                if (hand.get(i).canActivate()) {
                    gameController.selectPlayerCard("hand", i + 1);
                    GameView.prompt(hand.get(i).getName() + " activated");
                    TimeUnit.SECONDS.sleep(1);
                    gameController.activateEffect();
                }
            }
        }
    }

    private void attackActions() {

    }

    private String phaseChangerActions() throws Exception {
        return gameController.nextPhase();
    }

}
