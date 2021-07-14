package Network.Responses;

import Network.Client.Client;
import Network.Requests.RejectOnlineGameRequest;
import Network.Requests.Request;
import Network.Requests.StartBattleSuccessfullyRequest;
import Network.Requests.StartOnlineDuelRequest;
import Network.Server.Server;
import controller.DatabaseController;
import controller.GameController;
import javafx.scene.control.Alert;
import model.Player;
import view.MyAlert;
import view.View;
import view.ViewSwitcher;
import view.transions.YesNoDialog;

import java.util.HashMap;

public class StartOnlineDuelResponse extends Response {

    boolean isOnline;
    private int rounds;
    private String opponent;
    private String challenger;
    private boolean isReversed;

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
        isReversed = ((StartOnlineDuelRequest) request).isReversed();
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
                try {
                    GameController.getInstance().startGame(challenger, opponent, rounds, !isReversed);
                } catch (Exception exception) {
                    new MyAlert(Alert.AlertType.ERROR, exception.getMessage()).show(); //fixme: buggy if user dont have active deck or has invalid deck!
                }
                ViewSwitcher.switchTo(View.GAME_VIEW);
                Request request = new StartBattleSuccessfullyRequest(opponent, challenger, rounds);
                Client.getInstance().sendData(request.toString());
            } else {
                Request request = new RejectOnlineGameRequest(opponent);
                Client.getInstance().sendData(request.toString());
            }
        } else { //will go to player Client
            String prompt = exception.getMessage();
            new MyAlert(Alert.AlertType.WARNING, prompt).show();
        }
    }
}
