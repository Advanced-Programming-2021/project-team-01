package controller;

public enum Spell {
    MONSTER_REBORN("Monster Reborn"),
    POT_OF_GREED("Pot Of Greed"),
    TERRAFORMING("Terraforming"),
    RAIGEKI("Raigeki");
    String value;
    Spell(String value) {
        this.value = value;
    }

    static Spell getSpellByName(String name){
        for (Spell spell : Spell.values())
            if (spell.value.equals(name))
                return spell;
        return null;
    }
}
