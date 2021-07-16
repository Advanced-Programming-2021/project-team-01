package Network.Requests.Account;

import Network.Requests.Request;

public class DeleteMessageRequest extends Request {
    int ID;

    public DeleteMessageRequest(int ID, String authToken) {
        this.ID = ID;
        this.authToken = authToken;
    }

    public int getID() {
        return ID;
    }
}
