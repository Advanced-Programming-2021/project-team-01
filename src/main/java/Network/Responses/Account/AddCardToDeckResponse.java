package Network.Responses.Account;

import Network.Requests.Account.AddCardToDeckRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import Network.Server.Server;
import controller.DatabaseController;
import controller.exceptions.*;
import model.Deck;
import model.card.Card;
import view.DeckView;
import view.ViewSwitcher;

import java.io.IOException;
import java.util.List;

import static controller.RegisterController.onlineUser;

public class AddCardToDeckResponse extends Response {
    public AddCardToDeckResponse(Request request) {
        super(request);
    }

    @Override
    public void handleRequest() {
        AddCardToDeckRequest request = (AddCardToDeckRequest) super.request;
        String cardName = request.getCardName();
        String deckName = request.getDeckName();
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            exception = new CardNameNotExists(cardName);
            return;
        }
        if (!DatabaseController.doesDeckExists(deckName)) {
            exception = new DeckNotExists(deckName);
            return;
        }
        Deck deck = null;
        try {
            deck = DatabaseController.getDeckByName(deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (request.getDeckType().equals("main")) {
            if (deck.getMainDeck().size() >= 60) {
                exception = new MainDeckIsFull();
                return;
            }
            if (!onlineUser.getPlayerCards().contains(cardName)) {
                exception = new PlayerCardNotExist();
                return;
            }
            if (!deck.checkCardsLimit(card)) {
                exception = new CardNumberLimit(cardName, deckName);
                return;
            }
            if (!doesHaveEnoughCards(cardName, deck, getPlayerNumberOfCards(cardName))) {
                exception = new Exception("You don't have enough cards");
            }
            deck.addCardToMainDeck(card);
            DatabaseController.updateDeck(deck);
        } else {
            if (deck.getSideDeck().size() < 15) {
                exception = new SideDeckIsFull();
                return;
            }
            if (onlineUser.getPlayerCards().contains(cardName)) {
                exception = new PlayerCardNotExist();
                return;
            }
            if (!deck.checkCardsLimit(card)) {
                exception = new CardNumberLimit(cardName, deckName);
                return;
            }
            deck.addCardToSideDeck(card);
            DatabaseController.updateDeck(deck);
        }
    }

    @Override
    public void handleResponse() {
        DeckView deckView = (DeckView) ViewSwitcher.getCurrentView();
        AddCardToDeckRequest request = (AddCardToDeckRequest) super.request;
        deckView.addCardResponse(this, request.getDeckType().equals("main"), Card.getCardByName(request.getCardName()));
    }

    private boolean doesHaveEnoughCards(String cardName,Deck deck, int playerNum){
        List<Card> mainDeck = deck.getMainDeck();
        int counter = 0;
        for (Card card : mainDeck) {
            if (card.getName().equals(cardName)){
                counter++;
            }
        }
        return playerNum > counter;
    }

    private int getPlayerNumberOfCards(String cardName) {
        int counter = 0;
        for (String playerCard : Server.getLoggedInUsers().get(request.getAuthToken()).getPlayerCards()) {
            if (cardName.equals(playerCard)){
                counter++;
            }
        }
        return counter;
    }
}
