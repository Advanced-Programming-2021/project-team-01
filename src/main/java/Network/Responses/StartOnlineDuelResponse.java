package Network.Responses;

import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Server.Server;
import javafx.scene.control.Alert;
import view.GamePreview;
import view.MyAlert;
import view.ViewSwitcher;

public class StartOnlineDuelResponse extends Response{

    boolean isOnline;
    private int rounds;
    private String username;
    public StartOnlineDuelResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        username = ((StartOnlineDuelRequest) request).getOpponentUsername();
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
        //GamePreview gamePreview = (GamePreview) ViewSwitcher.getCurrentView();
        if (!isOnline) new MyAlert(Alert.AlertType.ERROR,"user is not online :(").show();
        else new MyAlert(Alert.AlertType.CONFIRMATION,"invitation sent").show();
    }
}
