package view;

import controller.RegisterController;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginView {

    public PasswordField passwordField;
    public TextField usernameField;

    public void signUp(MouseEvent event){
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.getEditor().setText("Enter a nickname");
        inputDialog.showAndWait();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String nickname = inputDialog.getEditor().getText();
        try {
            RegisterController.getInstance().createUser(username, password, nickname);
            System.out.println("user created successfully!");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

    }

    public void login(MouseEvent event){
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()){
            new MyAlert(Alert.AlertType.ERROR,"empty fields").show();
            return;
        }
        try {
            RegisterController.getInstance().loginUser(usernameField.getText(), passwordField.getText());
            System.out.println("user logged in successfully!");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }

    }

}
