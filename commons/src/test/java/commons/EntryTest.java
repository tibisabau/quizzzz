package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class EntryTest {
    @Test
    public void checkConstructor() {
        var p = new Entry(10, "lala", 1);
        assertNotNull(p);
    }

    @Test
    public void equals() {
        var a = new Entry(3, "b",1);
        var b = new Entry(3, "b",1);
        assertEquals(a, b);
    }

    @Test
    public void notEqualsHashCode() {
        var a = new Entry(1, "b",1);
        var b = new Entry(2, "c",1);
        assertNotEquals(a, b);

    }

}
