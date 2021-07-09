package view;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import controller.Effect;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;
import model.card.Attribute;
import model.card.CardType;
import model.card.MonsterCard;
import model.card.MonsterType;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardCreator implements Initializable {

    public JFXTextField name;
    public JFXTextField description;
    public JFXSlider defenseSlider;
    public JFXSlider attackSlider;
    public ChoiceBox choiceBox;
    public ChoiceBox attribute;
    public ChoiceBox effect;
    public Text price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defenseSlider.setMax(4000);
        attackSlider.setMax(4000);
        price.textProperty().bind(StringProperty.stringExpression(IntegerProperty.integerExpression(attackSlider.valueProperty().add(defenseSlider.valueProperty()))));
    }

    public Pair<MonsterCard, Effect> createCard(MouseEvent event) {
        String name;
        String description;
        int defense;
        int attack;
        int level;
        Attribute attribute;
        try {
            name = this.name.getText();
            description = this.description.getText();
            defense = (int) attackSlider.getValue();
            attack = (int) attackSlider.getValue();
            level = (int)choiceBox.getValue();
            attribute = (Attribute) (this.attribute.getValue());
        }catch (Exception e){
            new MyAlert(Alert.AlertType.WARNING, "Why are your field empty?").show();
            return null;
        }
        Effect effect;
        try {
            effect = (Effect) this.effect.getValue();
        }catch (Exception e){
            effect = null;
        }
        int price = Integer.parseInt(this.price.getText());
        if (name.isEmpty() || description.isEmpty() || attribute == null){
            new MyAlert(Alert.AlertType.WARNING, "Why are your field empty?").show();
        }
        ArrayList<MonsterType> arrayList = new ArrayList<>();
        arrayList.add(MonsterType.DRAGON);
        MonsterCard monsterCard = new MonsterCard(name, description, price, attack, defense, CardType.NORMAL,arrayList,attribute,level);
        return new Pair<>(monsterCard, effect);
    }

    public void init(Pane pane) {
        Image image = new Image(getClass().getResource("/Assets/gx.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));
    }

    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("src/resources/Creator/Pairs.json");
            Gson gson = new Gson();
            gson.toJson(new CustomCard(),fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
