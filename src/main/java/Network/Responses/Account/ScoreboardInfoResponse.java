package Network.Responses.Account;

import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import model.Player;
import view.ScoreboardView;
import view.ViewSwitcher;

import java.util.ArrayList;
import java.util.Map;

public class ScoreboardInfoResponse extends Response {
    Player player;
    ArrayList<Player> players;
    ArrayList<Boolean> isPlayerOnline = new ArrayList<>();

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
        scoreboardView.isPlayerOnline = isPlayerOnline;
        scoreboardView.setup();
    }

    public ArrayList<Player> getSortedScoreBoard() {
        ArrayList<Player> players = DatabaseController.getAllPlayers();
        players.sort(new Player.SortByScore());
        for (int i = 0; i < players.size(); i++) {
            isPlayerOnline.add(false);
        }
        for (int i = 0; i < players.size(); i++) {
            for (Map.Entry<String, Player> entry : Server.getLoggedInUsers().entrySet()) {
                if (entry.getValue().getUsername().equals(players.get(i).getUsername())) {
                    isPlayerOnline.set(i, true);
                }
            }
        }
        return players;
    }
}
