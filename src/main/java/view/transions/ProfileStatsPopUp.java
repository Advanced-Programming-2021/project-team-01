package view.transions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;


public class ProfileStatsPopUp extends Popup {
    public ProfileStatsPopUp(String username, String nickname) {
        centerOnScreen();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(100);
        vBox.setPrefHeight(100);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Text usernameTxt = new Text();
        usernameTxt.setText("Username : " + username);
        usernameTxt.setFill(Color.BLACK);
        Text nicknameTxt = new Text();
        nicknameTxt.setText("Nickname : " + nickname);
        nicknameTxt.setFill(Color.BLACK);
        vBox.getChildren().addAll(usernameTxt, nicknameTxt);
        getContent().add(vBox);
        requestFocus();
    }
}
