import com.google.gson.Gson;
import controller.DatabaseController;
import model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    @Test
    void doesDataBaseCreateSuccessfully() {
        Player player = new Player("ali", "123", "hoy");
        DatabaseController.updatePlayer(player);
        assertEquals(new Gson().toJson(player),
                new Gson().toJson(DatabaseController.getUserByName("ali")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hoy", "nickname"})
    void nicknameTest(String name) {
        assertTrue(DatabaseController.doesNicknameExist(name));
    }
    @ParameterizedTest
    @ValueSource(strings = {"horrry", "nicrererkname"})
    void nicknameFailTest(String name) {
        assertFalse(DatabaseController.doesNicknameExist(name));
    }

    @Test
    void f() {
        assertEquals(1, 1);
    }
}