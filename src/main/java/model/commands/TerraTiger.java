package model.commands;

import controller.GameController;
import model.State;
import model.card.Card;
import model.card.MonsterCard;
import console.menu.GameView;

import java.util.ArrayList;

public class TerraTiger extends Command implements Activate{

    public TerraTiger(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        while (true) {
            int input = GameView.getValidNumber(1, GameController.getInstance().getGameBoard().getCurrentPlayerHand().size());
            ArrayList<Card> hand =  GameController.getInstance().getGameBoard().getCurrentPlayerHand();
            if (!(hand.get(input - 1) instanceof MonsterCard)){
                continue;
            }
            if (((MonsterCard) hand.get(input - 1)).getLevel() > 4){
                GameView.showConsole("Level error");
                continue;
            }
            GameController.getInstance().setState(State.SPECIAL_SUMMON);
            GameController.getInstance().getGameBoard().summonCard((MonsterCard) hand.get(input - 1), gameController.getCurrentPlayerNumber());
            GameController.getInstance().setState(State.NONE);
            break;
        }
    }

    @Override
    public boolean canActivate(){
        return true;
    }
}
