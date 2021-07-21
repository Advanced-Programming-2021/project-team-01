package Network.Responses.Battle;

import Network.Requests.Battle.CancelMatchMakingRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;

public class CancelMatchMakingResponse extends Response {
    public CancelMatchMakingResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        CancelMatchMakingRequest request1 = (CancelMatchMakingRequest) request;
        Server.getOneRoundGames().remove(request1.getAuthToken());
        Server.getThreeRoundGames().remove(request1.getAuthToken());
    }

    @Override
    public void handleResponse() {

    }
}
