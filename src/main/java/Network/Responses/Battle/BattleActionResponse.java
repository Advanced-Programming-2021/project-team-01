package Network.Responses.Battle;

import Network.Requests.Battle.BattleActionRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.GameController;
import model.networkLocators.BattleAction;
import view.GameView;
import view.ViewSwitcher;

public class BattleActionResponse extends Response {
    public BattleActionResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        GameView gameView = (GameView) ViewSwitcher.getCurrentView();
        BattleAction battleAction = ((BattleActionRequest) request).getBattleAction();
        try {
            gameView.doAction(battleAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
