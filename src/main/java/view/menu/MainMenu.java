package view.menu;

import controller.exceptions.*;
import view.ConsoleCommands;
import view.Menu;

import java.util.regex.Matcher;

class MainMenu {
    private void newDuelWithFriend(Matcher matcher) {

    }

    private void newDuelWithAI(Matcher matcher) {
        
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
//            try {
//                newDuelWithFriend(matcher);
//            } catch (UsernameNotExists expt) {
//                System.out.println(expt.getMessage());
//            } catch (NoActiveDeck expt) {
//                System.out.println(expt.getMessage());
//            } catch (InvalidDeck expt) {
//                System.out.println(expt.getMessage());
//            } catch (InvalidRoundNumber expt) {
//                System.out.println(expt.getMessage());
//            }
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.NEW_DUEL_AI, command)) != null) {
//            try {
//                newDuelWithAI(matcher);
//            } catch (InvalidRoundNumber expt) {
//                System.out.println(expt.getMessage());
//            }
        } else
            System.out.println("invalid command");
    }
}
