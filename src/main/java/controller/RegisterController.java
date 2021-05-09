package controller;

import controller.exceptions.NicknameExists;
import controller.exceptions.UsernameExists;
import controller.exceptions.UsernameNotExists;
import controller.exceptions.WrongUsernamePassword;
import model.Player;
import view.Menu;
import view.menu.HandleRequestType;

public class RegisterController {
    public static Player onlineUser; //TODO Important: UPDATE DATABASE WHEN :-> AFTER LOGOUT, NEW_GAME
    private static RegisterController instance = null;

    public static RegisterController getInstance() {
        if (instance == null) {
            instance = new RegisterController();
        }
        return instance;
    }

    public void createUser(String username, String password, String nickname) throws Exception {
        if (DatabaseController.doesUserExists(username)) {
            throw new UsernameExists(username);
        }
        if (DatabaseController.doesNicknameExist(nickname)) {
            throw new NicknameExists(nickname);
        }
        DatabaseController.updatePlayer(new Player(username, password, nickname));
    }

    public void loginUser(String username, String password) throws Exception {
        if (!DatabaseController.doesUserExists(username)) {
            throw new UsernameNotExists();
        }
        Player player = DatabaseController.getUserByName(username);
        if (!player.getPassword().equals(password)) {
            throw new WrongUsernamePassword();
        }
        onlineUser = player;
        HandleRequestType.currentMenu = Menu.MAIN_MENU;
    }

    public void logout() {
        DatabaseController.updatePlayer(onlineUser);
        onlineUser = null;
        HandleRequestType.currentMenu = Menu.REGISTER_MENU;
    }
}
