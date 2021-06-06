package view.menu;

import controller.RegisterController;
import controller.ShopController;
import model.card.Card;
import view.ConsoleCommands;
import view.Menu;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;

class ShopView {

    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.BUY_CARD, input)) != null) {
            buyCard(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.MENU_ENTER, input)) != null) {
            enterMenu(matcher);
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_SHOW_CURRENT, input) != null) {
            showCurrentMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_EXIT, input) != null) {
            exitMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.SHOW_ALL_CARDS, input) != null) {
            showAllCards();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.INCREASE_MONEY, input)) != null) {
            increaseMoney(matcher);
        } else {
            System.err.println("invalid command");
        }

    }

    private void buyCard(Matcher matcher) {
        String cardName = matcher.group("cardName");
        try {
            ShopController.getInstance().buyCard(cardName);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    private void showAllCards() {
        TreeMap<String, Card> shopCards = Card.getAllCards();
        shopCards.forEach((key, value) -> System.out.println(key + ":" + value.getPrice()));
    }

    private void enterMenu(Matcher matcher) {
        String menu = matcher.group("menu");
        if (Menu.getMenu(menu) == null) {
            System.err.println("invalid command");
            return;
        }
        System.err.println("menu navigation is not possible");
    }

    private void increaseMoney(Matcher matcher) {
        RegisterController.onlineUser.increaseMoney(Integer.parseInt(matcher.group(1)));
    }

    private void exitMenu() {
        HandleRequestType.currentMenu = Menu.MAIN_MENU;
    }

    private void showCurrentMenu() {
        System.out.println("Shop");
    }
}
