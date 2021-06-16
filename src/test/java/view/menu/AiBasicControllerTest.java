package view.menu;

import controller.GameController;
import endpoint.Main;
import model.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.menu.*;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AiBasicControllerTest {
    HandleRequestType handleRequestType;
    ByteArrayInputStream in;


    @BeforeEach
    void init() {
        handleRequestType = new HandleRequestType();
        GameController.getInstance();
        handleRequestType.deckView = new DeckView();
        handleRequestType.mainMenu = new MainMenu();
        handleRequestType.gameView = new GameView();
        handleRequestType.shopView = new ShopView();
        handleRequestType.profileView = new ProfileView();
        handleRequestType.scoreBoardView = new ScoreBoardView();
        handleRequestType.registerView = new RegisterView();
        handleRequestType.getRegisterView().run("user login u ali p 123");
        handleRequestType.getMainMenu().run("duel new ai rounds 1");
    }

    @Test
    @DisplayName("draw ai")
    void draw() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/init.txt")));
        in = new ByteArrayInputStream(input.getBytes());
        HandleRequestType.scanner = new Scanner(in);
        Main.main(new String[1]);
        assertEquals(17,GameController.getInstance().getGameBoard().getPlayerDrawZone(2).size());
    }


    @AfterEach
    void cleanUp(){
        handleRequestType.getMainMenu().run("user logout");
    }


}