package model;

import controller.DatabaseController;
import controller.RegisterController;
import model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @BeforeEach
    void init() throws Exception {
        DatabaseController.loadGameCards();
    }

    @DisplayName("Deck limit test")
    @Test
    void cardLimitTest() throws IOException {
        Deck deck = DatabaseController.getDeckByName("ai");
        RegisterController.onlineUser = DatabaseController.getUserByName("AI");
        Assertions.assertFalse(deck.checkCardsLimit(Card.getCardByName("Axe Raider")));
    }

    @DisplayName("Deck add card test")
    @Test
    void addCardTest() throws IOException {
        Deck deck = DatabaseController.getDeckByName("ai");
        RegisterController.onlineUser = DatabaseController.getUserByName("AI");
        Assertions.assertFalse(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        Assertions.assertTrue(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        Assertions.assertFalse(deck.getSideDeck().contains(Card.getCardByName("Suijin")));
        deck.addCardToSideDeck(Card.getCardByName("Suijin"));
        Assertions.assertTrue(deck.getSideDeck().contains(Card.getCardByName("Suijin")));
    }

    @DisplayName("Deck remove card test")
    @Test
    void removeCardTest() throws IOException {
        Deck deck = DatabaseController.getDeckByName("ai");
        RegisterController.onlineUser = DatabaseController.getUserByName("AI");
        deck.addCardToMainDeck(Card.getCardByName("Suijin"));
        Assertions.assertTrue(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        deck.addCardToSideDeck(Card.getCardByName("Suijin"));
        Assertions.assertTrue(deck.getSideDeck().contains(Card.getCardByName("Suijin")));
        deck.removeCardFromMainDeck(Card.getCardByName("Suijin"));
        deck.removeCardFromSideDeck(Card.getCardByName("Suijin"));
        Assertions.assertFalse(deck.getMainDeck().contains(Card.getCardByName("Suijin")));
        Assertions.assertFalse(deck.getSideDeck().contains(Card.getCardByName("Suijin")));
    }

    @DisplayName("Deck name getter test")
    @Test
    void deckNameTest() throws IOException {
        Deck deck = DatabaseController.getDeckByName("ai");
        Assertions.assertTrue(deck.getDeckName().equals("ai"));
    }

    @DisplayName("Deck validation test")
    @Test
    void deckIsValidTest() throws IOException {
        Deck deck = DatabaseController.getDeckByName("ai");
        Assertions.assertTrue(deck.getMainDeck().size() >= 25);
        Assertions.assertTrue(deck.isDeckValid());
    }
}