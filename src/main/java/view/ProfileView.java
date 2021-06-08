package view;

import controller.DatabaseController;
import controller.ProfileController;
import controller.RegisterController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class ProfileView {
    public void changeNickname(MouseEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText("enter new nickname");
        inputDialog.showAndWait();
        String nickname = inputDialog.getEditor().getText();
        if (nickname.isEmpty()){
            inputDialog.close();
            new MyAlert(Alert.AlertType.ERROR,"emptyField").show();
            return;
        }
        try {
            ProfileController.getInstance().changeNickname(nickname);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    public void changePassword(MouseEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText("enter your old password");
        inputDialog.showAndWait();
        String oldPassword = inputDialog.getEditor().getText();
        if (oldPassword.isEmpty()){
            inputDialog.close();
            new MyAlert(Alert.AlertType.ERROR,"emptyField").show();
            return;
        }
        inputDialog.close();
        inputDialog.setContentText("enter your new password");
        inputDialog.showAndWait();
        String newPassword = inputDialog.getEditor().getText();
        if (newPassword.isEmpty()){
            inputDialog.close();
            new MyAlert(Alert.AlertType.ERROR,"emptyField").show();
            return;
        }
        try {
            ProfileController.getInstance().changePassword(oldPassword, newPassword);
        } catch (Exception exception) {
            new MyAlert(Alert.AlertType.ERROR,exception.getMessage()).show();
        }

    }

    public void back(MouseEvent event) {
        DatabaseController.updatePlayer(RegisterController.onlineUser);
        ViewSwitcher.switchTo(View.MAIN);
    }
}
