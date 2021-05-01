package controller;

import model.Player;

import java.util.ArrayList;

public class ScoreBoardController {
    private static ScoreBoardController instance = null;

    public static ScoreBoardController getInstance() {
        if (instance == null) {
            instance = new ScoreBoardController();
        }
        return instance;
    }

    public ArrayList<Player> sortedScoreBoard() {
        ArrayList<Player> players = DatabaseController.getAllPlayers();
        players.sort(new Player.SortByScore());
        return players;
    }
}
