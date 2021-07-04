package view.transions;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import model.Board;
import model.card.Card;
import view.CardView;

import java.awt.*;
import java.util.List;


public class CustomPopup extends Popup {
    public CustomPopup(List<Card> cards) {
        setAnchorX(700);
        setAnchorY(200);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPrefWidth(600);
        gridPane.setPrefHeight(600);
        gridPane.setVgap(10);
        gridPane.setHgap(25);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setOnMouseClicked(event -> {
            hide();
        });
        Board board = GameController.getInstance().getGameBoard();
        for (int i = 0; i < cards.size(); i++) {
            int column = i % 3;
            int row = i / 3;
            CardView cardView = new CardView(cards.get(i), board.getOwnerOfCard(cards.get(i)), false, false);
            GridPane.setRowIndex(cardView, row);
            GridPane.setColumnIndex(cardView, column);
            gridPane.getChildren().add(cardView);
        }
        javafx.scene.control.ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        getContent().add(scrollPane);
        requestFocus();
    }

}
