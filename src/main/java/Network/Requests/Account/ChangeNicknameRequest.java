package Network.Requests.Account;

import Network.Requests.Request;

public class ChangeNicknameRequest extends Request {

    private String newNickname;

    public ChangeNicknameRequest(String authToken,String newNickname){
        this.authToken = authToken;
        this.newNickname = newNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }
}
