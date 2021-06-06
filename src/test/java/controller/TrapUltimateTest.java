package controller;

import endpoint.MainConsole;
import model.Board;
import model.card.Card;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


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
        MainConsole.main(new String[1]);
        board = GameController.instance.getGameBoard();
        gameController = GameController.instance;
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
        System.setIn(sysInBackup);
        in.close();
    }


}