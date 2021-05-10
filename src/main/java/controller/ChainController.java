package controller;

import model.Chain;
import model.Player;
import model.card.Card;
import model.card.Property;
import model.card.SpellCard;
import model.card.TrapCard;
import view.ChainView;
import view.Menu;
import view.menu.GameView;
import view.menu.HandleRequestType;

public class ChainController {
    GameController gameController = GameController.getInstance();
    Chain chain;
    Player currentPlayer;
    Player opponentPlayer;
    public ChainController(Player currentPlayer, Player opponentPlayer,Chain chain) {
        HandleRequestType.currentMenu = Menu.CHAIN;
        this.currentPlayer = opponentPlayer;
        this.opponentPlayer = currentPlayer;
        this.chain = chain;
        run();
    }

    private void run(){
        ChainView.printTurn(currentPlayer);
        if (!GameView.getValidResponse()){
            ChainView.printTurn(opponentPlayer);
            HandleRequestType.currentMenu = Menu.GAME_MENU;
        }
    }


    public void activeEffect() throws Exception {
        Card card = gameController.getSelectedCard().getCard();
        if (card instanceof TrapCard){
            Property property = ((TrapCard) card).getProperty();
            if (property == Property.QUICK_PLAY || property == Property.COUNTER) {
                if (chain.doesExistInChain(card))
                    throw new Exception("Card activated before");
                else chain.setNext(card);
            }
        } else if (card instanceof SpellCard){
            Property property = ((SpellCard) card).getProperty();
            if (property == Property.QUICK_PLAY || property == Property.COUNTER){
                if (chain.doesExistInChain(card))
                    throw new Exception("Card activated before");
                else chain.setNext(card);
            }
        }
        throw new Exception("it’s not your turn to play this kind of moves");
    }

    public void back() {
        ChainView.printTurn(currentPlayer);
        HandleRequestType.currentMenu = Menu.GAME_MENU;
    }
}
