package model.card;

public enum CardType {
    RITUAL,
    EFFECT,
    NORMAL;

    public static CardType getCardType(String type) {
        switch (type) {
            case "Normal":
                return NORMAL;
            case "Effect":
                return EFFECT;
            case "Ritual":
                return RITUAL;
            default:
                return null;
        }
    }
}
