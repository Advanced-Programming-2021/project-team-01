package Network.Requests.Battle;

import Network.Requests.Request;
import model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class SendNeededCardsRequest extends Request {
    private List<Card> neededCards;
    private String opponent;

    public SendNeededCardsRequest(List<Card> cards, String opponent) {
        neededCards = cards;
        this.opponent = opponent;
    }

    public List<Card> getNeededCards() {
        return neededCards;
    }

    public String getOpponent() {
        return opponent;
    }
}
