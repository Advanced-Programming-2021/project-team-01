import com.google.gson.Gson;
import controller.DatabaseController;
import controller.DeckController;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    @Test
    void doesDataBaseCreateSuccessfully(){
        Player player = new Player("ali","123","hoy");
        DatabaseController.updatePlayer(player);
        assertEquals(new Gson().toJson(player),
                new Gson().toJson(DatabaseController.getUserByName("ali")));
    }
    @Test
    void f(){
        assertEquals(1,1);
    }
}