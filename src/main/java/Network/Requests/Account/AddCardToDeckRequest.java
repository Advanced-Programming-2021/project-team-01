package Network.Requests.Account;

import Network.Requests.Request;

public class AddCardToDeckRequest extends Request {
    private String deckType;
    private String cardName;
    private String deckName;

    public AddCardToDeckRequest(String deckType, String cardName, String deckName, String authToken) {
        this.deckType = deckType;
        this.cardName = cardName;
        this.deckName = deckName;
        this.authToken = authToken;
    }

    public String getDeckType() {
        return deckType;
    }

    public String getCardName() {
        return cardName;
    }

    public String getDeckName() {
        return deckName;
    }
}
