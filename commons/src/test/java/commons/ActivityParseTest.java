package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityParseTest {
    ActivityParse activityParse1;

    ActivityParse activityParse2;
    
    ActivityParse activityParse3;

    @BeforeEach
    void setUp() {
        activityParse1 = new ActivityParse("id","image_path","title",1000,"source");
        activityParse2 = new ActivityParse("id","image_path","title",1000,"source");
        activityParse3 = new ActivityParse("new_id","new_image_path","new_title",2000,"new_source");
    }

    @Test
    void testEquals() {
        assertEquals(activityParse1,activityParse2);
        assertNotEquals(activityParse1,activityParse3);
    }

    @Test
    void setId() {
        activityParse1.setId("test_id");
        assertEquals("test_id",activityParse1.id);
    }

    @Test
    void setTitle() {
        activityParse1.setTitle("test_title");
        assertEquals("test_title",activityParse1.title);
    }

    @Test
    void setImagePath() {
        activityParse1.setImagePath("test_path");
        assertEquals("test_path", activityParse1.imagePath);
    }

    @Test
    void setSource() {
        activityParse1.setSource("test_source");
        assertEquals("test_source",activityParse1.source);
    }

    @Test
    void setConsumptionInWh() {
        activityParse1.setConsumptionInWh(7000);
        assertEquals(7000, activityParse1.consumptionInWh);
    }

    @Test
    void getId() {
        assertEquals("id",activityParse1.getId());
    }

    @Test
    void getTitle() {
        assertEquals("title", activityParse1.getTitle());
    }

    @Test
    void getImagePath() {
        assertEquals("image_path",activityParse1.getImagePath());
    }

    @Test
    void getSource() {
        assertEquals("source", activityParse1.getSource());
    }

    @Test
    void getConsumptionInWh() {
        assertEquals(1000, activityParse1.getConsumptionInWh());
    }
}