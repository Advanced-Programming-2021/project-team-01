package controller;

import endpoint.Main;
import model.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        Main.main(new String[1]);
        board = GameController.instance.getGameBoard();
        gameController = GameController.instance;
    }

    @Test
    @DisplayName("attack to attack")
    void test1(){

    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
        System.setIn(sysInBackup);
        in.close();
    }

}