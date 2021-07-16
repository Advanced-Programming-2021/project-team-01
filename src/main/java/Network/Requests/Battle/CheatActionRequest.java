package Network.Requests.Battle;

import model.networkLocators.BattleAction;

public class CheatActionRequest extends BattleActionRequest{
    private String cardName;
    public CheatActionRequest(String cardName, String authToken, String opponentUserName, BattleAction battleAction) {
        super(authToken, opponentUserName, battleAction);
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}
