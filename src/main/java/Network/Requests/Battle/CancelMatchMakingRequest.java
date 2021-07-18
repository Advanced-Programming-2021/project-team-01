package Network.Requests.Battle;

import Network.Requests.Request;

public class CancelMatchMakingRequest extends Request {
    String token;
    public CancelMatchMakingRequest(String token) {
        this.token = token;
    }
}
