package controller;

import endpoint.Main;
import model.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.menu.HandleRequestType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    private InputStream sysInBackup;
    private GameController gameController;
    private Board board;
    private ByteArrayInputStream in;

    @BeforeEach
    void ready() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/init.txt")));
        sysInBackup = System.in;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        gameController = GameController.getInstance();
        board = gameController.getGameBoard();

    }

    @Test
    @DisplayName("Mystical space typhoon")
    void test1() throws Exception {
        for (int i = 0; i < 6; i++)
            gameController.nextPhase();
        gameController.cheater("Mystical space typhoon");
        gameController.selectPlayerCard("hand",6);
        gameController.activateEffect();
        assertNull(board.getPlayerSpellZone(2)[1].getCard());
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
        System.setIn(sysInBackup);
        in.close();
    }



}
