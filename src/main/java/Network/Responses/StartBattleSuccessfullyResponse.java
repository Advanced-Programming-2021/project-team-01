package Network.Responses;

import Network.Requests.Request;
import Network.Requests.StartBattleSuccessfullyRequest;
import controller.GameController;
import model.OnlineGame;
import view.View;
import view.ViewSwitcher;

public class StartBattleSuccessfullyResponse extends Response {
    public StartBattleSuccessfullyResponse(Request request) {
        super(request);
    }
    private OnlineGame game;

    @Override
    public void handleRequest() {
        this.game = ((StartBattleSuccessfullyRequest) request ).getGame();
    }

    @Override
    public void handleResponse() {
        StartBattleSuccessfullyRequest request = (StartBattleSuccessfullyRequest) getRequest();
        String opponent = request.getOpponent();
        String player = request.getChallenger();
        int numberOfRounds = request.getRounds();
        boolean isReversed = GameController.getInstance().isReversed;
        try {
            GameController.getInstance().startGame(game);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ViewSwitcher.switchTo(View.GAME_VIEW);
    }
}
