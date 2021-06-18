package view.menu;

import controller.DatabaseController;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProfileViewTest {
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
    void changeProfile() {
        handleRequestType.getRegisterView().run("user login u x p x");
        handleRequestType.getMainMenu().run("menu enter Profile");
        handleRequestType.getProfileView().run("profile change nickname y");
        handleRequestType.getProfileView().run("profile change password new y current x");
        handleRequestType.getProfileView().run("menu exit");
        handleRequestType.getMainMenu().run("user logout");
        Player player = DatabaseController.getUserByName("x");
        assertEquals(player.getPassword(),"y");
        assertEquals(player.getNickname(),"y");
        handleRequestType.getRegisterView().run("user login u x p y");
        handleRequestType.getMainMenu().run("menu enter Profile");
        handleRequestType.getProfileView().run("profile change nickname x");
        handleRequestType.getProfileView().run("profile change password new x current y");
        handleRequestType.getProfileView().run("menu exit");
        handleRequestType.getMainMenu().run("user logout");
        Player player2 = DatabaseController.getUserByName("x");
        assertEquals(player2.getPassword(),"x");
        assertEquals(player2.getNickname(),"x");
    }
}
