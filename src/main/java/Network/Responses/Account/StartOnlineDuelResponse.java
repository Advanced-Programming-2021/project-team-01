package Network.Responses.Account;

import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Responses.Response;
import Network.Server.Server;
import javafx.scene.control.Alert;
import view.GamePreview;
import view.MyAlert;
import view.ViewSwitcher;
import view.transions.YesNoDialog;

public class StartOnlineDuelResponse extends Response {

    boolean isOnline;
    private int rounds;
    private String username;
    private String challenger;
    public StartOnlineDuelResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        username = ((StartOnlineDuelRequest) request).getOpponentUsername();
        challenger = Server.getLoggedInUsers().get(request.getAuthToken()).getUsername();
        if (!Server.getLoggedInUsers().containsValue(username)){
            isOnline = false;
            exception = new Exception("User is not online :(");
            return;
        }
        isOnline = true;
        rounds = ((StartOnlineDuelRequest)request).getNoRounds();
    }

    @Override
    public void handleResponse() {
        String prompt = challenger + " has challenged you to a duel!";
        YesNoDialog yesNoDialog = new YesNoDialog(prompt);
        yesNoDialog.showAndWait();
        if (yesNoDialog.getResult()){
            //TODO: create a game via NewGameRequest
        }else {
            //TODO: notify challenger that no game will be played
        }
    }
}
