import com.opencsv.exceptions.CsvValidationException;
import controller.DatabaseController;
import view.menu.HandleRequestType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvValidationException {
        DatabaseController.loadGameCards();
        HandleRequestType handleRequestType = new HandleRequestType();
        handleRequestType.start();

    }
}
