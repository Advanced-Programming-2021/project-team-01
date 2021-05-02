package model;


public enum GamePhase {
    DRAW_PHASE,
    STANDBY_PHASE,
    MAIN_PHASE1,
    BATTLE_PHASE,
    MAIN_PHASE2,
    END_PHASE;

    public static GamePhase getPhase(String type) {
        switch (type) {
            case "draw":
                return DRAW_PHASE;
            case "standby":
                return STANDBY_PHASE;
            case "main1":
                return MAIN_PHASE1;
            case "battle":
                return BATTLE_PHASE;
            case "main2":
                return MAIN_PHASE2;
            case "end":
                return END_PHASE;
            default:
                return null;
        }
    }
}
