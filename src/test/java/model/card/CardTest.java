package model.card;

import com.opencsv.exceptions.CsvValidationException;
import controller.DatabaseController;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @BeforeEach
    void before() throws IOException, CsvValidationException {
        DatabaseController.loadGameCards();
    }
    @DisplayName("Card equal test and card clone")
    @Test
    void test1() throws CloneNotSupportedException {
        Card card1 = Card.getCardByName("Battle OX");
        Card card2 = (Card) ((MonsterCard)card1).clone();
        Assertions.assertEquals(card1, card2);
        Assertions.assertTrue(card1 != card2);
    }
}