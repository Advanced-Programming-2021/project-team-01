package Network.Requests.Account;

import Network.Requests.Request;

import java.awt.*;

public class CustomizeDeckRequest extends Request {
    String deckName;

    public CustomizeDeckRequest(String deckName, String authToken) {
        this.deckName = deckName;
        this.authToken = authToken;
    }

    public String getDeckName() {
        return deckName;
    }
}
