package Network.Responses.Account;

import Network.Requests.Account.DeleteDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.DeckNotExists;
import view.DeckPreView;
import view.ViewSwitcher;

import static controller.RegisterController.onlineUser;

public class DeleteDeckResponse extends Response {
    String deckName;

    public DeleteDeckResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        DeleteDeckRequest deleteDeckRequest = (DeleteDeckRequest) super.request;
        deckName = deleteDeckRequest.getDeckName();
        if (!DatabaseController.doesDeckExists(deckName)) {
            exception = new DeckNotExists(deckName);
            return;
        }
        DatabaseController.deleteDeckFile(deckName);
        Server.getLoggedInUsers().get(deleteDeckRequest.getAuthToken()).deleteDeck(deckName);
        DatabaseController.updatePlayer(Server.getLoggedInUsers().get(deleteDeckRequest.getAuthToken()));
    }

    @Override
    public void handleResponse() {
        DeckPreView deckPreView = (DeckPreView) ViewSwitcher.getCurrentView();
        deckPreView.deleteDeckResponse(this);
    }
}
