package console.menu;

import controller.DeckController;
import controller.exceptions.InvalidMenuNavigation;
import model.Deck;
import model.card.Card;
import model.card.MonsterCard;
import model.card.SpellCard;
import model.card.TrapCard;
import console.ConsoleCommands;
import console.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class DeckView {
    public void run(String input) throws IOException {
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
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.SHOW_PLAYER_CARD, input) != null) {
            showDecksCards();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.MENU_ENTER, input)) != null) {
            try {
                enterMenu(matcher);
            } catch (InvalidMenuNavigation expt) {
                System.out.println(expt.getMessage());
            }
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_EXIT, input) != null) {
            exitMenu();
        } else if (ConsoleCommands.getMatcher(ConsoleCommands.MENU_SHOW_CURRENT, input) != null) {
            showCurrentMenu();
        } else
            System.out.println("invalid command");
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
        System.out.println("Deck Menu");
    }

    private void createDeck(Matcher matcher) {
        String name = matcher.group("name");
        try {
            DeckController.getInstance().createDeck(name);
            System.out.println("deck created successfully!");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void deleteDeck(Matcher matcher) {
        String name = matcher.group("name");
        try {
            DeckController.getInstance().deleteDeck(name);
            System.out.println("deck deleted successfully");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void activateDeck(Matcher matcher) {
        String name = matcher.group("name");
        try {
            DeckController.getInstance().activateDeck(name);
            System.out.println("deck activated successfully");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void addCardToDeck(Matcher matcher) {
        String cardName = matcher.group("cardName"), deckName = matcher.group("deckName");
        boolean isMainDeck = (matcher.group("side") == null);

        try {
            DeckController.getInstance().addCardToDeck(cardName, deckName, isMainDeck);
            System.out.println("card added to deck successfully");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void removeCardFromDeck(Matcher matcher) {
        String cardName = matcher.group("cardName"), deckName = matcher.group("deckName");
        boolean isMainDeck = matcher.group("side") == null;
        try {
            DeckController.getInstance().removeCardFromDeck(cardName, deckName, isMainDeck);
            System.out.println("card removed from deck successfully");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void showDeck(Matcher matcher) {
        boolean isMainDeck = matcher.group("side") == null;//equals(""); //fixme: kharab
        String name = matcher.group("deckName");

        try {
            ArrayList<Card> cardMap = DeckController.getInstance().showDeckByName(name, isMainDeck);
            System.out.println("Deck: " + name);
            System.out.println(isMainDeck ? "endpoint.Main deck:" : "Side deck:");
            System.out.println("Monsters:");

            for (Card card : cardMap)
                if (card instanceof MonsterCard)
                    System.out.println(card.getName() + ": " + card.getDescription());

            System.out.println("Spells and Traps:");

            for (Card card : cardMap)
                if (card instanceof SpellCard || card instanceof TrapCard)
                    System.out.println(card.getName() + ": " + card.getDescription());
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void showAllDeck() throws IOException {
        ArrayList<Deck> decks = DeckController.getInstance().showAllDecks();
        System.out.println("Decks:");
        System.out.println("Active deck:");
        if (decks.get(0) != null) {
            System.out.println(decks.get(0).getDeckName() +
                    ": main deck " + decks.get(0).getMainDeck().size() +
                    ", side deck " + decks.get(0).getSideDeck().size() +
                    ", " + (decks.get(0).isDeckValid() ? "valid" : "invalid"));
        }
        System.out.println("Other decks:");
        for (int i = 1; i < decks.size(); i++) {
            System.out.println(decks.get(i).getDeckName() +
                    ": main deck " + decks.get(i).getMainDeck().size() +
                    ", side deck " + decks.get(i).getSideDeck().size() +
                    ", " + (decks.get(i).isDeckValid() ? "valid" : "invalid"));
        }
    }

    private void showDecksCards() {
        ArrayList<Card> cards = DeckController.getInstance().showPlayersAllCards();
        for (Card card : cards)
            System.out.println(card.getName() + " : " + card.getDescription());
    }
}
