package Network.Requests.Account;

import Network.Requests.Request;

public class DeleteDeckRequest extends Request {
    String deckName;

    public DeleteDeckRequest(String deckName, String authToken) {
        this.deckName = deckName;
        this.authToken = authToken;
    }

    public String getDeckName() {
        return deckName;
    }
}
