package view;

import model.card.CardLocation;

public class ZoneLocation {

    private int playerNumber;
    private int index;
    private CardLocation cardLocation;

    public ZoneLocation(int playerNumber, int index, CardLocation cardLocation) {
        this.playerNumber = playerNumber;
        this.index = index;
        this.cardLocation = cardLocation;
    }

    public int getIndex() {
        return index;
    }

    public CardLocation getCardLocation() {
        return cardLocation;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }


    public boolean equal(int playerNumber, int index, CardLocation cardLocation) {
        return playerNumber == this.playerNumber && index == this.index && cardLocation == this.cardLocation;
    }
}
