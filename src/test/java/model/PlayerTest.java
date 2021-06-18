package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    @DisplayName("creation test")
    void test(){
        Player p = new Player("axiom", "123","mojo");
        assertEquals(p.getNickname(),"mojo");
        assertEquals(p.getPassword(),"123");
        assertEquals(p.getUsername(),"axiom");
    }

}