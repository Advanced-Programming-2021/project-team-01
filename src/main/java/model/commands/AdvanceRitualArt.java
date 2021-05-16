package model.commands;

import controller.GameController;
import model.GamePhase;
import model.card.Card;

public class AdvanceRitualArt extends Command implements Activate{
    public AdvanceRitualArt(Card card) {
        super(card);
    }

    public void run() throws Exception {
        GameController.getInstance().ritualSummon();
    }

    public boolean canActivate(){
        return gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE1) ||
                gameController.getPhaseController().getGamePhase().equals(GamePhase.MAIN_PHASE2) ||
                gameController.getPhaseController().getGamePhase().equals(GamePhase.BATTLE_PHASE);
    }
}
