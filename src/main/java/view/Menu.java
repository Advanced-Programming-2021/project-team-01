package view;

public enum Menu {
    EXIT,
    REGISTER_MENU,
    MAIN_MENU,
    PROFILE_VIEW,
    SHOP,
    IMPORT_EXPORT,
    GAME_MENU,
    DECK_MENU,
    SCOREBOARD,
    CHAIN;

    public static Menu getMenu(String menu) {
        switch (menu) {
            case "Login":
                return REGISTER_MENU;
            case "endpoint.Main":
                return MAIN_MENU;
            case "Duel":
                return GAME_MENU;
            case "Deck":
                return DECK_MENU;
            case "Scoreboard":
                return SCOREBOARD;
            case "Profile":
                return PROFILE_VIEW;
            case "Import/Export":
                return IMPORT_EXPORT;
            case "Shop":
                return SHOP;
            default:
                return null;
        }

    }
}
