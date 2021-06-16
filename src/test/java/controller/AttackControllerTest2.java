package controller;

import model.Board;
import model.GamePhase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.menu.GameView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AttackControllerTest2 {
    Board board;
    GameController gameController;

    @BeforeEach
    public void init() throws Exception {
        board = GameController.getInstance().getGameBoard();
        gameController = GameController.getInstance();
    }

    @Test
    @DisplayName("direct attack")
    void test1() throws Exception {
        RegisterController.getInstance().loginUser("ali", "123");
        gameController.startGame("username", 1);
        gameController.nextPhase();
        gameController.nextPhase();
        gameController.selectPlayerCard("hand", 3);
        gameController.summon();
        for (int i = 0; i < 13; i++)
            gameController.nextPhase();
        assertEquals(gameController.getPhaseController().getGamePhase(), GamePhase.BATTLE_PHASE);
        gameController.selectPlayerCard("monster",1);
        gameController.directAttack();
        assertEquals(gameController.getOpponentLp(),6000);
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
    }

}