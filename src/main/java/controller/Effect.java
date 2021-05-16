package controller;

public enum Effect {
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
    SUPPLY_SQUAD("Supply Squad"),
    NEGATE_ATTACK("Negate Attack"),
    MIRROR_FORCE("Mirror Force"),
    MAGIC_CYLINDER("Magic Cylinder"),
    MIND_CRUSH("Mind Crush"),
    MAGIC_JAMMER("Magic Jammer"),
    TIME_SEAL("Time Seal"),
    TORRENTIAL_TRIBUTE("Torrential Tribute"),
    TRAP_HOLE("Trap Hole"),
    CALL_OF_THE_HAUNTED("Call of The Haunted"),
    SOLEMN_WARNING("Solemn Warning"),
    YOMI_SHIP("Yomi Ship"),
    SUIJIN("Suijin"),
    MAN_EATER_BUG("Man-Eater Bug"),
    RITUAL("Advanced Ritual Art"),
    TRICKY("The Tricky");
    String value;

    Effect(String value) {
        this.value = value;
    }

    public static Effect getSpellByName(String name) {
        for (Effect effect : Effect.values())
            if (effect.value.equals(name))
                return effect;
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
