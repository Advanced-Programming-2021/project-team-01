package endpoint;

import controller.DatabaseController;
import controller.GameController;
import console.menu.HandleRequestType;

public class MainConsole {
    public static void main(String[] args) throws Exception {
        DatabaseController.loadGameCards();
        GameController.getInstance();
        HandleRequestType handleRequestType = new HandleRequestType();
        handleRequestType.start();
    }
}
