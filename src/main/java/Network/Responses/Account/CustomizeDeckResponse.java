package Network.Responses.Account;

import Network.Requests.Account.CustomizeDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.DatabaseController;
import model.Deck;
import view.DeckPreView;
import view.DeckView;
import view.ViewSwitcher;

import java.io.IOException;

public class CustomizeDeckResponse extends Response {
    String deckName;
    Deck deck;

    public CustomizeDeckResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        CustomizeDeckRequest customizeDeckRequest = (CustomizeDeckRequest) super.request;
        try {
            deck = DatabaseController.getDeckByName(customizeDeckRequest.getDeckName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResponse() {
        DeckPreView deckPreView = (DeckPreView) ViewSwitcher.getCurrentView();
        deckPreView.customizeDeckResponse(this, deck);
    }
}
