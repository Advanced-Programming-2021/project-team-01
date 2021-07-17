package Network.Requests.Battle;

import Network.Requests.Request;
import controller.GameController;
import model.Board;
import model.card.Card;
import model.networkLocators.BattleAction;
import model.networkLocators.BattleState;

import java.util.ArrayList;
import java.util.List;

public class SendNeededCardsRequest extends Request {
    private List<BattleAction> neededCards;
    private String opponent;

    public SendNeededCardsRequest(List<Card> cards, String opponent) {
        neededCards = new ArrayList<>();
        Board board = GameController.getInstance().getGameBoard();
        for (Card card : cards) {
            BattleAction battleAction = new BattleAction(BattleState.CARD_INFO, board.getCardLocation(card),
                    board.getIndexOfCard(card), board.getOwnerOfCard(card));
            neededCards.add(battleAction);
        }
        this.opponent = opponent;
    }

    public List<BattleAction> getNeededCards() {
        return neededCards;
    }

    public String getOpponent() {
        return opponent;
    }
}
