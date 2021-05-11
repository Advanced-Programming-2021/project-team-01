package model;

public enum State {
    ACTIVE_SPELL,
    ACTIVE_TRAP,
    ATTACK,
    SUMMON,
    CHAIN,
    NONE;

//    public static State getState(String type) {
//        switch (type) {
//            case "spell":
//                return ACTIVE_SPELL;
//            case "trap":
//                return ACTIVE_TRAP;
//            case "attack":
//                return ATTACK;
//            case "none":
//                return NONE;
//            default:
//                return null;
//        }
//    }
}
