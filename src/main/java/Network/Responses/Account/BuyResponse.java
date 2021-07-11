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
    public BuyResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        BuyRequest buyRequest = (BuyRequest) super.request;
        Card card = Card.getCardByName(buyRequest.getCardName());
        if (card == null)
            exception = new CardNameNotExists(buyRequest.getCardName());
            //throw new CardNameNotExists(name);
        if (card.getPrice() > onlineUser.getMoney())
            exception = new NotEnoughMoney();
            //throw new NotEnoughMoney();
        Server.getLoggedInUsers().get(buyRequest.getAuthToken()).addCardToPlayerCards(card.getName());
    }

    @Override
    public void handleResponse() {
        ShopView shopView = (ShopView) ViewSwitcher.getCurrentView();
        shopView.buyResponse(this);
    }
}
