package controller;

import controller.exceptions.InvalidPassword;
import controller.exceptions.NicknameExists;
import controller.exceptions.SameOldNewPassword;

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
        RegisterController.getInstance().onlineUser.setNickname(nickname);
    }

    public void changePassword(String currentPassword, String newPassword) throws Exception {
        if (!RegisterController.getInstance().onlineUser.getPassword().equals(currentPassword))
            throw new InvalidPassword();
        if (currentPassword.equals(newPassword))
            throw new SameOldNewPassword();
        RegisterController.getInstance().onlineUser.setPassword(newPassword);
    }
}
