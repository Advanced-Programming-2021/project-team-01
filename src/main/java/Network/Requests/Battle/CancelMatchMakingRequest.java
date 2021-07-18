package Network.Requests.Battle;

import Network.Requests.Request;

public class CancelMatchMakingRequest extends Request {
    public CancelMatchMakingRequest(String token) {
        this.authToken = token;
    }

}
