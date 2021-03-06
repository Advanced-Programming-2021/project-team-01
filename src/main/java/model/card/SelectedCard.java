package model.card;

import controller.GameController;
import model.Board;
import model.Player;


public class SelectedCard {
    CardLocation cardLocation;
    int index;
    Player player;
    Card card;
    boolean isLocked = false;

    public void lock(){
        isLocked = true;
    }

    public void unlock(){
        isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public CardLocation getCardLocation() {
        return cardLocation;
    }

    public void setCardLocation(CardLocation cardLocation) {
        this.cardLocation = cardLocation;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    private void setCard(Card card) {
        this.card = card;
    }

    public void reset() {
        card = null;
        cardLocation = null;
        player = null;
        index = -1;
    }


    public void set(Card card, Player player, int index, CardLocation location) {
        setCard(card);
        setCardLocation(location);
        setPlayer(player);
        setIndex(index);
    }
    public void set(Card card){
        setCard(card);
        GameController gameController = GameController.getInstance();
        if (gameController.getCurrentPlayerNumber() == gameController.getGameBoard().getOwnerOfCard(card))
            setPlayer(GameController.getCurrentPlayer());
        else
            setPlayer(GameController.getOpponent());
        Board board = gameController.getGameBoard();
        setCardLocation(board.getCardLocation(card));
        setIndex(board.getIndexOfCard(this));
    }
}
