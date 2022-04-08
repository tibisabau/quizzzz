package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessXQuestionTest {
    private GuessXQuestion p;

    @BeforeEach
    public void setup() {
        Activity e1 = new Activity("", "", 1);
        p = new GuessXQuestion(e1);
    }

    @Test
    public void checkConstructor() {
        GuessXQuestion x = new GuessXQuestion();
        assertNotNull(p);
        assertNotNull(x);
    }

    @Test
    public void equalsHashCode() {
        Activity e1 = new Activity("", "", 1);
        GuessXQuestion me = new GuessXQuestion(e1);
        assertEquals(me, p);
        assertEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void setCorrectOptionTest() {
        Activity a = new Activity();
        p.setCorrectOption(a);
        assertEquals(p.getCorrectOption(), a);
    }

    @Test
    public void notEqualsHashCode() {
        Activity e2 = new Activity("", "", 2);
        GuessXQuestion me = new GuessXQuestion(e2);
        assertNotEquals(me, p);
        assertNotEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = p.toString();
        assertTrue(actual.contains(GuessXQuestion.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("correctOption"));
    }
}