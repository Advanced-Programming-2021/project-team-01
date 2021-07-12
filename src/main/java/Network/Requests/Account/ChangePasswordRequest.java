package Network.Requests.Account;

import Network.Requests.Request;

public class ChangePasswordRequest extends Request {
    private String newPassword;
    private String oldPassword;

    public ChangePasswordRequest(String authToken, String newPassword, String oldPassword) {
        this.authToken = authToken;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
