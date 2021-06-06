package view;

import javafx.scene.control.Alert;

public class MyAlert extends Alert {
    public MyAlert(AlertType alertType,String message) {
        super(alertType);
        setHeaderText(message);
    }
}
