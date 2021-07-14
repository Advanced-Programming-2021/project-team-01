package Network.Responses.Account;

import Network.Requests.Account.RemoveCardFromDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.DatabaseController;
import controller.exceptions.CardNameNotExists;
import controller.exceptions.CardNotInDeck;
import controller.exceptions.DeckNotExists;
import model.Deck;
import model.card.Card;
import view.DeckView;
import view.ViewSwitcher;

import java.io.IOException;

public class RemoveCardFromDeckResponse extends Response {
    public RemoveCardFromDeckResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        RemoveCardFromDeckRequest request = (RemoveCardFromDeckRequest) super.request;
        String cardName = request.getCardName();
        String deckName = request.getDeckName();
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            exception = new CardNameNotExists(cardName);
            return;
        }
        if (!DatabaseController.doesDeckExists(deckName)) {
            exception = new DeckNotExists(deckName);
            return;
        }
        Deck deck = null;
        try {
            deck = DatabaseController.getDeckByName(deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (request.getDeckType().equals("main")) {
            if (deck.removeCardFromMainDeck(card)) {
                DatabaseController.updateDeck(deck);
            } else {
                exception = new CardNotInDeck(cardName, "main");
                return;
            }
        } else {
            if (deck.removeCardFromSideDeck(card)) {
                DatabaseController.updateDeck(deck);
            } else {
                exception = new CardNotInDeck(cardName, "main");
                return;
            }
        }
    }

    @Override
    public void handleResponse() {
        DeckView deckView = (DeckView) ViewSwitcher.getCurrentView();
        RemoveCardFromDeckRequest request = (RemoveCardFromDeckRequest) super.request;
        deckView.removeCardResponse(this, request.getDeckType(), Card.getCardByName(request.getCardName()));
    }
}
