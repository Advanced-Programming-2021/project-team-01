package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.card.Card;
import model.card.MonsterCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ShopView implements Initializable {
    public Tab monsterTab;
    public BorderPane mainPane;
    public StackPane imageBar;
    public ScrollPane scroll;


    public void init(){
        GridPane pane = new GridPane();
        scroll.setContent(pane);
        monsterTab.setContent(scroll);
        pane.setStyle("-fx-background-color: transparent");
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        ArrayList<Card> monsters = getMonsters();
        ArrayList<Card> spells = getSpell();
        for (int i = 0; i < Math.round((double) monsters.size() / 6); i++){
            for (int j = 0; j < (Math.min((monsters.size() - 6 * i), 6)); j++){
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(250);
                rectangle.setWidth(150);
                rectangle.setFill(monsters.get(6 * i + j).getCardImage());
                pane.add(rectangle,j,i);
            }
        }
        imageBar.setPadding(new Insets(40,40,40,40));
        imageBar.getChildren().add(new Rectangle(220,380,Color.BLUE));

        ImageView button = new ImageView(new Image(getClass().getResource("k1.png").toExternalForm()));
        button.setFitWidth(100);
        button.setTranslateX(100);
        button.setTranslateY(1000);
        button.setFitHeight(100);
        button.setOnMouseClicked(event -> buyCard());
        mainPane.getChildren().add(button);
    }

    private ArrayList<Card> getMonsters() {
        ArrayList<Card> temp = new ArrayList<>();
        TreeMap<String, Card> treeMap = Card.getAllCards();
        for (Map.Entry<String, Card> entry : treeMap.entrySet()){
            if (entry.getValue() instanceof MonsterCard){
                temp.add(entry.getValue());
            }
        }
        return temp;
    }

    private ArrayList<Card> getSpell() {
        ArrayList<Card> temp = new ArrayList<>();
        TreeMap<String, Card> treeMap = Card.getAllCards();
        for (Map.Entry<String, Card> entry : treeMap.entrySet()){
            if (!(entry.getValue() instanceof MonsterCard)){
                temp.add(entry.getValue());
            }
        }
        return temp;
    }

    private void buyCard() {
        new MyAlert(Alert.AlertType.WARNING,"clicked on button").show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
}
