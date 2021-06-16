package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import model.card.SpellCard;
import view.menu.GameView;

public class MagicJammer extends Command implements Activate {
    Board board;

    public MagicJammer(Card card) {
        super(card);
    }

    public void run() throws Exception {
        board = gameController.getGameBoard();
        Card target = gameController.getChainController().getChain().getPrev();
        gameController.getChainController().getChain().getChainElements().remove(target);
        GameView.showConsole(target.getName() + " removed from chain! :)");
        board.sendCardFromSpellZoneToGraveyard(myCard);
        board.sendCardFromSpellZoneToGraveyard(target);
    }

    @Override
    public void runContinuous() throws Exception {

    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        myCard = gameController.getSelectedCard().getCard();
        return gameController.getChainController() != null &&
                gameController.getChainController().getChain().getChainElements().size() > 0;
    }
}
