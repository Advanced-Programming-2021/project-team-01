package Network.Responses.Battle;

import Network.Requests.Request;
import Network.Responses.Response;
import view.GameView;

public class GetNeededCardResponse extends Response {
    public GetNeededCardResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        GameView.getInstance().setCurrentResponse(this);
    }
}
