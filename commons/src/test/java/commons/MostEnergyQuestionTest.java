package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MostEnergyQuestionTest {

    private MostEnergyQuestion p;
    @BeforeEach
    public void setup() {
        Entry1 e1 = new Entry1( "", "", 1, "");
        Entry1 e2 = new Entry1( "", "", 1, "");
        Entry1 e3 = new Entry1( "", "", 1, "");
        Entry1 e4 = new Entry1( "", "", 1, "");
        p = new MostEnergyQuestion(e1, e2, e3, e4);
    }
    @Test
    public void checkConstructor() {
        assertNotNull(p);
    }

    @Test
    public void equalsHashCode() {
        Entry1 e1 = new Entry1("", "", 1, "");
        Entry1 e2 = new Entry1("", "", 1, "");
        Entry1 e3 = new Entry1("", "", 1, "");
        Entry1 e4 = new Entry1("", "", 1, "");
        MostEnergyQuestion me = new MostEnergyQuestion(e1, e2, e3, e4);
        assertEquals(me, p);
        assertEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void notEqualsHashCode() {
        Entry1 e1 = new Entry1("", "", 1, "");
        Entry1 e2 = new Entry1("", "", 2, "");
        Entry1 e3 = new Entry1("", "", 1, "");
        Entry1 e4 = new Entry1("", "", 2, "");
        MostEnergyQuestion me = new MostEnergyQuestion(e1, e2, e3, e4);
        assertNotEquals(me, p);
        assertNotEquals(p.hashCode(), me.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = p.toString();
        assertTrue(actual.contains(MostEnergyQuestion.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("firstOption"));
    }
}