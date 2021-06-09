package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.RegisterController;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class LoginView {


    public Label registerLabel;
    public JFXPasswordField rPasswordField;
    public JFXTextField rUsernameField;
    public JFXTextField usernameField;
    public JFXTextField nicknameField;
    public JFXPasswordField passwordField;
    public Label loginLabel;

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
        try {
            RegisterController.getInstance().loginUser(username, password);
            ViewSwitcher.switchTo(View.MAIN);
            registerLabel.setText("");
            System.out.println("user logged in successfully!");
        } catch (Exception exception) {
            loginLabel.setText(exception.getMessage());
        }
    }
}
