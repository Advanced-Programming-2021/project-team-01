package view.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class enterExitTest {
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

    @Test
    void enterExit() {
        handleRequestType.getRegisterView().run("user login u ali p 123");
        assertEquals(HandleRequestType.currentMenu, Menu.MAIN_MENU);
        handleRequestType.getMainMenu().run("menu enter Shop");
        handleRequestType.getShopView().run("menu entry Login");
        assertEquals(HandleRequestType.currentMenu, Menu.SHOP);
        handleRequestType.getShopView().run("menu exit");
        assertEquals(HandleRequestType.currentMenu, Menu.MAIN_MENU);
        handleRequestType.getMainMenu().run("menu enter Scoreboard");
        assertEquals(HandleRequestType.currentMenu, Menu.SCOREBOARD);
        handleRequestType.getScoreBoardView().run("menu enter Shop");
        assertNotEquals(HandleRequestType.currentMenu, Menu.SHOP);
        handleRequestType.getScoreBoardView().run("menu exit");
        handleRequestType.getMainMenu().run("menu enter Profile");
        assertEquals(HandleRequestType.currentMenu,Menu.PROFILE_VIEW);
        handleRequestType.getProfileView().run("menu exit");
        handleRequestType.getMainMenu().run("user logout");
    }
}
