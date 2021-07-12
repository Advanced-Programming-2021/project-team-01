package Network.Requests.Account;

import Network.Requests.Request;

public class ProfileInfoRequest extends Request {

    public ProfileInfoRequest(String authToken) {
        this.authToken = authToken;
    }
}
