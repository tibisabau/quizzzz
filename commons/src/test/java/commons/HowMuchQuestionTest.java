package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HowMuchQuestionTest {
    private HowMuchQuestion p;
    @BeforeEach
    public void setup() {
        Entry1 e1 = new Entry1(1, "", "", 1, "");
        Entry1 e2 = new Entry1(2, "", "", 1, "");
        Entry1 e3 = new Entry1(3, "", "", 1, "");
        Entry1 e4 = new Entry1(1, "", "", 1, "");
        p = new HowMuchQuestion(e1, e2, e3, e4);
    }
    @Test
    public void checkConstructor() {
        assertNotNull(p);
    }

    @Test
    public void equalsHashCode() {
        Entry1 e1 = new Entry1(1, "", "", 1, "");
        Entry1 e2 = new Entry1(2, "", "", 1, "");
        Entry1 e3 = new Entry1(3, "", "", 1, "");
        Entry1 e4 = new Entry1(1, "", "", 1, "");
        HowMuchQuestion me = new HowMuchQuestion(e1, e2, e3, e4);
        assertEquals(me, p);
        assertEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        Entry1 e1 = new Entry1(1, "", "", 1, "");
        Entry1 e2 = new Entry1(2, "", "", 2, "");
        Entry1 e3 = new Entry1(3, "", "", 1, "");
        Entry1 e4 = new Entry1(2, "", "", 2, "");
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