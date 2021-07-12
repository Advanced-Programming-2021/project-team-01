package Network.Responses.Account;

import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import model.Player;
import view.ShopView;
import view.ViewSwitcher;

import java.util.ArrayList;

public class ShopInfoResponse extends Response {
    Player player;

    public ShopInfoResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        player = Server.getLoggedInUsers().get(request.getAuthToken());
    }

    @Override
    public void handleResponse() {
        ShopView shopView = (ShopView) ViewSwitcher.getCurrentView();
        shopView.player = player;
        shopView.infoResponse(this);
    }
}
