package Network.Responses.Account;

import Network.Requests.Account.BuyRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.CardNameNotExists;
import controller.exceptions.NotEnoughMoney;
import model.AdminCard;
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
        AdminCard.adminCards.clear();
        AdminCard.loadAdminCards();
        BuyRequest buyRequest = (BuyRequest) super.request;
        card = Card.getCardByName(buyRequest.getCardName());
        if (card == null) {
            exception = new CardNameNotExists(buyRequest.getCardName());
            return;
        }
        if (card.getPrice() > Server.getLoggedInUsers().get(request.getAuthToken()).getMoney()) {
            exception = new NotEnoughMoney();
            return;
        }
        if (!AdminCard.getCardByName(buyRequest.getCardName()).isAllowed()) {
            exception = new Exception("Not allowed");
            return;
        }
        if (AdminCard.getCardByName(buyRequest.getCardName()).getAmount() < 1) {
            exception = new Exception("Not available");
            return;
        }
        Server.getLoggedInUsers().get(buyRequest.getAuthToken()).decreaseMoney(card.getPrice());
        Server.getLoggedInUsers().get(buyRequest.getAuthToken()).addCardToPlayerCards(card.getName());
        AdminCard.decreaseCardAmount(buyRequest.getCardName(), 1);
    }

    @Override
    public void handleResponse() {
        BuyRequest buyRequest = (BuyRequest) super.request;
        ShopView shopView = (ShopView) ViewSwitcher.getCurrentView();
        shopView.buyResponse(this, buyRequest.getCardName());
    }
}
