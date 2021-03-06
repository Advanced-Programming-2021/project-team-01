package view.transions;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import model.Board;
import model.ZoneSlot;
import model.card.Card;
import view.CardView;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class SelectCardDialog extends Dialog<List<Card>> {
    public List<Card> selectedCards;
    public int numberOfNeededCard;

    public SelectCardDialog(List<Card> cards, int numberOfNeededCard) {
        this.numberOfNeededCard = numberOfNeededCard;
        selectedCards = new ArrayList<>();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPrefWidth(600);
        gridPane.setPrefHeight(600);
        gridPane.setVgap(10);
        gridPane.setHgap(25);
        GameView.getInstance().setBackgroundImage(gridPane, "/view/graveyardPopUpBackground.jpeg", 600, 600);
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/view/graveyardPopUpBackground.jpeg").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(600, 600, false, false, false, false));
        gridPane.setBackground(new Background(backgroundimage));
        Board board = GameController.getInstance().getGameBoard();
        for (int i = 0; i < cards.size(); i++) {
            int column = i % 3;
            int row = i / 3;
            CardView cardView = null;
            Card card = cards.get(i);
            ZoneSlot zoneSlot = board.getZoneSlotByCard(card);
            if (zoneSlot != null) {
                if (board.getOwnerOfCard(card) == GameController.getInstance().getOpponentPlayerNumber()) {
                    if (zoneSlot.isHidden())
                        cardView = new CardView(cards.get(i), board.getOwnerOfCard(cards.get(i)), true, false);
                    else
                        cardView = new CardView(cards.get(i), board.getOwnerOfCard(cards.get(i)), false, false);
                } else
                    cardView = new CardView(cards.get(i), board.getOwnerOfCard(cards.get(i)), false, false);
            } else if (board.isCardInHand(card)) {
                if (board.getOwnerOfCard(card) == GameController.getInstance().getCurrentPlayerNumber()) {
                    cardView = new CardView(card, board.getOwnerOfCard(card), false, false);
                } else
                    cardView = new CardView(card, board.getOwnerOfCard(card), true, false);
            } else
                cardView = new CardView(card, board.getOwnerOfCard(card), false, false);
            CardView finalCardView = cardView;
            cardView.setOnMouseClicked(event -> {
                selectedCards.add(finalCardView.getCard());
                gridPane.getChildren().remove(finalCardView);
                decreaseNumberOfNeededCard();
                if (getNumberOfNeededCard() == 0) {
                    setResult(selectedCards);
                    close();
                    GameController.getInstance().getSelectedCard().unlock();
                }
            });
            GridPane.setRowIndex(cardView, row);
            GridPane.setColumnIndex(cardView, column);
            gridPane.getChildren().add(cardView);
        }
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        getDialogPane().setContent(scrollPane);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());
    }

    public int getNumberOfNeededCard() {
        return numberOfNeededCard;
    }

    public List<Card> getSelectedCards() {
        return selectedCards;
    }

    public void decreaseNumberOfNeededCard() {
        this.numberOfNeededCard -= 1;
    }
}
