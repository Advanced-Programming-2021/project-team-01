package console.menu;

import controller.ScoreBoardController;
import controller.exceptions.InvalidMenuNavigation;
import model.Player;
import console.ConsoleCommands;
import console.Menu;

import java.util.ArrayList;
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
        ArrayList<Player> players = ScoreBoardController.getInstance().getSortedScoreBoard();
        int index = 1;
        for (Player player : players) {
            System.out.println(index + "- " + player.getUsername() + ": " + player.getScore());
            index++;
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
