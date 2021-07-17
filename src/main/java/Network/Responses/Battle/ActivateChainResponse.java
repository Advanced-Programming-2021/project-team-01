package Network.Responses.Battle;

import Network.Requests.Request;
import Network.Responses.Response;
import controller.GameController;
import javafx.scene.control.Alert;
import view.MyAlert;
import view.transions.YesNoDialog;

public class ActivateChainResponse extends Response{
    public ActivateChainResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        YesNoDialog yesNoDialog = new YesNoDialog(GameController.getCurrentPlayer().getNickname() + ",Do you want to activate your trap and spell?");
        yesNoDialog.showAndWait();
        if (yesNoDialog.getResult()){
            try {
                GameController.getInstance().getChainController().start();
            } catch (Exception e) {
                new MyAlert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
}
