package controller;

import com.opencsv.exceptions.CsvValidationException;
import controller.exceptions.CardNameNotExists;
import model.card.Card;
import model.commands.Suijin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImportExportControllerTest {
    @Test
    @DisplayName("Export card test")
    void exportTest1() throws Exception {
        DatabaseController.loadGameCards();
        File file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + "Suijin" + ".json");
        assertFalse(file.exists());
        ImportExportController.exportCard("Suijin");
        file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + "Suijin" + ".json");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    @DisplayName("Export card test exception")
    void exportTest2() throws Exception {
        DatabaseController.loadGameCards();
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ImportExportController.exportCard("sss");
            }
         });
    }

    @Test
    @DisplayName("Import card test monster")
    void importTest1() throws Exception {
        DatabaseController.loadGameCards();
        ImportExportController.exportCard("Suijin");
        Card.getAllCards().clear();
        assertFalse(Card.getAllCards().containsKey("Suijin"));
        ImportExportController.importCard("Suijin");
        assertTrue(Card.getAllCards().containsKey("Suijin"));
        File file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + "Suijin" + ".json");
        file.delete();
    }

    @Test
    @DisplayName("Import card test spell")
    void importTest2() throws Exception {
        DatabaseController.loadGameCards();
        ImportExportController.exportCard("Pot of Greed");
        Card.getAllCards().clear();
        assertFalse(Card.getAllCards().containsKey("Pot of Greed"));
        ImportExportController.importCard("Pot of Greed");
        assertTrue(Card.getAllCards().containsKey("Pot of Greed"));
        File file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + "Pot of Greed" + ".json");
        file.delete();
    }

    @Test
    @DisplayName("Import card test trap")
    void importTest3() throws Exception {
        DatabaseController.loadGameCards();
        ImportExportController.exportCard("Negate Attack");
        Card.getAllCards().clear();
        assertFalse(Card.getAllCards().containsKey("Negate Attack"));
        ImportExportController.importCard("Negate Attack");
        assertTrue(Card.getAllCards().containsKey("Negate Attack"));
        File file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + "Negate Attack" + ".json");
        file.delete();
    }
}