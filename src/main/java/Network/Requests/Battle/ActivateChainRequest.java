package Network.Requests.Battle;

import Network.Requests.Request;

public class ActivateChainRequest extends Request {
    String opponent;

    public ActivateChainRequest(String opponent) {
        this.opponent = opponent;
    }
}
