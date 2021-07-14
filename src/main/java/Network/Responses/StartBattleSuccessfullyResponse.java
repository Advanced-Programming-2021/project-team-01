package Network.Responses;

import Network.Requests.Request;
import Network.Requests.StartBattleSuccessfullyRequest;
import controller.GameController;
import view.View;
import view.ViewSwitcher;

public class StartBattleSuccessfullyResponse extends Response {
    public StartBattleSuccessfullyResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        StartBattleSuccessfullyRequest request = (StartBattleSuccessfullyRequest) getRequest();
        String opponent = request.getOpponent();
        String player = request.getChallenger();
        int numberOfRounds = request.getRounds();
        boolean isReversed = GameController.getInstance().isReversed;
        try {
            GameController.getInstance().startGame(player, opponent, numberOfRounds, isReversed);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ViewSwitcher.switchTo(View.GAME_VIEW);
    }
}
