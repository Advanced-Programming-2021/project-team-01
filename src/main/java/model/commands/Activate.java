package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;

public interface Activate {
    void run() throws InvalidCommandException, MonsterZoneFull;
}
