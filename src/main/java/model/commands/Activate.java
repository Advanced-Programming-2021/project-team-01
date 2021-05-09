package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.card.Card;

public interface Activate {
    void run() throws Exception;
    void runContinuous() throws Exception;
    boolean canActivate() throws Exception;
}
