package view;

import controller.DatabaseController;
import controller.RegisterController;
import javafx.scene.input.MouseEvent;

public class ProfileView {
    public void changeNickname(MouseEvent event) {

    }

    public void changePassword(MouseEvent event) {


    }

    public void back(MouseEvent event) {
        DatabaseController.updatePlayer(RegisterController.onlineUser);
        ViewSwitcher.switchTo(View.MAIN);
    }
}
