package controller;

public class ProfileController {
    private static ProfileController instance = null;

    public static ProfileController getInstance() {
        if (instance == null) {
            instance = new ProfileController();
        }
        return instance;
    }

    public void changeNickname(String nickname) {
        System.out.println("EOOOOOO");
    }

    public void changePassword(String currentPassword, String newPassword) {

    }
}
