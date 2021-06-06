package view;

import controller.RegisterController;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class LoginView {

    public PasswordField passwordField;
    public TextField usernameField;

    public void signUp(MouseEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setContentText("Enter a nickname");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (password.isBlank() || username.isBlank()){
            new MyAlert(Alert.AlertType.ERROR,"empty fields").show();
            return;
        }
        inputDialog.showAndWait();
        String nickname = inputDialog.getEditor().getText();
        if (nickname.isBlank()){
            new MyAlert(Alert.AlertType.ERROR,"empty field").show();
            return;
        }
        try {
            RegisterController.getInstance().createUser(username, password, nickname);
            System.out.println("user created successfully!");
        } catch (Exception exception) {
            inputDialog.close();
            new MyAlert(Alert.AlertType.ERROR,exception.getMessage()).show();
        }

    }

    public void login(MouseEvent event) {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            new MyAlert(Alert.AlertType.ERROR, "empty fields").show();
            return;
        }
        try {
            RegisterController.getInstance().loginUser(usernameField.getText(), passwordField.getText());
            System.out.println("user logged in successfully!");
        } catch (Exception exception) {
            new MyAlert(Alert.AlertType.ERROR, exception.getMessage());
        }

    }

}
