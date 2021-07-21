package Network.Responses;

import Network.Requests.Request;
import Network.Requests.StartBattleSuccessfullyRequest;
import controller.GameController;
import model.OnlineGame;
import view.GamePreview;
import view.View;
import view.ViewSwitcher;

public class StartBattleSuccessfullyResponse extends Response {
    private OnlineGame game;

    public StartBattleSuccessfullyResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        this.game = ((StartBattleSuccessfullyRequest) request).getGame();
    }

    @Override
    public void handleResponse() {
        StartBattleSuccessfullyRequest request = (StartBattleSuccessfullyRequest) getRequest();
        try {
            GameController.getInstance().setControllerNumber(1);
            GameController.getInstance().startGame(game);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (((GamePreview) ViewSwitcher.getCurrentView()).superWaitingDialog != null &&
                ((GamePreview)ViewSwitcher.getCurrentView()).superWaitingDialog.isShowing()) {
            ((GamePreview) ViewSwitcher.getCurrentView()).superWaitingDialog.setResult(true);
            ((GamePreview) ViewSwitcher.getCurrentView()).superWaitingDialog.close();
        }
        ViewSwitcher.switchTo(View.GAME_VIEW);
    }
}
