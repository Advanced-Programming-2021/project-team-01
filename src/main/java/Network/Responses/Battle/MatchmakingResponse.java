package Network.Responses.Battle;

import Network.Requests.Battle.NewMatchmakingRequest;
import Network.Requests.Request;
import Network.Requests.StartOnlineDuelRequest;
import Network.Responses.Response;
import Network.Responses.StartOnlineDuelResponse;
import Network.Server.ClientHandler;
import Network.Server.Server;
import Network.Utils.Logger;

public class MatchmakingResponse extends Response {
    public MatchmakingResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        NewMatchmakingRequest trueRequest = ((NewMatchmakingRequest) request);
        int rounds = trueRequest.getNumberOfRounds();
        if (rounds == 1) {
            Server.getOneRoundGames().add(request.getAuthToken());
            if (Server.getOneRoundGames().size() >= 2) {
                StartOnlineDuelRequest startOnlineDuelRequest = new StartOnlineDuelRequest(Server.getOneRoundGames().get(1),
                        Server.getLoggedInUsers().get(Server.getOneRoundGames().get(0)).getUsername(),
                        trueRequest.getNumberOfRounds(),
                        false);
                //Math.random() % 1 == 1 ? true : false); // TODO: a simple coin toss by the server!
                StartOnlineDuelResponse response = new StartOnlineDuelResponse(startOnlineDuelRequest);
                response.handleRequest();
                String username = ((StartOnlineDuelRequest) response.getRequest()).getOpponentUsername();
                ClientHandler clientHandler = Server.getClientHandlers().get(username);
                clientHandler.out.println(ClientHandler.gson.toJson(response));
                clientHandler.out.flush();
                Server.getOneRoundGames().remove(0);
                Server.getOneRoundGames().remove(0);
                Logger.log("Sent: " + response);
            }
        } else {
            Server.getThreeRoundGames().add(request.getAuthToken());
            if (Server.getThreeRoundGames().size() == 2) {
                StartOnlineDuelRequest startOnlineDuelRequest = new StartOnlineDuelRequest(Server.getThreeRoundGames().get(0),
                        Server.getLoggedInUsers().get(Server.getThreeRoundGames().get(1)).getUsername(),
                        trueRequest.getNumberOfRounds(),
                        true); // TODO: a simple coin toss by the server!
                StartOnlineDuelResponse response = new StartOnlineDuelResponse(startOnlineDuelRequest);
                String username = ((StartOnlineDuelRequest) response.getRequest()).getOpponentUsername();
                ClientHandler clientHandler = Server.getClientHandlers().get(username);
                clientHandler.out.println(ClientHandler.gson.toJson(response));
                clientHandler.out.flush();
                Server.getThreeRoundGames().remove(0);
                Server.getThreeRoundGames().remove(0);
                Logger.log("Sent: " + response);
            }
        }
    }

    @Override
    public void handleResponse() {

    }
}
