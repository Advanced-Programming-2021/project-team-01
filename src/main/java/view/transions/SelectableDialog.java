package view.transions;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import model.Board;
import model.card.Card;
import view.CardView;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class SelectableDialog extends Dialog<List<Card>> {
    public List<Card> selectedCards;

    public SelectableDialog(List<Card> cards) {
        selectedCards = new ArrayList<>();
        GridPane gridPaneAllCars = new GridPane();
        gridPaneAllCars.setAlignment(Pos.CENTER);
        gridPaneAllCars.setPrefWidth(600);
        gridPaneAllCars.setPrefHeight(280);
        gridPaneAllCars.setVgap(10);
        gridPaneAllCars.setHgap(25);

        GridPane gridPaneSelectedCards = new GridPane();
        gridPaneSelectedCards.setAlignment(Pos.CENTER);
        gridPaneSelectedCards.setPrefWidth(600);
        gridPaneSelectedCards.setPrefHeight(280);
        gridPaneSelectedCards.setVgap(10);
        gridPaneSelectedCards.setHgap(25);

        gridPaneAllCars.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY, Insets.EMPTY)));
        gridPaneSelectedCards.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,CornerRadii.EMPTY, Insets.EMPTY)));


        Board board = GameController.getInstance().getGameBoard();
        for (int i = 0; i < cards.size(); i++) {
            CardView cardView = new CardView(cards.get(i), board.getOwnerOfCard(cards.get(i)), false, false);
            cardView.setOnMouseClicked(event -> {
                if (gridPaneAllCars.getChildren().contains(cardView)) {
                    gridPaneAllCars.getChildren().remove(cardView);
//                    GridPane.setRowIndex(cardView, 0);
//                    GridPane.setColumnIndex(cardView, selectedCards.size());
                    selectedCards.add(cardView.getCard());
                    gridPaneSelectedCards.getChildren().add(cardView);
                }else if (gridPaneSelectedCards.getChildren().contains(cardView)){
                    gridPaneSelectedCards.getChildren().remove(cardView);
//                    GridPane.setRowIndex(cardView, 0);
//                    GridPane.setColumnIndex(cardView, gridPaneAllCars.getChildren().size());
                    selectedCards.remove(cardView.getCard());
                    gridPaneAllCars.getChildren().add(cardView);
                }
            });
//            GridPane.setRowIndex(cardView, 0);
//            GridPane.setColumnIndex(cardView, i);
//            gridPaneAllCars.getChildren().add(cardView);
            gridPaneAllCars.add(cardView,i,0);
        }
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);

        ScrollPane scrollPaneAllCards = new ScrollPane();
        scrollPaneAllCards.setPrefWidth(600);
        scrollPaneAllCards.setPrefHeight(280);
        scrollPaneAllCards.setContent(gridPaneAllCars);
        scrollPaneAllCards.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneAllCards.setFitToHeight(true);

        ScrollPane scrollPaneSelectedCards = new ScrollPane();
        scrollPaneSelectedCards.setPrefWidth(600);
        scrollPaneSelectedCards.setPrefHeight(280);
        scrollPaneSelectedCards.setContent(gridPaneSelectedCards);
        scrollPaneSelectedCards.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneSelectedCards.setFitToHeight(true);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefHeight(600);
        vBox.setPrefWidth(600);

        Button button = new Button();
        button.setAlignment(Pos.CENTER);
        button.setPrefHeight(30);
        button.setPrefWidth(100);
        button.setText("Done!");
        button.setOnMouseClicked(event -> {
            setResult(selectedCards);
            close();
        });

        vBox.getChildren().addAll(button,scrollPaneAllCards, scrollPaneSelectedCards);
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/view/graveyardPopUpBackground.jpeg").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(600, 600, false, false, false, false));
        vBox.setBackground(new Background(backgroundimage));

        getDialogPane().setContent(vBox);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());
    }


}
