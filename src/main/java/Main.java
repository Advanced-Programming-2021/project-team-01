import com.opencsv.exceptions.CsvValidationException;
import controller.DatabaseController;
import controller.GameController;
import view.menu.HandleRequestType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController.loadGameCards();
        GameController.getInstance();
        HandleRequestType handleRequestType = new HandleRequestType();
        handleRequestType.start();
    }
}
