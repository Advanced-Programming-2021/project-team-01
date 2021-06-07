package view;

import controller.ScoreBoardController;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import model.Player;

import java.util.ArrayList;

public class ScoreboardView {
    public void init(Pane root) {
        ListView<String> scoreTable = new ListView<>();
        Observable scores = FXCollections.observableArrayList(getSortedScoreBoard());
        scoreTable.setItems((ObservableList<String>) scores);
        scoreTable.setPrefWidth(300);
        scoreTable.setPrefHeight(400);
        scoreTable.setTranslateX(210);
        scoreTable.setTranslateY(350);
        root.getChildren().add(scoreTable);
    }

    private ArrayList getSortedScoreBoard() {
        ArrayList<Player> players = ScoreBoardController.getInstance().getSortedScoreBoard();
        ArrayList<String> nameOfPlayers = new ArrayList<>();
        int index = 1;
        for (Player player : players) {
            nameOfPlayers.add(index + "- " + player.getUsername() + ": " + player.getScore());
            index++;
        }
        return nameOfPlayers;
    }

    @FXML
    private void exitMenu() {
        ViewSwitcher.switchTo(View.MAIN);
    }
}
