package Network.Responses.Account;

import Network.Requests.Account.SendMessageRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;

public class SendMessageResponse extends Response {
    public SendMessageResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        SendMessageRequest request = (SendMessageRequest) super.request;
        Server.getMessages().add(request.getMessage());
        for (String username : Server.getChatRoomOnlineUsernames()) {
            Server.getClientHandlers().get(username).updateChatRoom(username);
        }
    }

    @Override
    public void handleResponse() { }
}
