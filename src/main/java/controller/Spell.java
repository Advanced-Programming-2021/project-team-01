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

    public String toString(){
        return value;
    }
    static Spell getSpellByName(String name) {
        return Spell.valueOf(name);
    }

}
