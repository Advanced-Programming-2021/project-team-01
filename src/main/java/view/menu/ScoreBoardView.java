package view.menu;

import controller.ScoreBoardController;
import controller.exceptions.InvalidMenuNavigation;
import view.ConsoleCommands;
import view.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class ScoreBoardView {
    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.MENU_ENTER, input)) != null) {
            try {
                enterMenu(matcher);
            } catch (InvalidMenuNavigation expt) {
                System.err.println(expt.getMessage());
            }
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_EXIT, input) != null) {
            exitMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_SHOW_CURRENT, input) != null) {
            showCurrentMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.SHOW_SCOREBOARD, input) != null) {
            showScoreBoard();
        } else
            System.out.println("invalid command");
    }

    private void showScoreBoard() {
        HashMap<String, Integer> ranks = ScoreBoardController.getInstance().showScoreBoard();
        int counter = 1;

        for (Map.Entry<String, Integer> set : ranks.entrySet()) {
            System.out.println(counter + "- " + set.getKey() + ": " + set.getValue());
            counter++;
        }
    }

    private void enterMenu(Matcher matcher) throws InvalidMenuNavigation {
        String menuName = matcher.group("menu");
        if (Menu.getMenu(menuName) == Menu.MAIN_MENU)
            HandleRequestType.currentMenu = Menu.MAIN_MENU;
        else
            throw new InvalidMenuNavigation();
    }

    private void exitMenu() {
        HandleRequestType.currentMenu = Menu.MAIN_MENU;
    }

    private void showCurrentMenu() {
        System.out.println("Scoreboard Menu");
    }
}
