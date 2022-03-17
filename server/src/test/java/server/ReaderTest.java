package server;

import commons.Activity;
import commons.ActivityParse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    ActivityParse activityParse;

    Activity activity;

    @BeforeEach
    void setUp() {
        activityParse = new ActivityParse("id","path","title",1000,"source");
        activity = new Activity("path","title",1000);
    }

    @Test
    void convert() {
        Activity testActivity = Reader.convert(activityParse);
        assertEquals(activity, testActivity);
    }
}