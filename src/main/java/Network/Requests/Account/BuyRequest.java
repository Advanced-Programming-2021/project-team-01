package Network.Requests.Account;

import Network.Requests.Request;

public class BuyRequest extends Request {
    private String cardName;

    public BuyRequest(String cardName, String authToken) {
        this.cardName = cardName;
        this.authToken = authToken;
    }

    public String getCardName() {
        return cardName;
    }
}
