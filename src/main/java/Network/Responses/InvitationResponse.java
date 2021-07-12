package Network.Responses;

import Network.Requests.InvitationRequest;
import Network.Requests.Request;
import Network.Server.Server;
import javafx.scene.layout.Pane;
import model.Player;
import view.transions.YesNoDialog;

public class InvitationResponse extends Response {
    private String challenger;
    public InvitationResponse(Request request) {
        super(request);
    }

    public boolean hasAccepted(){
        return true;
    }

    @Override
    public void handleRequest() {
        //fixme: i cant think of any needed task at the server side because of GPV
        challenger = Server.getLoggedInUsers().get(request.getAuthToken()).getUsername();
        //auth token will surely not fail because its a request from server!
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
