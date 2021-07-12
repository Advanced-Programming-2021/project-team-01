package Network.Responses.Account;

import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.exceptions.TokenFailException;
import model.Player;
import view.ProfileView;
import view.ViewSwitcher;

public class ProfileInfoResponse extends Response {
    public ProfileInfoResponse(Request request) {
        super(request);
    }
    Player player;

    @Override
    public void handleRequest() {
        if (!Server.getLoggedInUsers().containsKey(request.getAuthToken())){
            exception = new TokenFailException();
            return;
        }
        player = Server.getLoggedInUsers().get(request.getAuthToken());

    }

    @Override
    public void handleResponse() {
        ProfileView profileView = (ProfileView) ViewSwitcher.getCurrentView();
        if (player != null)
            profileView.initializeInfo(player);
    }
}
