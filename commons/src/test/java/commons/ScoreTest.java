package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreTest {

    @Test
    public void checkConstructor() {
        var s = new Score("User", 10);
        assertEquals("User", s.getUserName());
        assertTrue(10 == s.getScore());
    }

    @Test
    public void equalsHashCode() {
        var s1 = new Score("a", 3);
        var s2 = new Score("a", 3);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Score("a", 10);
        var b = new Score("a", 11);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void getGameTest() {
        var a = new Score("a", 10);
        assertEquals(a.getGame(), 0);
    }

    @Test
    public void setGameTest() {
        var a = new Score("a", 10);
        a.setGame(2);
        assertEquals(a.getGame(), 2);
    }

    @Test
    public void getScoreText() {
        var a = new Score("a", 10);
        assertEquals(a.getScore(), 10);
    }

    @Test
    public void setScoreText() {
        var a = new Score("a", 10);
        a.setScore(2);
        assertEquals(a.getScore(), 2);
    }

    @Test
    public void setUserNameTest() {
        var a = new Score("bob", 10);
        a.setUserName("a");
        assertEquals(a.getUserName(), "a");
    }

    @Test
    public void equalsTest() {
        var a = new Score("bob", 10);
        Object b = null;
        assertFalse(a.equals(b));
    }

    @Test
    public void hasToString() {
        var actual = new Score("a", 10).toString();
        assertTrue(actual.contains(Score.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("userName"));
    }

}
