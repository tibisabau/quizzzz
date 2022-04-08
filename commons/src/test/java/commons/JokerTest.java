package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JokerTest {

Joker joker;

Joker jokerEmpty;

long userID;

Integer gameID;

int type;

String userName;

    @BeforeEach
    void setUp(){
        userID = 123;
        gameID = 2;
        type = 1;
        userName = "Jack";
        joker = new Joker(userID, gameID, type, userName);
        jokerEmpty = new Joker();
    }


    @Test
    void getUserIDTest() {
        assertEquals(joker.getUserID(), 123);
    }

    @Test
    void getGameIdTest() {
        assertEquals(joker.getGameID(), 2);
    }

    @Test
    void getTypeTest() {
        assertEquals(joker.getType(), 1);
    }

    @Test
    void getUserNameTest() {
        assertEquals(joker.getUserName(), "Jack");
    }

}
