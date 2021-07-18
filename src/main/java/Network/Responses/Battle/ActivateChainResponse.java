package Network.Responses.Battle;

import Network.Requests.Battle.ActivateChainRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.GameController;
import view.GameView;

public class ActivateChainResponse extends Response {
    public boolean shouldActive;
    public boolean hasHandledResponse = false;

    public ActivateChainResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        ActivateChainRequest request1 = (ActivateChainRequest) request;
        shouldActive = request1.getBoolean();
        hasHandledResponse = true;
        GameView.getInstance().setCurrentResponse(this);
        GameController.getInstance().getChainController().waitingDialog.boroGomsho = Boolean.TRUE;
    }
}
