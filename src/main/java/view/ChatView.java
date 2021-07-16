package view;

import Network.Client.Client;
import Network.Requests.Account.ExitChatRoomRequest;
import Network.Requests.Request;
import Network.Server.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChatView implements GraphicalView {
    public TextArea messageField;
    public ScrollPane scrollPane;
    public VBox messagesVBox;

    @Override
    public void init(Pane root) { }

    public void loadChatMessages(ArrayList<Message> messages) {
        for (Message message : messages)
            messagesVBox.getChildren().add(new ChatLabel(message));
    }

    @FXML
    private void sendMessage() {
        if (messageField.getText().length() > 0) {
            Label label = new Label(messageField.getText());
            messagesVBox.getChildren().add(label);
            messageField.setText("");
        }
    }

    @FXML
    private void exit() {
        Request request = new ExitChatRoomRequest(Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
        ViewSwitcher.switchTo(View.MAIN);
    }
}

class ChatLabel extends Label {
    private Message message;

    public ChatLabel(Message message) {
        this.message = message;
        setText(message.getSender() + " : " + message.getContent());
        setStyle("-fx-text-fill: white;-fx-font: 16px \"Arial\";");
    }
}
