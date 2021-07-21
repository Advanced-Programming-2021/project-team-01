package Network.Responses.Account;

import Network.Client.Client;
import Network.Requests.Account.LoginRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.ClientHandler;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.UsernameNotExists;
import controller.exceptions.WrongUsernamePassword;
import model.Player;
import view.LoginView;
import view.ViewSwitcher;

import javax.security.auth.login.LoginException;
import java.util.UUID;

public class LoginResponse extends Response {

    private String token;
    private Player player;

    public LoginResponse(Request request) {
        super(request);
    }

    public void handleRequest(ClientHandler clientHandler) {
        LoginRequest request = (LoginRequest) super.request;
        String username = request.getUsername();
        String password = request.getPassword();
        if (!DatabaseController.doesUserExists(username)) {
            //throw new UsernameNotExists();
            exception = new UsernameNotExists();
            return;
        }
        Player player = DatabaseController.getUserByName(username);
        if (!player.getPassword().equals(password)) {
            //throw new WrongUsernamePassword();
            exception = new WrongUsernamePassword();
            return;
        }
        if (Server.getClientHandlers().containsKey(player.getUsername())) {
            exception = new Exception("user has already logged in");
            return;
        }
        if (exception == null) {
            token = UUID.randomUUID().toString();
            this.player = player;
            Server.getLoggedInUsers().put(token, player);
            Server.getClientHandlers().put(username, clientHandler);
        }
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        LoginView loginView = (LoginView) ViewSwitcher.getCurrentView();
        if (loginView.loginResponse(this)) {
            Client.getInstance().setToken(token);
        }
    }
}
