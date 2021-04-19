package view.menu;

import controller.RegisterController;
import view.ConsoleCommands;
import view.Menu;

import java.util.regex.Matcher;

class RegisterView {

    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.LOGIN, input)) != null) {
            loginUser(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.CREATE_USER, input)) != null) {
            createNewUser(matcher);
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.LOGOUT, input)) != null) {
            logout();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.MENU_EXIT, input)) != null) {
            exitMenu();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.MENU_SHOW_CURRENT, input)) != null) {
            showCurrentMenu();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.MENU_ENTER, input)) != null) {
            enterMenu(matcher);
        } else {
            System.out.println("invalid command");
        }

    }

    private void createNewUser(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        String nickname = matcher.group("nickname");
        try {
            RegisterController.getInstance().createUser(username, password, nickname);
            System.out.println("user created successfully!");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    private void loginUser(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");
        try {
            RegisterController.getInstance().loginUser(username, password);
            System.out.println("user logged in successfully!");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    private void logout() {
        RegisterController.getInstance().logout();
        System.out.println("user logged out successfully");
    }

    private void enterMenu(Matcher matcher) {
        String menu = matcher.group("menu");
        if (Menu.getMenu(menu) == null) {
            System.out.println("invalid command");
            return;
        }
        if (Menu.getMenu(menu) != Menu.MAIN_MENU) {
            System.out.println("menu navigation is not possible");
            return;
        }
        HandleRequestType.currentMenu = Menu.getMenu(menu);
    }

    private void exitMenu() {
        HandleRequestType.currentMenu = Menu.EXIT;
    }

    private void showCurrentMenu() {
        System.out.println("Login");
    }
}
