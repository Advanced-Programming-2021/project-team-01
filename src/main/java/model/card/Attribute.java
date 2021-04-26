package model.card;

public enum Attribute {
    EARTH,
    WATER,
    DARK,
    LIGHT,
    FIRE,
    WIND;

    public static Attribute getAttribute(String attribute) {
        switch (attribute) {
            case "EARTH":
                return EARTH;
            case "WATER":
                return WATER;
            case "DARK":
                return DARK;
            case "LIGHT":
                return LIGHT;
            case "FIRE":
                return FIRE;
            case "WIND":
                return WIND;
            default:
                return null;
        }
    }
}
