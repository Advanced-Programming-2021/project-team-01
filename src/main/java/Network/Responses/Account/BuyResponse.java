package Network.Responses.Account;

import Network.Requests.Account.BuyRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.exceptions.CardNameNotExists;
import controller.exceptions.NotEnoughMoney;
import model.card.Card;
import view.ShopView;
import view.ViewSwitcher;

import static controller.RegisterController.onlineUser;

public class BuyResponse extends Response {
    Card card;

    public BuyResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        BuyRequest buyRequest = (BuyRequest) super.request;
        System.out.println(buyRequest.getCardName());
        System.out.println(Card.getAllCards().size());
        card = Card.getCardByName(buyRequest.getCardName());
        System.out.println(buyRequest.getCardName());
        if (card == null) {
            exception = new CardNameNotExists(buyRequest.getCardName());
            return;
        }
        if (card.getPrice() > Server.getLoggedInUsers().get(request.getAuthToken()).getMoney()) {
            exception = new NotEnoughMoney();
            return;
        }
        Server.getLoggedInUsers().get(buyRequest.getAuthToken()).addCardToPlayerCards(card.getName());
        Server.getLoggedInUsers().get(buyRequest.getAuthToken()).decreaseMoney(card.getPrice());
    }

    @Override
    public void handleResponse() {
        BuyRequest buyRequest = (BuyRequest) super.request;
        ShopView shopView = (ShopView) ViewSwitcher.getCurrentView();
        shopView.buyResponse(this, buyRequest.getCardName());
    }
}
