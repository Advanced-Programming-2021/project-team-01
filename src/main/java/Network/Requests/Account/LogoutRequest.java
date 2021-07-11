package Network.Requests.Account;

import Network.Requests.Request;
import model.Player;

public class LogoutRequest extends Request {
    private String token;

    public LogoutRequest(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
