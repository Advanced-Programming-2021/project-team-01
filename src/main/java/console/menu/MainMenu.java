package console.menu;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import controller.GameController;
import controller.RegisterController;
import controller.exceptions.*;
import console.ConsoleCommands;
import console.Menu;

import java.util.regex.Matcher;

class MainMenu {
    private void newDuelWithFriend(Matcher matcher) {
        try {
            int roundNumber = Integer.parseInt(matcher.group("roundNumber"));
            String username = matcher.group("username");
            GameController.getInstance().startGame(username,username, roundNumber,false);//nonesense!
            System.out.println(GameController.getInstance().nextPhase());
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
        HandleRequestType.currentMenu = Menu.GAME_MENU;
    }

    private void newDuelWithAI(Matcher matcher) {
        try {
            int roundNumber = Integer.parseInt(matcher.group("roundNumber"));
            //GameController.getInstance().startGame("AI", roundNumber, EffectiveAlternativeSelector);
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
        HandleRequestType.currentMenu = Menu.GAME_MENU;
    }

    private void enterMenu(Matcher matcher) throws InvalidMenuNavigation {
        String menuName = matcher.group("menu");
        if (Menu.getMenu(menuName) != null)
            HandleRequestType.currentMenu = Menu.getMenu(menuName);
        else
            throw new InvalidMenuNavigation();
    }

    private void exitMenu() {
        RegisterController.getInstance().logout();
    }

    private void showCurrentMenu() {
        System.out.println("endpoint.Main Menu");
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
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.LOGOUT, command)) != null){
            logout();
        }
        else
            System.out.println("invalid command");
    }
    private void logout() {
        RegisterController.getInstance().logout();
        System.out.println("user logged out successfully");
    }
}
