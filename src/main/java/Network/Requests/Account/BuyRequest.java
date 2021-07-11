package Network.Requests.Account;

import Network.Requests.Request;

public class BuyRequest extends Request {
    private String cardName;

    public BuyRequest(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }
}
