package view;

import javafx.scene.control.Alert;
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

public class MyAlert extends Dialog<String> {
    public MyAlert(Alert.AlertType alertType, String message) {
        setResult("dummy");
        BorderPane pane = new BorderPane();
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/Assets/50061.png").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(500, 180, false, false, false, false));        pane.setBackground(new Background(backgroundimage));
        pane.setPrefWidth(450);
        pane.setPrefHeight(180);
        pane.setBackground(new Background(backgroundimage));
        getDialogPane().setContent(pane);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());

        Text text = new Text(message);
        text.setFont(Font.font("Helvetica", FontWeight.BOLD,20));
        text.setWrappingWidth(400);
        text.setFill(Color.WHITE);
        text.setTranslateX(+15);
        pane.setCenter(text);

        Button ok = new Button("ok");

        ok.setPrefWidth(80);
        ok.setPrefHeight(40);
        ok.setTranslateX(175);
        ok.setOnMouseClicked(event -> {
            setResult("ok");
            close();
        });

        Image image;
        ImageView imageView;
        switch (alertType) {
            case WARNING:{
                image = new Image("/Assets/warning.png");
                break;
            }case ERROR:{
                image = new Image("Assets/error.png");
                break;
            }case CONFIRMATION:{
                image = new Image("Assets/confirm.png");
                break;
            }case INFORMATION:{
                image = new Image("Assets/info.png");
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + alertType);
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setTranslateY(50);
        imageView.setTranslateX(30);
        imageView.setFitWidth(80);
        pane.setLeft(imageView);
        pane.setBottom(ok);


    }
}
