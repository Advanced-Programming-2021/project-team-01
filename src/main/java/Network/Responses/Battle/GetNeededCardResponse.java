package Network.Responses.Battle;

import Network.Requests.Battle.SendNeededCardsRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import controller.GameController;
import javafx.collections.FXCollections;
import model.Board;
import model.card.Card;
import model.networkLocators.BattleAction;
import view.GameView;

import java.util.List;

public class GetNeededCardResponse extends Response {
    List<Card> cards = FXCollections.observableArrayList();

    public List<Card> getCards() {
        return cards;
    }

    public GetNeededCardResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {

    }

    @Override
    public void handleResponse() {
        SendNeededCardsRequest request = (SendNeededCardsRequest) getRequest();
        Board board = GameController.getInstance().getGameBoard();
        for (BattleAction battleAction : request.getNeededCards()) {
            cards.add(board.getCardByBattleAction(battleAction));
        }
        GameView.getInstance().setCurrentResponse(this);
    }
}
