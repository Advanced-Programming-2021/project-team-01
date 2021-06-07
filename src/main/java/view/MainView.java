package view;

import controller.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainView {
    @FXML
    private void logout() {
        RegisterController.getInstance().logout();
        ViewSwitcher.switchTo(View.LOGIN);
    }
}
