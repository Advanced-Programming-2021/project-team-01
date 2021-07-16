package Network.Responses.Account;

import Network.Requests.Account.DeleteMessageRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;

public class DeleteMessageResponse extends Response {
    public DeleteMessageResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        DeleteMessageRequest request = (DeleteMessageRequest) super.request;
        Server.getMessages().removeIf(e -> e.getID() == request.getID());
        for (String username : Server.getChatRoomOnlineUsernames()) {
            Server.getClientHandlers().get(username).updateChatRoom(username);
        }
    }

    @Override
    public void handleResponse() { }
}
