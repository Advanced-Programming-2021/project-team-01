package Network.Responses.Battle;

import Network.Requests.Battle.ActivateChainRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.GameController;
import javafx.scene.control.Alert;
import view.MyAlert;

public class ActivateChainResponse extends Response {
    public ActivateChainResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        ActivateChainRequest request1 = (ActivateChainRequest) request;
        if (request1.getBoolean()) {
            try {
                GameController.getInstance().getChainController().start();
            } catch (Exception e) {
                new MyAlert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
}
