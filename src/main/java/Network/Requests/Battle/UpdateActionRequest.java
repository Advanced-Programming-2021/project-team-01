package Network.Requests.Battle;

import Network.Requests.Request;
import model.networkLocators.BattleAction;

public class UpdateActionRequest extends Request {
    String opponentUsername;
    BattleAction battleAction;

    public UpdateActionRequest(String opponentUsername, BattleAction battleAction) {
        this.opponentUsername = opponentUsername;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public BattleAction getBattleAction() {
        return battleAction;
    }

}
