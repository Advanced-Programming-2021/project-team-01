package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConsoleCommands {
    //RegisterMenu:

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
    SHOW_SCOREBOARD("scoreboard show"),

    //ProfileMenu:

    CHANGE_NICKNAME("profile change nickname (?<nickname>\\w+)"),
    CHANGE_PASSWORD(
            "profile change password new (?<newPassword>\\w+) current (?<currentPassword>\\w+)",
            "profile change password current (?<currentPassword>\\w+) new (?<newPassword>\\w+)",
            "profile change new (?<newPassword>\\w+) password current (?<currentPassword>\\w+)",
            "profile change current (?<currentPassword>\\w+) password new (?<newPassword>\\w+)",
            "profile change new (?<newPassword>\\w+) current (?<currentPassword>\\w+) password",
            "profile change current (?<currentPassword>\\w+) new (?<newPassword>\\w+) password"),

    //DeckMenu:

    CREATE_DECK("^deck create (?<name>\\w+)$"),
    DELETE_DECK("^deck delete (?<name>\\w+)$"),
    ACTIVATE_DECK("^deck set-activate (?<name>\\w+)$"),
    ADD_CARD("^deck add-card card (?<cardName>[\\w\\s']+) deck (?<deckName>[\\w\\s']+)(?<side> side)*$",
            "^deck add-card(?<side> side)* card (?<cardName>[\\w\\s']+) deck (?<deckName>[\\w\\s']+)$",
            "^deck add-card card (?<cardName>[\\w\\s']+)(?<side> side)* deck (?<deckName>[\\w\\s']+)$",
            "^deck add-card deck (?<deckName>[\\w\\s']+) card (?<cardName>[\\w\\s']+)(?<side> side)*$",
            "^deck add-card(?<side> side)* deck (?<deckName>[\\w\\s']+) card (?<cardName>[\\w\\s']+)$",
            "^deck add-card deck (?<deckName>[\\w\\s']+)(?<side> side)* card (?<cardName>[\\w\\s']+)$"),
    REMOVE_CARD("^deck rm-card card (?<cardName>[\\w\\s']+) deck (?<deckName>[\\w\\s']+)(?<side> side)*$",
            "^deck rm-card card (?<cardName>[\\w\\s']+)(?<side> side)* deck (?<deckName>[\\w\\s']+)$",
            "^deck rm-card(?<side> side)* card (?<cardName>[\\w\\s']+) deck (?<deckName>[\\w\\s']+)$",
            "^deck rm-card deck (?<deckName>[\\w\\s']+) card (?<cardName>[\\w\\s']+)(?<side> side)*$",
            "^deck rm-card deck (?<deckName>[\\w\\s']+)(?<side> side)* card (?<cardName>[\\w\\s']++)$",
            "^deck rm-card(?<side> side)* deck (?<deckName>[\\w\\s']+) card (?<cardName>[\\w\\s']+)$"),
    SHOW_DECK("^deck show deck-name (?<deckName>[\\w\\s']+)(?<side> side)*$",
            "^deck show deck-name(?<side> side)* (?<deckName>[\\w\\s']+)$"),
    SHOW_ALL_DECK("^deck show all$"),
    SHOW_DECK_CARDS("^show deck cards$"),
    BUY_CARD("^shop buy (?<cardName>[\\w\\s']+)$"),
    SHOW_ALL_CARDS("shop show all"),

    //MainMenu:

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

    //Game Menu
    SELECT_PLAYER_CARD("select (?<type>monster|spell)( (?<opponent>opponent))*( (?<number>\\d+))*",
            "select (?<opponent>opponent)( (?<type>monster|spell))*( (?<number>\\d+))*",
            "select (?<type>field)( (?<opponent>opponent))*",
            "select (?<opponent>opponent) (?<type>field)",
            "select (?<type>hand) (?<number>\\d+)(?<opponent>)*"),
    SELECT_PLAYER_CARD_CHAIN("select (?<type>monster|spell)( (?<number>\\d+))*",
            "select (?<type>field)",
            "select (?<type>hand) (?<number>\\d+)"),
    DESELECT_CARD("select -d"),
    NEXT_PHASE("next phase"),
    REMOVE_SELECTION,
    SUMMON_MONSTER("summon"),
    SET_CARD("set"),
    CHANGE_MONSTER_POSITION("set position (?<position>attack|defense)"),
    FLIP_SUMMON("flip-summon"),
    ATTACK("attack (?<number>\\d+)"),
    DIRECT_ATTACK("attack direct"),
    ACTIVATE_EFFECT("active effect"),
    SHOW_GRAVEYARD("show graveyard"),
    SHOW_SELECTED_CARD("card show selected"),
    BACK("back"),
    SURRENDER("surrender"),
    INCREASE_MONEY("im (\\d+)"),
    INCREASE_LP("increase lp (\\d+)"),
    SET_WINNER("duel set-winner (\\w+)"),
    NewEnumerationItem41,
    IMPORT_CARD("^import card (?<cardName>\\w+)$"),
    EXPORT_CARD("^export card (?<cardName>\\w+)$"),
    CHEAT("gc ([a-zA-Z' -]+)");
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
