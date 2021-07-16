package Network.Responses.Account;

import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;

public class ExitChatRoomResponse extends Response {
    public ExitChatRoomResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        Server.getChatRoomOnlineUsernames().remove(Server.getLoggedInUsers().get(request.getAuthToken()).getUsername());
    }

    @Override
    public void handleResponse() {

    }
}
