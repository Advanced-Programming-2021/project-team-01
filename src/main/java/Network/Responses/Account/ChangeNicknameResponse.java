package Network.Responses.Account;

import Network.Requests.Account.ChangeNicknameRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.NicknameExists;
import controller.exceptions.TokenFailException;
import model.Player;
import view.ProfileView;
import view.ViewSwitcher;

public class ChangeNicknameResponse extends Response {
    public ChangeNicknameResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        if (!Server.getLoggedInUsers().containsKey(request.getAuthToken())){
            exception = new TokenFailException();
            return;
        }
        String nickname = ((ChangeNicknameRequest) request).getNewNickname();
        if (DatabaseController.doesNicknameExist(nickname)) {
            exception = new NicknameExists(nickname);
            return;
        }
        Player player = Server.getLoggedInUsers().get(request.getAuthToken());
        player.setNickname(nickname);
        DatabaseController.updatePlayer(player);
    }

    @Override
    public void handleResponse() {
        ProfileView profileView = (ProfileView) ViewSwitcher.getCurrentView();
        profileView.changeNicknameResponse(this);
    }
}
