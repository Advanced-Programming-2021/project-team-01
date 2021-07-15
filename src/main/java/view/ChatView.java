package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ChatView implements GraphicalView {
    public TextArea messageField;
    public ScrollPane scrollPane;
    public VBox messagesVBox;

    @Override
    public void init(Pane root) {
    }

    @FXML
    private void sendMessage() {
        if (messageField.getText().length() > 0) {
            Label label = new Label(messageField.getText());
            label.setStyle("-fx-text-fill: white;-fx-font: 16px \"Arial\";");
            messagesVBox.getChildren().add(label);
            messageField.setText("");
        }
    }

    @FXML
    private void exit() {
        ViewSwitcher.switchTo(View.MAIN);
    }
}
