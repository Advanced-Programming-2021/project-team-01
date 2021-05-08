package controller;

public enum Spell {
    MONSTER_REBORN("Monster Reborn"),
    POT_OF_GREED("Pot of Greed"),
    TERRAFORMING("Terraforming"),
    RAIGEKI("Raigeki"),
    UMIIRUKA("Umiiruka"),
    FOREST("Forest"),
    YAMI("Yami"),
    CLOSED_FOREST("Closed Forest"),
    BLACK_PENDANT("Black Pendant"),
    UNITED_WE_STAND("United We Stand"),
    MAGNUM_SHIELD("Magnum Shield"),
    SWORD_OF_DESTRUCTION("Sword of dark destruction"),
    DARK_HOLE("Dark Hole"),
    HARPIES_FEATHER_DUSTER("Harpie's Feather Duster"),
    MYSTICAL_SPACE_TYPHOON("Mystical space typhoon"),
    TWIN_TWISTERS("Twin Twisters"),
    CHANGE_OF_HEART("Change of Heart"),
    SUPPLY_SQUAD("Supply Squad");
    String value;

    Spell(String value) {
        this.value = value;
    }

    public static Spell getSpellByName(String name) {
        for (Spell spell : Spell.values())
            if (spell.value.equals(name))
                return spell;
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
