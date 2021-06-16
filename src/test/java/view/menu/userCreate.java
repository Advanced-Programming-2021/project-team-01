package view.menu;

import controller.DatabaseController;
import controller.RegisterController;
import controller.exceptions.NicknameExists;
import controller.exceptions.UsernameExists;
import controller.exceptions.WrongUsernamePassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import view.Menu;

import static org.junit.jupiter.api.Assertions.*;

public class userCreate {
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
    void user() {
        assertThrows(UsernameExists.class, () -> RegisterController.getInstance().createUser("ali", "x", "x4"));
        assertThrows(NicknameExists.class, () -> RegisterController.getInstance().createUser("mor","123","hoy"));
        assertThrows(WrongUsernamePassword.class,() -> RegisterController.getInstance().loginUser("ali","123213123"));
    }
}
