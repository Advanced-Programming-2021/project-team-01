package controller;

import Network.Client.Client;
import Network.Requests.Account.ChangeNicknameRequest;
import controller.exceptions.InvalidPassword;
import controller.exceptions.NicknameExists;
import controller.exceptions.SameOldNewPassword;

import static controller.RegisterController.onlineUser;

public class ProfileController {
    private static ProfileController instance = null;

    public static ProfileController getInstance() {
        if (instance == null) {
            instance = new ProfileController();
        }
        return instance;
    }

    public void changeNickname(String nickname) throws Exception {
        if (DatabaseController.doesNicknameExist(nickname))
            throw new NicknameExists(nickname);
    }

    public void changePassword(String currentPassword, String newPassword) throws Exception {
        if (!onlineUser.getPassword().equals(currentPassword))
            throw new InvalidPassword();
        if (currentPassword.equals(newPassword))
            throw new SameOldNewPassword();
        onlineUser.setPassword(newPassword);
    }
}
