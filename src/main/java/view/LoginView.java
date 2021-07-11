package view;

import Network.Client.Client;
import Network.Requests.Account.LoginRequest;
import Network.Requests.Request;
import Network.Responses.Response;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.util.Random;

public class LoginView implements GraphicalView{


    public Label registerLabel;
    public JFXPasswordField rPasswordField;
    public JFXTextField rUsernameField;
    public JFXTextField usernameField;
    public JFXTextField nicknameField;
    public JFXPasswordField passwordField;
    public Label loginLabel;
    public static MediaView mediaView;

    @FXML
    public void init(Pane root){
        Media media = new Media(getClass().getResource("/Assets/YuGiOhGx.mp4").toExternalForm());
        mediaView = new MediaView(new MediaPlayer(media));
        mediaView.getMediaPlayer().play();
        mediaView.getMediaPlayer().autoPlayProperty().setValue(true);
        mediaView.getMediaPlayer().muteProperty().setValue(true);
    }

    public void signUp(MouseEvent event) {
        String nickname = nicknameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (password.isEmpty() || username.isEmpty() || nickname.isEmpty()) {
            registerLabel.setText("empty field");
            return;
        }
        try {
            RegisterController.getInstance().createUser(username, password, nickname);
            registerLabel.setText("");
            System.out.println("user created successfully!");
        } catch (Exception exception) {
            registerLabel.setText(exception.getMessage());
        }
    }

    public void login(MouseEvent event) {
        String username = rUsernameField.getText();
        String password = rPasswordField.getText();
        if (username.isEmpty() || password.isEmpty()){
            loginLabel.setText("empty field");
            return;
        }
        Request request = new LoginRequest(username, password);
        Client.getInstance().out.println(request);
        Client.getInstance().out.flush();
//      RegisterController.getInstance().loginUser(username, password);
        }

    public void loginResponse(Response response){
        try {
            if (response.getException() != null)
                throw response.getException();
            mediaView.getMediaPlayer().stop();
            ViewSwitcher.switchTo(View.MAIN);
            registerLabel.setText("");
            System.out.println("user logged in successfully!");
        }catch (Exception exception){
            loginLabel.setText(exception.getMessage());
        }

    }

}
