package Network.Responses.Account;

import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import model.Player;
import view.ScoreboardView;
import view.ViewSwitcher;

import java.util.ArrayList;

public class ScoreboardInfoResponse extends Response {
    Player player;
    ArrayList<Player> players;

    public ScoreboardInfoResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        player = Server.getLoggedInUsers().get(request.getAuthToken());
        players = getSortedScoreBoard();
    }

    @Override
    public void handleResponse() {
        ScoreboardView scoreboardView = (ScoreboardView) ViewSwitcher.getCurrentView();
        scoreboardView.player = player;
        scoreboardView.players = players;
        scoreboardView.setup();
    }

    public ArrayList<Player> getSortedScoreBoard() {
        ArrayList<Player> players = DatabaseController.getAllPlayers();
        players.sort(new Player.SortByScore());
        return players;
    }
}
