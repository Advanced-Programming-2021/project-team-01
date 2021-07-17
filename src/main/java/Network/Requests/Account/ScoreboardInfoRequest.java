package Network.Requests.Account;

import Network.Requests.Request;

public class ScoreboardInfoRequest extends Request {
    public ScoreboardInfoRequest(String authToken) {
        this.authToken = authToken;
    }
}
