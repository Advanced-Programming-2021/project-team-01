package view.transions;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import model.Board;
import model.card.Card;
import view.CardView;
import view.GameView;

import java.util.List;


public class GraveyardPopUp extends Popup {
    public GraveyardPopUp(List<Card> cards) {
        centerOnScreen();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPrefWidth(600);
        gridPane.setPrefHeight(600);
        gridPane.setVgap(10);
        gridPane.setHgap(25);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        GameView.getInstance().setBackgroundImage(gridPane,"/view/graveyardPopUpBackground.jpeg",600,600);
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/view/graveyardPopUpBackground.jpeg").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(600, 600, false, false, false, false));
        gridPane.setBackground(new Background(backgroundimage));
        gridPane.setOnMouseClicked(event -> {
            hide();
            GameController.getInstance().getSelectedCard().unlock();
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
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        getContent().add(scrollPane);
        requestFocus();
    }

}
