package Network.Requests.Account;

import Network.Requests.Request;

public class RegisterRequest extends Request {
    private String userName;
    private String password;
    private String nickname;
    public RegisterRequest(String userName, String password, String nickname){
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
