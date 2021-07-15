package model.networkLocators;

import model.card.CardLocation;

public class BattleAction {
    private BattleState battleState;
    private CardLocation cardLocation;
    private int index;
    private int playerNumber;

    public BattleAction(BattleState battleState, CardLocation cardLocation, int index) {
        this.battleState = battleState;
        this.cardLocation = cardLocation;
        this.index = index;
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public CardLocation getCardLocation() {
        return cardLocation;
    }

    public int getIndex() {
        return index;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}