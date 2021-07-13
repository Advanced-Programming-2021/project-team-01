package Network.Requests.Account;

import Network.Requests.Request;

public class ActivateDeckRequest extends Request {
    String deckName;

    public ActivateDeckRequest(String deckName, String authToken) {
        this.deckName = deckName;
        this.authToken = authToken;
    }

    public String getDeckName() {
        return deckName;
    }
}
