package controller;

public enum Spell {
    MONSTER_REBORN;


    static Spell getSpellByName(String name) {
        if (name.equals("Monster Reborn")) {
            return MONSTER_REBORN;
        }
        return null;
    }

}
