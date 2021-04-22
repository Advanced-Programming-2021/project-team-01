package view.menu;

import controller.DeckController;
import model.Deck;
import model.card.Card;
import model.card.MonsterCard;
import model.card.SpellCard;
import model.card.TrapCard;
import view.ConsoleCommands;

import java.util.ArrayList;
import java.util.Map;
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
        boolean isMainDeck = matcher.group("side").equals("");

        try {
            DeckController.getInstance().addCardToDeck(cardName, deckName, isMainDeck);
            System.out.println("card added to deck successfully");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void removeCardFromDeck(Matcher matcher) {
        String cardName = matcher.group("cardName"), deckName = matcher.group("deckName");
        boolean isMainDeck = matcher.group("side").equals("");

        try {
            DeckController.getInstance().removeCardFromDeck(cardName, deckName, isMainDeck);
            System.out.println("card removed from deck successfully");
        } catch (Exception expt) {
            System.err.println(expt.getMessage());
        }
    }

    private void showDeck(Matcher matcher) {
        boolean isMainDeck = matcher.group("side").equals("");
        String name = matcher.group("deckName");

        try {
            ArrayList<Card> cardMap = DeckController.getInstance().showDeckByName(name, isMainDeck);
            System.out.println("Deck: " + name);
            System.out.println(isMainDeck ? "Main deck:" : "Side deck:");
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

    private void showAllDeck() {
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
