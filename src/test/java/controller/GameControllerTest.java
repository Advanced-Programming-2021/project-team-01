package controller;

import endpoint.Main;
import model.Board;
import model.card.Card;
import model.card.MonsterCard;
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
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {


    private InputStream sysInBackup;
    private ByteArrayInputStream in;
    private Board board;
    private GameController gameController;

    @BeforeEach
    public void init() throws Exception {
        gameController = GameController.getInstance();
        DatabaseController.loadGameCards();
    }

    @Test
    @DisplayName("field spell")
    void test1() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/field_test.txt")));
        sysInBackup = System.in;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        board = GameController.getInstance().getGameBoard();
        gameController = GameController.getInstance();
        gameController.selectPlayerCard("monster",4);
        assertEquals(gameController.getZoneSlotSelectedCard().getAttack(),800);
        assertEquals(gameController.getZoneSlotSelectedCard().getDefence(),1400);
        gameController.selectPlayerCard("hand",4);
        gameController.setCard();
        gameController.selectPlayerCard("field");
        assertNotNull(gameController.selectedCard.getCard());
        gameController.selectPlayerCard("monster",4);
        assertEquals(gameController.getZoneSlotSelectedCard().getAttack(),1300);
        assertEquals(gameController.getZoneSlotSelectedCard().getDefence(),1000);

    }

    @Test
    @DisplayName("twin twisters")
    void test2() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/twin_twisters.txt")));
        sysInBackup = System.in;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        board = GameController.getInstance().getGameBoard();
        gameController = GameController.getInstance();
        assertEquals(board.getPlayerOneGraveYard().size(),2);
        assertEquals(board.getPlayerTwoGraveYard().size(),2);
        ArrayList<String> names = new ArrayList<>();
        for (Card card : board.getPlayerTwoGraveYard()) {
            names.add(card.getName()) ;
        }
        assertTrue(names.contains("Twin Twisters"));

    }

    @Test
    @DisplayName("scanner")
    void test3() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/scanner_test.txt")));
        sysInBackup = System.in;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        board = GameController.getInstance().getGameBoard();
        gameController = GameController.getInstance();
        assertEquals(gameController.getZoneSlotSelectedCard().getAttack(),1200);
        assertEquals(gameController.getZoneSlotSelectedCard().getDefence(),700);
        assertEquals(gameController.getZoneSlotSelectedCard().getCard().getName(),"Baby dragon");
    }

    @Test
    @DisplayName("negateAttack")
    void negate() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/negateAttack")));
        sysInBackup = System.in;
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        assertEquals(8000,gameController.getOpponentLp());
        assertNull(gameController.getGameBoard().getPlayerSpellZone(2)[1].getCard());
    }

    @Test
    @DisplayName("black pendant")
    void blackT() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/blackPendant.txt")));
        in = new ByteArrayInputStream(input.getBytes());
        HandleRequestType.scanner = new Scanner(in);
        Main.main(new String[1]);
        int original = ((MonsterCard) gameController.getGameBoard().getPlayerOneMonsterZone()[1].getCard()).getAttack();
        int boost = gameController.getGameBoard().getPlayerOneMonsterZone()[1].getAttack();
        assertEquals(boost - original, 500);
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
        System.setIn(sysInBackup);
        in.close();
    }

}