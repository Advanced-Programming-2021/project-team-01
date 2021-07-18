package Network.Responses.Battle;

import Network.Requests.Battle.UpdateActionRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import view.GameView;

public class UpdateActionResponse extends Response {
    public UpdateActionResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        GameView.getInstance().lastBattleAction = ((UpdateActionRequest) request).getBattleAction();
    }
}
