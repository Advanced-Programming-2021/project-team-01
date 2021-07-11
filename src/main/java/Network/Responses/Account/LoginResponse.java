package Network.Responses.Account;

import Network.Requests.Account.LoginRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.UsernameNotExists;
import controller.exceptions.WrongUsernamePassword;
import model.Player;
import view.LoginView;
import view.ViewSwitcher;

import java.util.UUID;

public class LoginResponse extends Response {

    private String token;
    public LoginResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        LoginRequest request = (LoginRequest) super.request;
        String username = request.getUsername();
        String password = request.getPassword();
        if (!DatabaseController.doesUserExists(username)) {
            //throw new UsernameNotExists();
            exception = new UsernameNotExists();
        }
        Player player = DatabaseController.getUserByName(username);
        if (!player.getPassword().equals(password)) {
            //throw new WrongUsernamePassword();
            exception = new WrongUsernamePassword();
        }
        token = UUID.randomUUID().toString();
        Server.getLoggedInUsers().put(token,player);
    }

    @Override
    public void handleResponse() {
        LoginView loginView = (LoginView) ViewSwitcher.getCurrentView();
        loginView.loginResponse(this);
    }
}
