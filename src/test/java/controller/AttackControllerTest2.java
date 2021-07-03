package controller;

import model.Board;
import model.GamePhase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AttackControllerTest2 {
    GameController gameController;
    Board board;

    @BeforeEach
    public void init() throws Exception {
        gameController = GameController.getInstance();
        DatabaseController.loadGameCards();
    }

    @Test
    @DisplayName("direct attack")
    void test1() throws Exception {
        RegisterController.getInstance().loginUser("ali", "123");
        gameController.startGame("username", 1);
        board = gameController.getGameBoard();
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand", 3);
        gameController.summon();
        for (int i = 0; i < 13; i++)
            gameController.nextPhase();
        assertEquals(gameController.getPhaseController().getGamePhase(), GamePhase.BATTLE_PHASE);
        gameController.selectPlayerCard("monster", 1);
        gameController.directAttack();
        assertEquals(gameController.getOpponentLp(), 6000);
    }

    @Test
    @DisplayName("attack to DO")
    void test2() throws Exception {
        RegisterController.getInstance().loginUser("ali", "123");
        gameController.startGame("username", 1);
        board = gameController.getGameBoard();
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand", 4);
        gameController.setMonster();
        for (int i = 0; i < 6; i++)
            gameController.nextPhase();
        gameController.cheater("Battle warrior");
        gameController.selectPlayerCard("hand", 6);
        gameController.summon();
        for (int i = 0; i < 12; i++)
            gameController.nextPhase();
        gameController.selectPlayerCard("hand", 2);
        gameController.summon();
        for (int i = 0; i < 13; i++)
            gameController.nextPhase();
        gameController.gameBoard.showBoard();
        assertEquals(gameController.getPhaseController().getGamePhase(), GamePhase.BATTLE_PHASE);
        gameController.selectPlayerCard("monster", 1);
        gameController.attack(1);
        assertEquals(gameController.getCurrentLp(), 7550);
        gameController.selectPlayerCard("monster", 2);
        gameController.attack(1);
        board.showBoard();
        assertEquals(gameController.getOpponentLp(), 8000);
        assertNull(board.getPlayerOneMonsterZone()[1].getCard());
    }

    @Test
    @DisplayName("flip summon")
    void test3() throws Exception {
        RegisterController.getInstance().loginUser("ali", "123");
        gameController.startGame("username", 1);
        board = gameController.getGameBoard();
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand", 4);
        gameController.setMonster();
        assertTrue(board.getPlayerOneMonsterZone()[1].isHidden());
        assertTrue(board.getPlayerOneMonsterZone()[1].isDefending());
        for (int i = 0; i < 12; i++)
            gameController.nextPhase();
        gameController.selectOpponentCard("hand", 1);
        gameController.selectPlayerCard("monster", 1);
        board.showBoard();
        gameController.flipSummon();
        assertFalse(board.getPlayerOneMonsterZone()[1].isHidden());
        assertFalse(board.getPlayerOneMonsterZone()[1].isDefending());
    }

    @Test
    @DisplayName("change position")
    void test4() throws Exception {
        RegisterController.getInstance().loginUser("ali", "123");
        gameController.startGame("username", 1);
        board = gameController.getGameBoard();
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand", 4);
        gameController.summon();
        assertFalse(board.getPlayerOneMonsterZone()[1].isHidden());
        assertFalse(board.getPlayerOneMonsterZone()[1].isDefending());
        for (int i = 0; i < 12; i++)
            gameController.nextPhase();
        gameController.selectOpponentCard("hand", 1);
        gameController.selectPlayerCard("monster", 1);
        board.showBoard();
        gameController.changeCardPosition("defense");
        assertFalse(board.getPlayerOneMonsterZone()[1].isHidden());
        assertTrue(board.getPlayerOneMonsterZone()[1].isDefending());
        board.showBoard();
    }

    @Test
    void test5() throws Exception {
        RegisterController.getInstance().loginUser("ali", "123");
        gameController.startGame("username", 1);
        board = gameController.getGameBoard();
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand", 4);
        gameController.setMonster();
        for (int i = 0; i < 6; i++)
            gameController.nextPhase();
        gameController.cheater("Battle warrior");
        gameController.selectPlayerCard("hand", 6);
        gameController.summon();
        for (int i = 0; i < 12; i++)
            gameController.nextPhase();
        gameController.selectPlayerCard("hand", 2);
        gameController.summon();
        for (int i = 0; i < 13; i++)
            gameController.nextPhase();
        gameController.gameBoard.showBoard();
        assertEquals(gameController.getPhaseController().getGamePhase(), GamePhase.BATTLE_PHASE);
        gameController.selectPlayerCard("monster", 1);
        gameController.attack(1);
        for (int i = 0; i < 5; i++) {
            gameController.nextPhase();
        }
        assertEquals(gameController.getPhaseController().getGamePhase(),GamePhase.MAIN_PHASE1);
        gameController.selectPlayerCard("monster",1);
        assertFalse(board.getPlayerOneMonsterZone()[1].isHidden());
        assertTrue(board.getPlayerOneMonsterZone()[1].isDefending());
        gameController.changeCardPosition("attack");
        assertFalse(board.getPlayerOneMonsterZone()[1].isHidden());
        assertFalse(board.getPlayerOneMonsterZone()[1].isDefending());
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
    }

}