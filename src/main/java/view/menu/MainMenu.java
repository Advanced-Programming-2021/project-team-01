package view.menu;

import controller.GameController;
import controller.exceptions.*;
import view.ConsoleCommands;
import view.Menu;

import java.util.regex.Matcher;

class MainMenu {
    private void newDuelWithFriend(Matcher matcher) {
        try {
            int roundNumber = Integer.parseInt(matcher.group("roundNumber"));
            String username = matcher.group("username");
            GameController.getInstance().startGame(username, roundNumber);
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void newDuelWithAI(Matcher matcher) {
        try {
            int roundNumber = Integer.parseInt(matcher.group("roundNumber"));
            GameController.getInstance().startGame("ai", roundNumber);
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void enterMenu(Matcher matcher) throws InvalidMenuNavigation {
        String menuName = matcher.group("menu");
        if (Menu.getMenu(menuName) != null)
            HandleRequestType.currentMenu = Menu.getMenu(menuName);
        else
            throw new InvalidMenuNavigation();
    }

    private void exitMenu() {
        HandleRequestType.currentMenu = Menu.REGISTER_MENU;
    }

    private void showCurrentMenu() {
        System.out.println("Main Menu");
    }

    public void run(String command) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.MENU_ENTER, command)) != null) {
            try {
                enterMenu(matcher);
            } catch (InvalidMenuNavigation expt) {
                System.out.println(expt.getMessage());
            }
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_EXIT, command) != null) {
            exitMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_SHOW_CURRENT, command) != null) {
            showCurrentMenu();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.NEW_DUEL_PLAYER, command)) != null) {
            newDuelWithFriend(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.NEW_DUEL_AI, command)) != null) {
            newDuelWithAI(matcher);
        } else
            System.out.println("invalid command");
    }
}
