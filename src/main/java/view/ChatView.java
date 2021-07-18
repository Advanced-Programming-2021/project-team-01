package view;

import Network.Client.Client;
import Network.Requests.Account.DeleteMessageRequest;
import Network.Requests.Account.ExitChatRoomRequest;
import Network.Requests.Account.SendMessageRequest;
import Network.Requests.Request;
import Network.Server.Message;
import controller.DatabaseController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.transions.ProfileStatsPopUp;

import java.awt.*;
import java.util.ArrayList;

public class ChatView implements GraphicalView {
    public String username;
    public TextArea messageField;
    public ScrollPane scrollPane;
    public VBox messagesVBox;
    public Text numberOfOnlineUsers;

    public void setNumber(int number) {
        numberOfOnlineUsers.setText(String.valueOf(number));
    }

    @Override
    public void init(Pane root) {
    }

    public void loadChatMessages(ArrayList<Message> messages) {
        messagesVBox.getChildren().clear();
        for (Message message : messages) {
            HBox hBox = new HBox();
            ImageView profileView = new ImageView(new Image(getClass().getResource(
                    "/Assets/ProfileDatabase/Chara001.dds" + String.valueOf(message.getProfileNum()) + ".png").toExternalForm()));
            profileView.setFitHeight(25);
            profileView.setFitWidth(25);
            ProfileStatsPopUp popUp = new ProfileStatsPopUp(message.getSender(), DatabaseController.getUserByName(message.getSender()).getNickname());
            profileView.setOnMouseClicked(event -> {
                popUp.setX(MouseInfo.getPointerInfo().getLocation().x);
                popUp.setY(MouseInfo.getPointerInfo().getLocation().y);
                if (!popUp.isShowing()) {
                    popUp.show(ViewSwitcher.getStage());
                    Timeline timeline = new Timeline();
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(2.5), event1 -> {
                        if (popUp.isShowing())
                            popUp.hide();
                    });
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            });
            hBox.setSpacing(10);
            hBox.getChildren().addAll(profileView, new ChatLabel(message));
            messagesVBox.getChildren().add(hBox);
        }
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
