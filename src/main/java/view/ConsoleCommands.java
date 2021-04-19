package view;

import controller.exceptions.InvalidCommandException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum ConsoleCommands {
    LOGIN("^user login (?:username|u) (?<username>\\w+) (?:password|p) (?<password>\\w+)$",
                 "^user login (?:password|p) (?<password>\\w+) (?:username|u) (?<username>\\w+)$"),
    LOGOUT,
    MENU_ENTER,
    MENU_EXIT,
    CREATE_USER,
    MENU_SHOW_CURRENT,
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
    NEW_DUEL,
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
    EXPORT_CARD("maz","zam","moz");
    private String[] strings;

    ConsoleCommands(String... string) {
        System.arraycopy(string, 0, strings, 0, string.length);
    }

    private String[] getValue(){
        return strings;
    }

    public Matcher getMatcher(ConsoleCommands consoleCommands,String command){
        Matcher matcher;
        Pattern pattern;
        for (int i = 0; i < consoleCommands.getValue().length; i++){
            pattern = Pattern.compile(consoleCommands.getValue()[i]);
            matcher = pattern.matcher(command);
            if (matcher.matches()){
                return matcher;
            }
        }
        return null;
    }
}
