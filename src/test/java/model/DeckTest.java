package model;

import controller.DatabaseController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @DisplayName("Deck limit test")
    @Test
    void test1() throws IOException {
        Deck deck = DatabaseController.getDeckByName("ai");
    }
}