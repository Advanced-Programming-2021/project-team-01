package view;

import Network.Client.Client;
import Network.Requests.Account.LoginRequest;
import Network.Requests.Account.RegisterRequest;
import Network.Requests.Request;
import Network.Responses.Account.RegisterResponse;
import Network.Responses.Response;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class LoginView implements GraphicalView {


    public static MediaView mediaView;
    public Label registerLabel;
    public JFXPasswordField rPasswordField;
    public JFXTextField rUsernameField;
    public JFXTextField usernameField;
    public JFXTextField nicknameField;
    public JFXPasswordField passwordField;
    public Label loginLabel;

    @FXML
    public void init(Pane root) {
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
        Request request = new RegisterRequest(username, password, nickname);
        Client.getInstance().sendData(request.toString());

    }

    public void signUpResponse(RegisterResponse response) {
        try {
            if (response.getException() != null)
                throw response.getException();
            registerLabel.setText("");
            System.out.println("user created successfully!");
        } catch (Exception exception) {
            registerLabel.setText(exception.getMessage());
        }
    }

    public void login(MouseEvent event) {
        String username = rUsernameField.getText();
        String password = rPasswordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            loginLabel.setText("empty field");
            return;
        }
        Request request = new LoginRequest(username, password);
        Client.getInstance().sendData(request.toString());
//      RegisterController.getInstance().loginUser(username, password);
    }

    public boolean loginResponse(Response response) {
        try {
            if (response.getException() != null)
                throw response.getException();
            mediaView.getMediaPlayer().stop();
            ViewSwitcher.switchTo(View.MAIN);
            registerLabel.setText("");
            System.out.println("user logged in successfully!");
            return true;
        } catch (Exception exception) {
            loginLabel.setText(exception.getMessage());
            return false;
        }

    }

}
