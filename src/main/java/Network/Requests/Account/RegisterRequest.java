package Network.Requests.Account;

import Network.Requests.Request;

public class RegisterRequest extends Request {
    private String userName;
    private String password;
    public RegisterRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
