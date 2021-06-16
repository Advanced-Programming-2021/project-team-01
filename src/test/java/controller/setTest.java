package controller;

import controller.exceptions.CardNotInPosition;
import controller.exceptions.InvalidSelection;
import model.Board;
import model.card.MonsterCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class setTest {
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
    @DisplayName("setMonster")
    void test1() throws Exception {
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand",3);
        gameController.setCard();
        assertTrue(board.getPlayerOneMonsterZone()[1].getCard() instanceof MonsterCard);
        gameController.selectPlayerCard("hand",2);
        gameController.setCard();
        assertNotNull(board.getPlayerSpellZone(1)[1]);
        board.showBoard();
        board.showGraveyard(1);
        assertFalse(board.isMonsterZoneEmpty(1));
        assertNull(board.getCounterTraps(2));
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
    }

}