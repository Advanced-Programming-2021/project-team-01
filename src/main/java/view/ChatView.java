package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ChatView implements GraphicalView {
    public TextArea messageField;

    @Override
    public void init(Pane root) {

    }

    @FXML
    private void exit() {
        ViewSwitcher.switchTo(View.MAIN);
    }
}
