package Network.Responses.Account;

import Network.Requests.Account.RegisterRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.DatabaseController;
import controller.exceptions.NicknameExists;
import controller.exceptions.UsernameExists;
import controller.exceptions.UsernameNotExists;
import model.Player;
import view.LoginView;
import view.ViewSwitcher;

import java.util.Random;

public class RegisterResponse extends Response {


    public RegisterResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        RegisterRequest request = (RegisterRequest) super.request;
        String username = request.getUserName();
        String password = request.getPassword();
        String nickname = request.getNickname();
        if (DatabaseController.doesUserExists(username)) {
            //throw new UsernameExists(username);
            exception = new UsernameExists(username);
            return;
        }
        if (DatabaseController.doesNicknameExist(nickname)) {
            //throw new NicknameExists(nickname);
            exception = new NicknameExists(nickname);
            return;
        }
        if (exception == null) {
            Player player = new Player(username, password, nickname);
            player.setPictureNumber(getRandomPicture());
            DatabaseController.updatePlayer(player);
        }
    }

    @Override
    public void handleResponse() {
        LoginView loginView = (LoginView) ViewSwitcher.getCurrentView();
        loginView.signUpResponse(this);
    }

    private int getRandomPicture(){
        Random random = new Random();
        int number = random.nextInt(32) + 1;
        return number;
    }
}
