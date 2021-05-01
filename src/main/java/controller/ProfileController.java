package controller;

import controller.exceptions.NicknameExists;

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

    public void changePassword(String currentPassword, String newPassword) {

    }
}
