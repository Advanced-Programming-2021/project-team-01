package Network.Responses.Account;

import Network.Requests.Account.SendMessageRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import view.ChatView;
import view.ViewSwitcher;

public class SendMessageResponse extends Response {
    private int numberOfOnlineUsers;

    public SendMessageResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        SendMessageRequest request = (SendMessageRequest) super.request;
        request.getMessage().setProfileNum(Server.getLoggedInUsers().get(request.getAuthToken()).getProfile());
        Server.getMessages().add(request.getMessage());
        numberOfOnlineUsers = Server.getLoggedInUsers().size();
        for (String username : Server.getChatRoomOnlineUsernames()) {
            Server.getClientHandlers().get(username).updateChatRoom(username);
        }
    }

    @Override
    public void handleResponse() {
        ChatView chatView = (ChatView) ViewSwitcher.getCurrentView();
        chatView.setNumber(numberOfOnlineUsers);
    }
}
