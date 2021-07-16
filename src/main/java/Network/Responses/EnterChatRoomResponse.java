package Network.Responses;

import Network.Requests.Request;
import Network.Server.Message;
import Network.Server.Server;
import view.ChatView;
import view.ViewSwitcher;

import java.util.ArrayList;

public class EnterChatRoomResponse extends Response {
    ArrayList<Message> messages;

    public EnterChatRoomResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        messages = Server.getMessages();
        Server.getChatRoomOnlineUsernames().add(Server.getLoggedInUsers().get(request.getAuthToken()).getUsername());
    }

    @Override
    public void handleResponse() {
        ChatView chatView = (ChatView) ViewSwitcher.getCurrentView();
        chatView.loadChatMessages(messages);
    }
}
