package view;

import Network.Client.Client;
import Network.Requests.Account.DeleteMessageRequest;
import Network.Requests.Account.ExitChatRoomRequest;
import Network.Requests.Account.SendMessageRequest;
import Network.Requests.Request;
import Network.Server.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChatView implements GraphicalView {
    public String username;
    public TextArea messageField;
    public ScrollPane scrollPane;
    public VBox messagesVBox;

    @Override
    public void init(Pane root) { }

    public void loadChatMessages(ArrayList<Message> messages) {
        messagesVBox.getChildren().clear();
        for (Message message : messages)
            messagesVBox.getChildren().add(new ChatLabel(message));
    }

    @FXML
    private void sendMessage() {
        if (messageField.getText().length() > 0) {
            Request request = new SendMessageRequest(new Message(username, messageField.getText()),
                    Client.getInstance().getToken());
            messageField.setText("");
            Client.getInstance().sendData(request.toString());
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
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete message");
        deleteItem.setOnAction(event -> {
            deleteMessage(message);
        });
        setContextMenu(contextMenu);
        contextMenu.getItems().addAll(deleteItem);
        contextMenu.setStyle("-fx-background-color: black;-fx-text-fill: white;");
        setOnContextMenuRequested(event -> {
            contextMenu.show(this, event.getScreenX(), event.getScreenY() - 100);
        });
    }

    private void deleteMessage(Message message) {
        Request request = new DeleteMessageRequest(message.getID(), Client.getInstance().getToken());
        Client.getInstance().sendData(request.toString());
    }
}
