package model.networkLocators;

import model.card.CardLocation;

public class CheatBattleAction extends BattleAction{
    private String cardName;
    public CheatBattleAction(String cardName ,BattleState battleState, CardLocation cardLocation, int index, int playerNumber) {
        super(battleState, cardLocation, index, playerNumber);
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}
