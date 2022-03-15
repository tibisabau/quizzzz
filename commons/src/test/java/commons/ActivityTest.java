package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
        Activity activity1;
        Activity activity2;
        Activity activity3;

    @BeforeEach
    void setUp() {
        activity1 = new Activity("path", "title", 1000);
        activity2 = new Activity("path", "title", 1000);
        activity3 = new Activity("new_path", "new_title", 2000);
    }

    @Test
    void testEquals() {
        activity1.setId(0);
        activity2.setId(0);
        assertEquals(activity1, activity2);
    }

    @Test
    void setId() {
        activity1.setId(3);
        assertEquals(3, activity1.id);
    }

    @Test
    void setTitle() {
        activity1.setTitle("i_just_set_this_title");
        assertEquals("i_just_set_this_title", activity1.title);
    }

    @Test
    void setImage_path() {
        activity1.setImage_path("some_path");
        assertEquals("some_path",activity1.image_path);
    }

    @Test
    void setConsumption_in_wh() {
        activity1.setConsumption_in_wh(1234);
        assertEquals(1234, activity1.consumption_in_wh);
    }

    @Test
    void getId() {
        activity1.id = 7;
        assertEquals(7, activity1.getId());
    }

    @Test
    void getTitle() {
        activity1.title = "test_title";
        assertEquals("test_title", activity1.getTitle());
    }

    @Test
    void getImage_path() {
        activity1.image_path = "test_path";
        assertEquals("test_path", activity1.getImage_path());
    }

    @Test
    void getConsumption_in_wh() {
        activity1.consumption_in_wh = 7000;
        assertEquals(7000, activity1.getConsumption_in_wh());
    }

    @Test
    void toStringAnswer() {
        assertEquals("title", activity1.toStringAnswer());
    }

    @Test
    void testHashCode() {
        assertNotNull(activity1.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(activity1.toString());
    }
}