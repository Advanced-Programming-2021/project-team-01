package Network.Requests.Account;

import Network.Requests.Request;

public class EnterChatRoomRequest extends Request {
    public EnterChatRoomRequest(String authToken) {
        this.authToken = authToken;
    }
}
