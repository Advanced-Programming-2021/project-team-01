package Network.Responses;

import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Server.Server;

public class StartOnlineDuelResponse extends Response{

    private int rounds;
    private String username;
    public StartOnlineDuelResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        username = ((StartOnlineDuelRequest) request).getOpponentUsername();
        if (!Server.getLoggedInUsers().containsValue(username)){
            exception = new Exception("User is not online :(");
            return;
        }
        rounds = ((StartOnlineDuelRequest)request).getNoRounds();
    }

    @Override
    public void handleResponse() {

    }
}
