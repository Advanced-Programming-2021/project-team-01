package Network.Responses.Account;

import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Message;
import Network.Server.Server;
import view.ChatView;
import view.ViewSwitcher;

import java.util.ArrayList;

public class EnterChatRoomResponse extends Response {
    ArrayList<Message> messages;
    String username;

    public EnterChatRoomResponse(Request request) {
        super(request);
        messages = Server.getMessages();
    }

    @Override
    public void handleRequest() {
        Server.getChatRoomOnlineUsernames().add(Server.getLoggedInUsers().get(request.getAuthToken()).getUsername());
        username = Server.getLoggedInUsers().get(request.getAuthToken()).getUsername();
    }

    @Override
    public void handleResponse() {
        ChatView chatView = (ChatView) ViewSwitcher.getCurrentView();
        chatView.username = username;
        chatView.loadChatMessages(messages);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
