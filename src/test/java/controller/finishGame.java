package controller;

import endpoint.Main;
import model.Board;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.Menu;
import view.menu.HandleRequestType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class finishGame {
    InputStream sysInBackup;
    ByteArrayInputStream in;
    Board board;
    GameController gameController;

    @BeforeEach
    void init() throws Exception {
        String input = new String(Files.readAllBytes(Paths.get("src/test/java/controller/finishGame.txt")));
        sysInBackup = System.in; // backup System.in to restore it later
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        HandleRequestType.scanner = new Scanner(System.in);
        Main.main(new String[1]);
        board = GameController.getInstance().getGameBoard();
        gameController = GameController.getInstance();
    }

    @Test
    @DisplayName("player one win")
    void test1(){
        assertEquals(HandleRequestType.currentMenu, Menu.MAIN_MENU);
    }

    @AfterEach
    @DisplayName("cleanUp")
    void reset() throws IOException {
        gameController.endJunit();
        System.setIn(sysInBackup);
        in.close();
    }

}