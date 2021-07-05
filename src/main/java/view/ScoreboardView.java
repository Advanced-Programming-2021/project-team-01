package view;

import controller.RegisterController;
import controller.ScoreBoardController;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardView {
    public void init(Pane root) {
        ListView<String> scoreTable = new ListView<>();
        Observable scores = FXCollections.observableArrayList(getSortedScoreBoard());
        scoreTable.setItems((ObservableList<String>) scores);
        scoreTable.setCellFactory(cell -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setFont(Font.font(16));
                    String regex = "\\d+- (" + RegisterController.onlineUser.getUsername() + ") : \\d+";
                    if (getIndex() < 20 && item.matches(regex)) {
                        setTextFill(Color.BLUE);
                        setFont(Font.font(22));
                    }
                }
            }
        });
        scoreTable.setPrefWidth(300);
        scoreTable.setPrefHeight(400);
        scoreTable.setTranslateX(210);
        scoreTable.setTranslateY(350);
        root.getChildren().add(scoreTable);
    }

    private List<String> getSortedScoreBoard() {
        ArrayList<Player> players = ScoreBoardController.getInstance().getSortedScoreBoard();
        ArrayList<String> nameOfPlayers = new ArrayList<>();
        int index = 1;
        for (Player player : players) {
            nameOfPlayers.add(index + "- " + player.getUsername() + " : " + player.getScore());
            index++;
        }
        return nameOfPlayers;
    }

    @FXML
    private void exitMenu() {
        ViewSwitcher.switchTo(View.MAIN);
    }
}
