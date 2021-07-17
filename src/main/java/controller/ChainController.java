package controller;

import console.ChainView;
import javafx.collections.FXCollections;
import model.Board;
import model.Chain;
import model.card.Card;
import model.card.Property;
import model.card.SpellCard;
import model.card.TrapCard;
import view.GameView;

import java.util.List;

public class ChainController {
    GameController gameController = GameController.getInstance();
    Chain chain;

    public ChainController(Chain chain) {
        this.chain = chain;
    }

    public Chain getChain() {
        return chain;
    }

    protected void run() throws Exception {
        gameController.changeTurn();
        if (gameController.getGameBoard().getCounterTraps(gameController.getOpponentPlayerNumber()) == null) {
            gameController.changeTurn();
            return;
        }
        ChainView.printTurn(GameController.getCurrentPlayer());
        GameController.getInstance().getGameBoard().showBoard();
        if (GameView.getYesOrNo(GameController.currentPlayer.getNickname() + ",Do you want to activate your trap and spell?")) {
            start();
        }
        back();
    }

    public void start() throws Exception {
        List<Card> cards = FXCollections.observableArrayList();
        List<Card> playerCards = FXCollections.observableArrayList();
        Board board = GameController.getInstance().getGameBoard();
        int player = GameController.getInstance().getCurrentPlayerNumber();
        playerCards.addAll(board.getPlayerHand(player));
        playerCards.addAll(board.getCardInSpellZone(player));
        for (Card card : playerCards) {
            if (card instanceof TrapCard || (card instanceof SpellCard &&
                    (((SpellCard) card).getProperty() == Property.QUICK_PLAY || ((SpellCard) card).getProperty() == Property.COUNTER)))
                cards.add(card);
        }
        List<Card> selectedCards = GameView.selectedCardsWithSelectableDialog(cards);
        for (Card selectedCard : selectedCards) {
            GameController.getInstance().getSelectedCard().unlock();
            GameController.getInstance().getSelectedCard().set(selectedCard);
            activeEffect();
        }
    }


    public void activeEffect() throws Exception {
        Card card = gameController.getSelectedCard().getCard();
        if (card instanceof TrapCard) {
            if (gameController.effectController.isMirageDragoon()) {
                throw new Exception("Mirage Dragon Stopped You!");
            }
            if (chain.doesExistInChain(card))
                throw new Exception("Card activated before");
            else if (card.canActivate()) chain.setNext(card);
            else throw new Exception("you cant activate this card");

        } else if (card instanceof SpellCard) {
            Property property = ((SpellCard) card).getProperty();
            if (property == Property.QUICK_PLAY || property == Property.COUNTER) {
                if (chain.doesExistInChain(card))
                    throw new Exception("Card activated before");
                else if (card.canActivate()) {
                    GameController.getInstance().getGameBoard().setSpell(gameController.getCurrentPlayerNumber(), (SpellCard) card);
                    GameController.getInstance().getGameBoard().setSpellFaceUp(card);
                    chain.setNext(card);
                } else throw new Exception("you cant activate this card");
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
