package model.commands;

import controller.GameController;

public abstract class Command implements Activate{ //Constructor:
    GameController gameController = GameController.getInstance();

}
