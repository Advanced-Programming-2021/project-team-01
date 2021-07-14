package Network.Responses.Account;

import Network.Client.Client;
import Network.Requests.RejectOnlineGameRequest;
import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import javafx.scene.control.Alert;
import model.Player;
import view.MyAlert;
import view.transions.YesNoDialog;

import java.util.HashMap;

public class StartOnlineDuelResponse extends Response {

    boolean isOnline;
    private int rounds;
    private String opponent;
    private String challenger;

    public StartOnlineDuelResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        opponent = ((StartOnlineDuelRequest) request).getOpponentUsername();
        challenger = Server.getLoggedInUsers().get(request.getAuthToken()).getUsername();
        if (!DatabaseController.doesUserExists(opponent)) {
            exception = new Exception("User doesnt Exists");
            return;
        }
        HashMap<String, Player> playerHashMap = Server.getLoggedInUsers();
        playerHashMap.forEach(((token, player) -> {
            if (player.getUsername().equals(opponent)) isOnline = true;
        }));
        if (!isOnline) {
            exception = new Exception("User is not online");
            return;
        }
        rounds = ((StartOnlineDuelRequest) request).getNoRounds();
    }

    @Override
    public void handleResponse() {
        if (exception == null) { //will go to the opponent Client
            String prompt = challenger + " has challenged you to a duel!\nacept?";
            YesNoDialog yesNoDialog = new YesNoDialog(prompt);
            yesNoDialog.showAndWait();
            if (yesNoDialog.getResult()) {
                //TODO: create a game via NewGameRequest
            } else {
                Request request = new RejectOnlineGameRequest(opponent);
                Client.getInstance().sendData(request.toString());
            }
        }else { //will go to player Client
            String prompt = exception.getMessage();
            new MyAlert(Alert.AlertType.WARNING, prompt).show();
        }
    }
}
