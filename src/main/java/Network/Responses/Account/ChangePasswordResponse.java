package Network.Responses.Account;

import Network.Requests.Account.ChangePasswordRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.InvalidPassword;
import controller.exceptions.SameOldNewPassword;
import controller.exceptions.TokenFailException;
import model.Player;
import view.ProfileView;
import view.ViewSwitcher;

import static controller.RegisterController.onlineUser;

public class ChangePasswordResponse extends Response {
    public ChangePasswordResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        ChangePasswordRequest request = (ChangePasswordRequest) this.request;
        String currentPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        if (!Server.getLoggedInUsers().containsKey(request.getAuthToken())){
            exception = new TokenFailException();
            return;
        }
        if (!onlineUser.getPassword().equals(currentPassword)) {
            exception = new InvalidPassword();
            return;
        }
        if (currentPassword.equals(newPassword)){
            exception = new SameOldNewPassword();
            return;
        }
        Player player = Server.getLoggedInUsers().get(request.getAuthToken());
        player.setPassword(newPassword);
        DatabaseController.updatePlayer(player);
    }

    @Override
    public void handleResponse() {
        ProfileView profileView = (ProfileView) ViewSwitcher.getCurrentView();
        profileView.changePasswordResponse(this);
    }
}
