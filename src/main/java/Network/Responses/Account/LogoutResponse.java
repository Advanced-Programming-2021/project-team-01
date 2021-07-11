package Network.Responses.Account;

import Network.Client.Client;
import Network.Requests.Account.LogoutRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;

public class LogoutResponse extends Response {
    public LogoutResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        LogoutRequest request = (LogoutRequest) super.request;
        DatabaseController.updatePlayer(Server.getLoggedInUsers().get(request.getToken()));
        Server.removeUser(request.getToken());
    }

    @Override
    public void handleResponse(){
        Client.getInstance().setToken(null);
    }
}
