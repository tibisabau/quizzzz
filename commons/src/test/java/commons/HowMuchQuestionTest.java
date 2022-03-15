package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HowMuchQuestionTest {
    private HowMuchQuestion p;
    @BeforeEach
    public void setup() {
        Activity e1 = new Activity("", "", 1, "");
        Activity e2 = new Activity("", "", 1, "");
        Activity e3 = new Activity("", "", 1, "");
        Activity e4 = new Activity("", "", 1, "");
        p = new HowMuchQuestion(e1, e2, e3, e4);
    }
    @Test
    public void checkConstructor() {
        assertNotNull(p);
    }

    @Test
    public void equalsHashCode() {
        Activity e1 = new Activity("", "", 1, "");
        Activity e2 = new Activity("", "", 1, "");
        Activity e3 = new Activity("", "", 1, "");
        Activity e4 = new Activity("", "", 1, "");
        HowMuchQuestion me = new HowMuchQuestion(e1, e2, e3, e4);
        assertEquals(me, p);
        assertEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        Activity e1 = new Activity("", "", 1, "");
        Activity e2 = new Activity("", "", 2, "");
        Activity e3 = new Activity("", "", 1, "");
        Activity e4 = new Activity("", "", 2, "");
        HowMuchQuestion me = new HowMuchQuestion(e1, e2, e3, e4);
        assertNotEquals(me, p);
        assertNotEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = p.toString();
        assertTrue(actual.contains(HowMuchQuestion.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("firstOption"));
    }
}