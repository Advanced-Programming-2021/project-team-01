package controller;

import controller.exceptions.*;
import model.Deck;
import model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class DeckControllerTest {
    @BeforeEach
    void init() throws Exception {
        RegisterController.onlineUser = DatabaseController.getUserByName("username");
        DatabaseController.loadGameCards();
    }

    @DisplayName("Creating deck test")
    @Test
    void createDeckTest() throws Exception {
        Assertions.assertFalse(DatabaseController.doesDeckExists("test_deck"));
        DeckController.getInstance().createDeck("test_deck");
        Assertions.assertTrue(DatabaseController.doesDeckExists("test_deck"));
        DatabaseController.deleteDeckFile("test_deck");
    }

    @DisplayName("Deleting deck test")
    @Test
    void deleteDeckTest() throws Exception {
        DeckController.getInstance().createDeck("test_deck");
        Assertions.assertTrue(DatabaseController.doesDeckExists("test_deck"));
        DeckController.getInstance().deleteDeck("test_deck");
        Assertions.assertFalse(DatabaseController.doesDeckExists("test_deck"));
        DatabaseController.deleteDeckFile("test_deck");
    }

    @DisplayName("Activate deck test")
    @Test
    void activateDeckTest() throws Exception {
        Assertions.assertFalse(RegisterController.onlineUser.getActiveDeck().equals("xx"));
        DeckController.getInstance().activateDeck("xx");
        Assertions.assertTrue(RegisterController.onlineUser.getActiveDeck().equals("xx"));
    }

    @DisplayName("Add card to deck test")
    @Test
    void addCardToDeckTest() throws Exception {
        Assertions.assertThrows(MainDeckIsFull.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DeckController.getInstance().addCardToDeck("Suijin", "test", true);
            }
        });
        DeckController.getInstance().createDeck("test_deck");
        Deck deck = DatabaseController.getDeckByName("test_deck");
        Assertions.assertFalse(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        DeckController.getInstance().addCardToDeck("Suijin", "test_deck", true);
        Assertions.assertFalse(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        DatabaseController.deleteDeckFile("test_deck");
    }

    @DisplayName("Remove card test")
    @Test
    void removeCardFromDeckTest() throws Exception {
        Assertions.assertThrows(CardNotInDeck.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                DeckController.getInstance().removeCardFromDeck("Suijin", "test", true);
            }
        });
        DeckController.getInstance().createDeck("test_deck");
        Deck deck = DatabaseController.getDeckByName("test_deck");
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck.removeCardFromMainDeck(Card.getCardByName("Suijin"));
        Assertions.assertFalse(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        DatabaseController.deleteDeckFile("test_deck");
    }

    @DisplayName("Show deck by name test")
    @Test
    void showDeckByNameTest() throws Exception {
        DeckController.getInstance().createDeck("test_deck");
        Deck deck = DatabaseController.getDeckByName("test_deck");
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck.addCardToMainDeck(Card.getCardByName("Axe Raider"));
        Assertions.assertTrue(DeckController.getInstance().showDeckByName("test_deck", true)
                .get(0).getName().equals("Axe Raider"));
        DatabaseController.deleteDeckFile("test_deck");
    }
}