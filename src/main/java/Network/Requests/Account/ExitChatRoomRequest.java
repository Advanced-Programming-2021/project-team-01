package Network.Requests.Account;

import Network.Requests.Request;

public class ExitChatRoomRequest extends Request {
    public ExitChatRoomRequest(String authToken) {
        this.authToken = authToken;
    }
}
