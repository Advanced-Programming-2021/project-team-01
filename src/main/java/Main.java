import com.opencsv.exceptions.CsvValidationException;
import controller.DatabaseController;
import model.Deck;
import model.card.Card;
import view.menu.HandleRequestType;

import java.io.IOException;
import java.util.Map;

public class Main{
    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController.loadGameCards();
        HandleRequestType handleRequestType = new HandleRequestType();
        handleRequestType.start();
    }
}