package Network.Requests.Battle;

import Network.Requests.Request;

public class ActivateChainRequest extends Request {
    String opponent;
    boolean aBoolean;

    public ActivateChainRequest(String opponent, boolean aBoolean) {
        this.opponent = opponent;
        this.aBoolean = aBoolean;
    }

    public boolean getBoolean() {
        return aBoolean;
    }

    public String getOpponent() {
        return opponent;
    }
}
