package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConsoleCommands {
    LOGIN(
            "^user login (?:username|u) (?<username>\\w+) (?:password|p) (?<password>\\w+)$",
                 "^user login (?:password|p) (?<password>\\w+) (?:username|u) (?<username>\\w+)$"),
    LOGOUT(
            "user logout"),
    MENU_ENTER(
            "menu enter (?<menu>\\w+)"),
    MENU_EXIT(
            "menu exit"),
    CREATE_USER(
            "^user create (?:username|u) (?<username>\\w+) (?:nickname|n) (?<nickname>\\w+) (?:password|p) (?<password>\\w+)$",
                "^user create (?:username|u) (?<username>\\w+) (?:password|p) (?<password>\\w+) (?:nickname|n) (?<nickname>\\w+)$",
                "^user create (?:nickname|n) (?<nickname>\\w+) (?:username|u) (?<username>\\w+) (?:password|p) (?<password>\\w+)$",
                "^user create (?:nickname|n) (?<nickname>\\w+) (?:password|p) (?<password>\\w+) (?:username|u) (?<username>\\w+)$",
                "^user create (?:password|p) (?<password>\\w+) (?:nickname|n) (?<nickname>\\w+) (?:username|u) (?<username>\\w+)$",
                "^user create (?:password|p) (?<password>\\w+) (?:username|u) (?<username>\\w+) (?:nickname|n) (?<nickname>\\w+)$"),
    MENU_SHOW_CURRENT(
            "menu show-current"),
    SHOW_SCOREBOARD,
    PROFILE_CHANGE,
    CREATE_DECK,
    DELETE_DECK,
    ACTIVATE_DECK,
    ADD_CARD,
    REMOVE_CARD,
    SHOW_DECK,
    SHOW_ALL_DECK,
    SHOW_DECK_CARDS,
    BUY_CARD,
    SHOW_ALL_CARDS,
    NEW_DUEL_PLAYER("^duel new second-player (?<username>\\w+) rounds (?<roundNumber>\\d+)$",
            "^duel new rounds (?<roundNumber>\\d+) second-player (?<username>\\w+)$",
            "^duel second-player (?<username>\\w+) new rounds (?<roundNumber>\\d+)$",
            "^duel rounds (?<roundNumber>\\d+) new second-player (?<username>\\w+)$",
            "^duel second-player (?<username>\\w+) rounds (?<roundNumber>\\d+) new$",
            "^duel rounds (?<roundNumber>\\d+) second-player (?<username>\\w+) new$"),
    NEW_DUEL_AI("^duel new ai rounds (?<roundNumber>\\d+)$",
            "^duel ai new rounds (?<roundNumber>\\d+)$",
            "^duel new rounds (?<roundNumber>\\d+) ai$",
            "^duel ai rounds (?<roundNumber>\\d+) new$",
            "^duel rounds (?<roundNumber>\\d+) new ai$",
            "^duel rounds (?<roundNumber>\\d+) ai new$"),
    SELECT_PLAYER_CARD,
    SELECT_OPPONENT_CARD,
    NEXT_PHASE,
    REMOVE_SELECTION,
    SUMMON_MONSTER,
    SET_MONSTER,
    CHANGE_MONSTER_POSITION,
    FLIP_SUMMON,
    ATTACK,
    DIRECT_ATTACK,
    ACTIVATE_EFFECT,
    SET_SPELL,
    SET_TRAP,
    SHOW_GRAVEYARD,
    SHOW_SELECTED_CARD,
    BACK,
    SURRENDER,
    INCREASE_MONEY,
    FORCEFUL_SELECT,
    INCREASE_LP,
    SET_WINNER,
    NewEnumerationItem41,
    IMPORT_CARD,
    EXPORT_CARD("maz", "zam", "moz");
    private String[] strings;

    ConsoleCommands(String... string) {
        strings = new String[string.length];
        System.arraycopy(string, 0, strings, 0, string.length);
    }

    private String[] getValue() {
        return strings;
    }

    public static Matcher getMatcher(ConsoleCommands consoleCommands, String command) {
        Matcher matcher;
        Pattern pattern;
        for (int i = 0; i < consoleCommands.getValue().length; i++) {
            pattern = Pattern.compile(consoleCommands.getValue()[i]);
            matcher = pattern.matcher(command);
            if (matcher.matches()) {
                return matcher;
            }
        }
        return null;
    }
}
