package view.menu;

import com.google.gson.Gson;
import controller.exceptions.InvalidMenuNavigation;
import view.ConsoleCommands;
import view.Menu;

import java.util.regex.Matcher;

public class ImportExportView {
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
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.IMPORT_CARD, input)) != null) {
            importCard(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.EXPORT_CARD, input)) != null) {
            exportCard(matcher);
        } else
            System.out.println("invalid command");
    }

    private void importCard(Matcher matcher) {

    }

    private void exportCard(Matcher matcher) {

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
        System.out.println("Import/Export Menu");
    }

    private Gson gsonBuilder(Matcher matcher) {
        return null;
    }
}
