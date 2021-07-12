package view;

import controller.DatabaseController;
import controller.GameController;
import controller.ProfileController;
import controller.RegisterController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Player;

public class ProfileView implements GraphicalView{
    public ImageView profileImage;
    public Text username;
    public Text nickname;
    Player player;


    public void initializeInfo(Player player){
        this.player = player;
        Image image = new Image(getClass().getResource("/Assets/ProfileDatabase/Chara001.dds" + player.getProfile() + ".png").toExternalForm());
        profileImage.setImage(image);
        username.setText(player.getUsername());
        nickname.setText(player.getNickname());
    }

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
            this.nickname.setText(nickname);
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
        ViewSwitcher.switchTo(View.MAIN);
    }

    @Override
    public void init(Pane root) {

    }
}
