package Network.Responses.Account;

import Network.Requests.Account.ActivateDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.DeckNotExists;
import view.DeckPreView;
import view.View;
import view.ViewSwitcher;

public class ActivateDeckResponse extends Response {
    String deckName;

    public ActivateDeckResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        ActivateDeckRequest activateDeckRequest = (ActivateDeckRequest) super.request;
        deckName = activateDeckRequest.getDeckName();
        if (!DatabaseController.doesDeckExists(deckName)) {
            exception = new DeckNotExists(deckName);
            return;
        }
        Server.getLoggedInUsers().get(request.getAuthToken()).setActiveDeck(deckName);
        DatabaseController.updatePlayer(Server.getLoggedInUsers().get(request.getAuthToken()));
    }

    @Override
    public void handleResponse() {
        DeckPreView deckPreView = (DeckPreView) ViewSwitcher.getCurrentView();
        deckPreView.activateDeckResponse(this, deckName);
    }
}
