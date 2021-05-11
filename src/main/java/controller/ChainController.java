package controller;

import model.Chain;
import model.card.Card;
import model.card.Property;
import model.card.SpellCard;
import model.card.TrapCard;
import view.ChainView;
import view.menu.GameView;

public class ChainController {
    GameController gameController = GameController.getInstance();
    Chain chain;

    public ChainController(Chain chain) {
        this.chain = chain;
    }

    protected void run() throws Exception {
        //fixme: hand Checker
        if (gameController.getGameBoard().getCounterTraps(gameController.getOpponentPlayerNumber()) == null) {
            return;
        }
        gameController.changeTurn();
        ChainView.printTurn(GameController.getCurrentPlayer());
        GameController.getInstance().getGameBoard().showBoard();
        GameView.showConsole("Do you want to activate your trap and spell?");
        if (!GameView.getValidResponse()) {
            back();
            return;
        }
        ChainView chainView = new ChainView();
        chainView.start();
    }


    public void activeEffect() throws Exception {
        Card card = gameController.getSelectedCard().getCard();
        if (card instanceof TrapCard) {
            if (chain.doesExistInChain(card))
                throw new Exception("Card activated before");
            else if (card.canActivate()) chain.setNext(card);
            else throw new Exception("you cant activate this card");

        } else if (card instanceof SpellCard) {
            Property property = ((SpellCard) card).getProperty();
            if (property == Property.QUICK_PLAY || property == Property.COUNTER) {
                if (chain.doesExistInChain(card))
                    throw new Exception("Card activated before");
                else if (card.canActivate()) chain.setNext(card);
                else throw new Exception("you cant activate this card");
            }
        } else {
            throw new Exception("it’s not your turn to play this kind of moves");
        }
    }

    public void back() {
        gameController.changeTurn();
        ChainView.printTurn(GameController.getCurrentPlayer());
        GameController.getInstance().getGameBoard().showBoard();
    }
}
