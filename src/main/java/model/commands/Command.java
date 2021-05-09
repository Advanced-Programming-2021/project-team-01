package model.commands;

import controller.GameController;

public abstract class Command implements Activate{ //Constructor:
    GameController gameController = GameController.getInstance();

    public void run() throws Exception {

    }

    public void runContinuous() throws Exception {

    }

    public boolean canActivate() throws Exception {
        return false;
    }

}
