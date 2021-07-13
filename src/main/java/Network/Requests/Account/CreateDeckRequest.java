package Network.Requests.Account;

import Network.Requests.Request;

public class CreateDeckRequest extends Request {
    String deckName;

    public CreateDeckRequest(String deckName, String authToken) {
        this.deckName = deckName;
        this.authToken = authToken;
    }

    public String getDeckName() {
        return deckName;
    }
}
