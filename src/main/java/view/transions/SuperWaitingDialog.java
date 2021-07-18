package view.transions;

import Network.Client.Client;
import Network.Requests.Battle.CancelMatchMakingRequest;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import view.GamePreview;


public class SuperWaitingDialog extends Dialog<Boolean> {
    BorderPane pane;

    public SuperWaitingDialog() {
        pane = new BorderPane();
        BackgroundImage backgroundimage = new BackgroundImage(new javafx.scene.image.Image(getClass().getResource("/Assets/50061.png").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(500, 180, false, false, false, false));
        pane.setBackground(new Background(backgroundimage));
        pane.setPrefWidth(450);
        pane.setPrefHeight(180);
        pane.setBackground(new Background(backgroundimage));
        getDialogPane().setContent(pane);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());

        Text text = new Text("Your request is being handled\n Wait just a sec");
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        text.setWrappingWidth(400);
        text.setFill(Color.WHITE);
        text.setTranslateX(+15);
        pane.setCenter(text);

        javafx.scene.image.Image image;
        ImageView imageView;
        image = new Image("Assets/info.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setTranslateY(50);
        imageView.setTranslateX(30);
        imageView.setFitWidth(80);
        pane.setLeft(imageView);

        Button button = new Button("cancel");
        button.setTranslateX(150);
        button.setPadding(new Insets(10,10,10,10));
        button.setOnMouseClicked(event -> {
            CancelMatchMakingRequest cancelMatchMakingRequest = new CancelMatchMakingRequest(Client.getInstance().getToken());
            Client.getInstance().sendData(cancelMatchMakingRequest.toString());
            setResult(true);
            close();
        });

        pane.setBottom(button);
    }
}
