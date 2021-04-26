package model.card;

public enum Property {
    NORMAL,
    COUNTER,
    CONTINUOUS,
    QUICK_PLAY,
    FIELD,
    EQUIP,
    RITUAL;

    public static Property getProperty(String type) {
        switch (type) {
            case "Normal":
                return NORMAL;
            case "Counter":
                return COUNTER;
            case "Continuous":
                return CONTINUOUS;
            case "Quick-play":
                return QUICK_PLAY;
            case "Field":
                return FIELD;
            case "Equip":
                return EQUIP;
            case "Ritual":
                return RITUAL;
            default:
                return null;
        }
    }
}
