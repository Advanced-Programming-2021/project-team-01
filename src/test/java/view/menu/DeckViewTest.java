package view.menu;

import com.opencsv.exceptions.CsvValidationException;
import controller.DatabaseController;
import controller.RegisterController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Menu;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DeckViewTest {
    HandleRequestType handleRequestType;

    @BeforeEach
    void init() {
        handleRequestType = new HandleRequestType();
        handleRequestType.deckView = new DeckView();
        handleRequestType.mainMenu = new MainMenu();
        handleRequestType.gameView = new GameView();
        handleRequestType.shopView = new ShopView();
        handleRequestType.profileView = new ProfileView();
        handleRequestType.scoreBoardView = new ScoreBoardView();
        handleRequestType.registerView = new RegisterView();
    }

    @BeforeEach
    void createDeck() throws IOException, CsvValidationException {
        DatabaseController.loadGameCards();
        handleRequestType.getRegisterView().run("user login u x p x");
        handleRequestType.getMainMenu().run("menu enter Deck");
        assertEquals(HandleRequestType.currentMenu, Menu.DECK_MENU);
        handleRequestType.getDeckView().run("deck create deck1");
    }

    @AfterEach
    void deleteAndLogout() throws IOException {
        handleRequestType.getDeckView().run("deck delete deck1");
        assertNull(DatabaseController.getDeckByName("deck1"));
        handleRequestType.getMainMenu().run("user logout");
    }

    @Test
    void enterExit() throws IOException {

        assertNotNull(DatabaseController.getDeckByName("deck1"));

        handleRequestType.getDeckView().run("deck set-activate deck1");
        assertEquals(RegisterController.onlineUser.getActiveDeck(), "deck1");

        handleRequestType.getDeckView().run("deck add-card card Pot of Greed deck deck1");
        assertEquals(DatabaseController.getDeckByName("deck1").getMainDeck().get(0).getName(), "Pot of Greed");
        handleRequestType.getDeckView().run("deck rm-card card Pot of Greed deck deck1");
        handleRequestType.getDeckView().run("deck show deck-name deck1");
        handleRequestType.getDeckView().run("deck show all");
        System.out.println(DatabaseController.getDeckByName("deck1").getMainDeck().size());
        assertTrue(DatabaseController.getDeckByName("deck1").getMainDeck().isEmpty());


    }
}
