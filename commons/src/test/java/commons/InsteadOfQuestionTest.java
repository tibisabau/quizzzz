package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsteadOfQuestionTest {

    Activity activity1;

    Activity activity2;

    Activity activity3;

    Activity activity4;

    Activity activity5;

    InsteadOfQuestion insteadOfQuestion;

    InsteadOfQuestion equalsTestQuestion;

    @BeforeEach
    void setUp(){
        activity1 = new Activity("path1","title1",1001);
        activity2 = new Activity("path2","title2",1002);
        activity3 = new Activity("path3","title3",1003);
        activity4 = new Activity("path4","title4",1004);
        activity5 = new Activity("path5","title5",1005);
        insteadOfQuestion = new InsteadOfQuestion(activity1,
                activity2, activity3, activity4, activity5);
    }

    @Test
    void getPromptedOption() {
        assertEquals(activity1, insteadOfQuestion.getPromptedOption());
    }

    @Test
    void getIdentity() {
        assertEquals("this is me", insteadOfQuestion.getIdentity());
    }

    @Test
    void setPromptedOption() {
        Activity testActivity = new Activity();
        insteadOfQuestion.setPromptedOption(testActivity);
        assertEquals(testActivity, insteadOfQuestion.getPromptedOption());
    }

    @Test
    void getCorrectOption() {
        assertEquals(activity2, insteadOfQuestion.getCorrectOption());
    }

    @Test
    void setCorrectOption() {
        Activity testActivity = new Activity();
        insteadOfQuestion.setCorrectOption(testActivity);
        assertEquals(testActivity, insteadOfQuestion.getCorrectOption());
    }

    @Test
    void getFirstOption() {
        assertEquals(activity3, insteadOfQuestion.getFirstOption());
    }

    @Test
    void setFirstOption() {
        Activity testActivity = new Activity();
        insteadOfQuestion.setFirstOption(testActivity);
        assertEquals(testActivity, insteadOfQuestion.getFirstOption());
    }

    @Test
    void getSecondOption() {
        assertEquals(activity4, insteadOfQuestion.getSecondOption());
    }

    @Test
    void getThirdOption() {
        assertEquals(activity5, insteadOfQuestion.getThirdOption());

    }

    @Test
    void setSecondOption() {
        Activity testActivity = new Activity();
        insteadOfQuestion.setSecondOption(testActivity);
        assertEquals(testActivity, insteadOfQuestion.getSecondOption());
    }

    @Test
    void setThirdOption() {
        Activity testActivity = new Activity();
        insteadOfQuestion.setThirdOption(testActivity);
        assertEquals(testActivity, insteadOfQuestion.getThirdOption());
    }

    @Test
    void testEquals() {
        equalsTestQuestion = new InsteadOfQuestion(null,
                null, null, null, null);
        assertFalse(insteadOfQuestion.equals(equalsTestQuestion));
        equalsTestQuestion = new InsteadOfQuestion(activity1, activity2,
                activity3, activity4, activity5);
        assertTrue(insteadOfQuestion.equals(equalsTestQuestion));
    }

    @Test
    void testHashCode() {
        assertNotNull(insteadOfQuestion.hashCode());
        equalsTestQuestion = new InsteadOfQuestion(activity1, activity3,
                activity3, activity5, activity5);
        assertNotEquals(equalsTestQuestion.hashCode(),
                insteadOfQuestion.hashCode());
    }

    @Test
    void testToString() {
       assertNotNull(insteadOfQuestion.toString());
    }
}