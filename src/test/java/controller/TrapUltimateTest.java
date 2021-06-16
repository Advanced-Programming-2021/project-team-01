package controller;

import endpoint.Main;
import model.Board;
import model.card.Card;
import org.junit.jupiter.api.*;
import view.menu.HandleRequestType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


class TrapUltimateTest {
    InputStream sysInBackup;
    ByteArrayInputStream in;
    Board board;
    GameController gameController;

    @BeforeEach
    @DisplayName("Initial trap card")
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
    @DisplayName("PotOfGreed")
    void test1() throws Exception {
        gameController.cheater("Pot of Greed");
        ArrayList<Card> hand;
        if (gameController.getCurrentPlayerNumber() == 1) hand = board.getPlayerOneHand();
        else hand = board.getPlayerTwoHand();
        board.showBoard();
        for (Card card : hand) {
            if (card.getName().equals("Pot of Greed")) {
                Assertions.assertTrue(true);
                return;
            }
        }
        Assertions.fail();
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
        System.setIn(sysInBackup);
        in.close();
    }


}