package model.commands;

import model.Board;
import model.State;
import model.card.Card;

public class MagicCylinder extends Command implements Activate{
    Board board;
    Card target;

    public MagicCylinder(Card card) {
        super(card);
    }

    public void run() throws Exception {
        board = gameController.getGameBoard();
        target = gameController.getAttackController().getAttacker();
        gameController.setState(State.NONE);
        gameController.decreasePlayerLP(gameController.getAttackController().getAttackerNumber(),
                board.getZoneSlotByCard(target).getAttack());
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }


    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        return gameController.getState() == State.ATTACK &&
                board.getOwnerOfCard(gameController.getAttackController().getAttacker()) !=
                        board.getOwnerOfCard(myCard);
    }


}
