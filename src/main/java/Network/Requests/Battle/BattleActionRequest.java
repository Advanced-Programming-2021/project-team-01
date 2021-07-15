package Network.Requests.Battle;

import Network.Requests.Request;
import model.networkLocators.BattleAction;

public class BattleActionRequest extends Request {
    private String opponentUserName;
    private BattleAction battleAction;

    public BattleActionRequest(String authToken, String opponentUserName, BattleAction battleAction) {
        this.authToken = authToken;
        this.opponentUserName = opponentUserName;
        this.battleAction = battleAction;
    }

    public String getOpponentUserName() {
        return opponentUserName;
    }

    public BattleAction getBattleAction() {
        return battleAction;
    }
}
