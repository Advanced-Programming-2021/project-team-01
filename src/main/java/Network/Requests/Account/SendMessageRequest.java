package Network.Requests.Account;

import Network.Requests.Request;
import Network.Server.Message;

public class SendMessageRequest extends Request {
    Message message;

    public SendMessageRequest(Message message, String authToken) {
        this.message = message;
        this.authToken = authToken;
    }

    public Message getMessage() {
        return message;
    }
}
