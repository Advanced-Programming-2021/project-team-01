package view.menu;

import controller.DeckController;
import view.ConsoleCommands;

import java.util.regex.Matcher;

public class DeckView {
    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.CREATE_DECK, input)) != null) {
            createDeck(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.DELETE_DECK, input)) != null) {
            deleteDeck(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.ACTIVATE_DECK, input)) != null) {
            activateDeck(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.ADD_CARD, input)) != null) {
            addCardToDeck(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.REMOVE_CARD, input)) != null) {
            removeCardFromDeck(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.SHOW_DECK, input)) != null) {
            showDeck(matcher);
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.SHOW_ALL_DECK, input) != null) {
            showAllDeck();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.SHOW_ALL_CARDS, input) != null) {
            showDecksCards();
        } else
            System.out.println("invalid command");
    }

    private void createDeck(Matcher matcher) {
        String name = matcher.group("name");
        try {
            DeckController.getInstance().createDeck(name);
            System.out.println("deck created successfully!");
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void deleteDeck(Matcher matcher) {
        String name = matcher.group("name");
        try {
            DeckController.getInstance().deleteDeck(name);
            System.out.println("deck deleted successfully");
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void activateDeck(Matcher matcher) {
        String name = matcher.group("name");
        try {
            DeckController.getInstance().activateDeck(name);
            System.out.println("deck activated successfully");
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void addCardToDeck(Matcher matcher) {
        String cardName = matcher.group("cardName"), deckName = matcher.group("deckName");
        boolean isMainDeck = matcher.group("side").equals("");

        try {
            DeckController.getInstance().addCardToDeck(cardName, deckName, isMainDeck);
            System.out.println("card added to deck successfully");
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void removeCardFromDeck(Matcher matcher) {
        String cardName = matcher.group("cardName"), deckName = matcher.group("deckName");
        boolean isMainDeck = matcher.group("side").equals("");

        try {
            DeckController.getInstance().removeCardFromDeck(cardName, deckName, isMainDeck);
            System.out.println("card removed from deck successfully");
        } catch (Exception expt) {
            System.out.println(expt.getMessage());
        }
    }

    private void showDeck(Matcher matcher) {

    }

    private void showAllDeck() {

    }

    private void showDecksCards() {

    }
}
