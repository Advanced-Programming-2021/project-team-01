package view.menu;

import view.ConsoleCommands;

import java.util.regex.Matcher;

class RegisterView {

    public void run(String input) {
        Matcher matcher;
        if((matcher = ConsoleCommands.getMatcher(ConsoleCommands.LOGIN,input))!= null){
            System.out.println(matcher.group("username"));
            System.out.println(matcher.group("password"));
        }

    }

    private void createNewUser(Matcher matcher) {

    }

    private void loginUser(Matcher matcher) {

    }

    private void logout() {

    }

    private void enterMenu(Matcher matcher) {

    }

    private void exitMenu() {

    }

    private void showCurrentMenu() {

    }
}
