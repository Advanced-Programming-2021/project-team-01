package view;

import controller.RegisterController;
import controller.ScoreBoardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardView {
    public void init(Pane root) {
        ListView<String> scoreTable = new ListView<>();
        ObservableList<String> scores = FXCollections.observableArrayList(getSortedScoreBoard());
        scoreTable.setItems(scores);
        scoreTable.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        scoreTable.setCellFactory(cell -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setTextFill(Color.WHITE);
                    setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    String regex = "\\d+- (" + RegisterController.onlineUser.getUsername() + ") : \\d+";
                    if (getIndex() < 20 && item.matches(regex)) {
                        setTextFill(Color.ORANGE);
                        setBackground(new Background(new BackgroundFill(Color.rgb(0, 66, 99), CornerRadii.EMPTY, Insets.EMPTY)));
                        setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    }
                }else {
                    setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }
        });
        scoreTable.setPrefWidth(300);
        scoreTable.setPrefHeight(400);
        scoreTable.setTranslateX(533);
        scoreTable.setTranslateY(150);
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
