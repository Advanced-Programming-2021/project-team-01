package controller;

import controller.exceptions.CardNotInPosition;
import controller.exceptions.InvalidSelection;
import controller.exceptions.LevelFiveException;
import model.Board;
import model.card.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import view.menu.HandleRequestType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TributeTest {
    GameController gameController;
    Board board;

    @BeforeEach
    void init() throws Exception{
        RegisterController.getInstance().loginUser("ali", "123");
        gameController = GameController.getInstance();
        gameController.startGame("username", 1);
        gameController = GameController.getInstance();
        board = gameController.getGameBoard();
    }

    @Test
    void testTricky() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/tricky.txt")));
        gameController.selectPlayerCard("hand",1);
        Card card = gameController.getSelectedCard().getCard();
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        HandleRequestType.scanner = new Scanner(in);
        gameController.activateEffect();
        assertEquals(card, board.getPlayerOneMonsterZone()[1].getCard());
    }

    @Test
    void tributeSummon() throws Exception {
        gameController.selectPlayerCard("hand",1);
        gameController.nextPhase();
        gameController.nextPhase();
        assertThrows(LevelFiveException.class, () -> {
            gameController.summon();
        });
        gameController.tribute(3);
        assertNotNull(board.getPlayerOneMonsterZone()[1].getCard());
    }

    @AfterEach
    void cleanUp(){
        gameController.endJunit();
    }

}