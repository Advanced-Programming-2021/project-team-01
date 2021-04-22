package controller;

import model.Player;
import view.Menu;
import view.menu.HandleRequestType;

public class RegisterController {
    public Player onlineUser; //TODO Important: UPDATE DATABASE WHEN :-> AFTER LOGOUT, NEW_GAME
    private static RegisterController instance = null;

    public static RegisterController getInstance() {
        if (instance == null) {
            instance = new RegisterController();
        }
        return instance;
    }

    public void createUser(String username, String password, String nickname) throws Exception {
        //TODO: login




    }

    public void loginUser(String username, String password) {
        //TODO: login



        HandleRequestType.currentMenu = Menu.MAIN_MENU;
    }

    public void logout() {
        //TODO: code cleanup
        HandleRequestType.currentMenu = Menu.REGISTER_MENU;
    }
}
