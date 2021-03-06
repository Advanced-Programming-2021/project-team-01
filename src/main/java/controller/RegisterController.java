package controller;

import controller.exceptions.NicknameExists;
import controller.exceptions.UsernameExists;
import controller.exceptions.UsernameNotExists;
import controller.exceptions.WrongUsernamePassword;
import model.Player;
import console.Menu;
import console.menu.HandleRequestType;

import java.util.Random;

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
        Player player = new Player(username, password, nickname);
        player.setPictureNumber(getRandomPicture());
        DatabaseController.updatePlayer(player);
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

    private int getRandomPicture(){
        Random random = new Random();
        int number = random.nextInt(32) + 1;
        return number;
    }
}
