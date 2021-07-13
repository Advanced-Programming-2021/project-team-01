package Network.Responses.Account;

import Network.Requests.Account.CreateDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.DeckExists;
import model.Deck;
import view.DeckPreView;
import view.ViewSwitcher;

import static controller.RegisterController.onlineUser;

public class CreateDeckResponse extends Response {
    String deckName;

    public CreateDeckResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        CreateDeckRequest request = (CreateDeckRequest) super.request;
        deckName = request.getDeckName();
        if (DatabaseController.doesDeckExists(deckName)) {
            exception = new DeckExists(deckName);
            return;
        }
        Deck deck = new Deck(deckName);
        DatabaseController.updateDeck(deck);
        Server.getLoggedInUsers().get(getRequest().getAuthToken()).addDeck(deckName);
        DatabaseController.updatePlayer(Server.getLoggedInUsers().get(getRequest().getAuthToken()));
    }

    @Override
    public void handleResponse() {
        DeckPreView deckPreView = (DeckPreView) ViewSwitcher.getCurrentView();
        deckPreView.createDeckResponse(this);
    }
}
