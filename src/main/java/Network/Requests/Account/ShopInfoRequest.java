package Network.Requests.Account;

import Network.Requests.Request;

public class ShopInfoRequest extends Request {
    public ShopInfoRequest(String authToken) {
        this.authToken = authToken;
    }
}
