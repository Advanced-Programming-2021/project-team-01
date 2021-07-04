package view.transions;

import javafx.stage.Popup;
import model.card.Card;

import java.util.ArrayList;

public class CustomPopup extends Popup {
    public CustomPopup(ArrayList<Card> cards){
        setWidth(400);
        setHeight(400);
    }

}
