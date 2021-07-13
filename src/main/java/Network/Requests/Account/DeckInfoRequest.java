package Network.Requests.Account;

import Network.Requests.Request;

public class DeckInfoRequest extends Request {
    public DeckInfoRequest(String authToken) {
        this.authToken = authToken;
    }
}
