package controller;

import java.util.HashMap;

public class ScoreBoardController {
    private static ScoreBoardController instance = null;

    public static ScoreBoardController getInstance() {
        if (instance == null) {
            instance = new ScoreBoardController();
        }
        return instance;
    }

    public HashMap<String, Integer> showScoreBoard() {
        return null;
    }
}
