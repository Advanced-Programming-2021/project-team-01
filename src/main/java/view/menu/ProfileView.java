package view.menu;

import controller.ProfileController;
import view.ConsoleCommands;
import view.Menu;

import java.util.regex.Matcher;

class ProfileView {

    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.CHANGE_NICKNAME, input)) != null) {
            changeNickname(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.CHANGE_PASSWORD, input)) != null) {
            changePassword(matcher);
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_SHOW_CURRENT,input)!=null){
            showCurrentMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_EXIT,input)!=null){
            exitMenu();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.MENU_ENTER,input))!=null){
            enterMenu(matcher);
        } else {
            System.err.println("invalid command");
        }

    }

    private void changeNickname(Matcher matcher) {
        String nickname = matcher.group("nickname");
        try {
            ProfileController.getInstance().changeNickname(nickname);
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }

    private void changePassword(Matcher matcher) {
        String oldPassword = matcher.group("currentPassword");
        String newPassword = matcher.group("newPassword");
        try {
            ProfileController.getInstance().changePassword(oldPassword,newPassword);
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }

    private void enterMenu(Matcher matcher) {
        String menu = matcher.group("menu");
        if (Menu.getMenu(menu) == null) {
            System.err.println("invalid command");
            return;
        }
        System.err.println("menu navigation is not possible");
    }

    private void exitMenu() {
        HandleRequestType.currentMenu = Menu.MAIN_MENU;
    }

    private void showCurrentMenu() {

    }
}
