package Network.Responses;

import Network.Requests.Account.DeckInfoRequest;
import Network.Requests.Request;
import Network.Server.Server;
import model.Player;
import view.DeckPreView;
import view.DeckView;
import view.ViewSwitcher;

public class DeckInfoResponse extends Response {
    Player player;

    public DeckInfoResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        player = Server.getLoggedInUsers().get(request.getAuthToken());
    }

    @Override
    public void handleResponse() {
        DeckPreView deckPreView = (DeckPreView) ViewSwitcher.getCurrentView();
        deckPreView.player = player;
        deckPreView.receivePlayerResponse();
    }
}
