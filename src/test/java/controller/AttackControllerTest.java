package controller;

import controller.exceptions.CardNotInPosition;
import controller.exceptions.InvalidSelection;
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

class AttackControllerTest {

    InputStream sysInBackup;
    ByteArrayInputStream in;
    Board board;
    GameController gameController;

    @BeforeEach
    void init() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/init.txt")));
        sysInBackup = System.in; // backup System.in to restore it later
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        board = GameController.getInstance().getGameBoard();
        gameController = GameController.getInstance();
    }

    @Test
    @DisplayName("attack to attack")
    void test1() throws Exception{
        gameController.nextPhase();
        int opPoint = gameController.getOpponentLp();
        gameController.selectPlayerCard("monster",1);
        gameController.attack(1);
        int opPoint2 = gameController.getOpponentLp();
        assertEquals(800 ,opPoint - opPoint2);
        assertNull(board.getPlayerTwoMonsterZone()[1].getCard());
        assertNotNull(board.getPlayerOneMonsterZone()[1].getCard());
    }

    @Test
    @DisplayName("attack to attack")
    void test2() throws Exception{
        gameController.nextPhase();
        int opPoint = gameController.getOpponentLp();
        gameController.selectPlayerCard("monster",1);
        gameController.attack(2);
        int opPoint2 = gameController.getOpponentLp();
        assertEquals(0 ,opPoint - opPoint2);
        assertNull(board.getPlayerTwoMonsterZone()[2].getCard());
        assertNull(board.getPlayerOneMonsterZone()[1].getCard());
        assertEquals(board.getPlayerOneGraveYard().size(),1);
        assertEquals(board.getPlayerTwoGraveYard().size(),1);
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
        System.setIn(sysInBackup);
        in.close();
    }

}