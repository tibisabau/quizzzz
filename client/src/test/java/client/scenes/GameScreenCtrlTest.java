package client.scenes;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GameScreenCtrlTest {
    GameScreenCtrl ctrl ;


    @BeforeEach
    public void setup() {
        MainCtrl sut = new MainCtrl();
        ctrl = new GameScreenCtrl(null, sut);
    }

    @Test
    public void testAnswerCorrectGX () {
        GuessXQuestion question = new GuessXQuestion();
        String path = "path";
        String title = "title";
        long consumption = 1 ;
        question.setCorrectOption(new Activity (path, title, consumption) );
        assertTrue( ctrl.GXCorrectAnswer(question, 1));
    }

    @Test
    public void testAnswerCorrectHM() {
        HowMuchQuestion question = new HowMuchQuestion();
        String path0 = "path0";
        String title0 = "title0";
        long consumption0 = 1 ;
        String path1 = "path1";
        String title1 = "title1";
        long consumption1 = 2 ;
        String path2 = "path2";
        String title2 = "title2";
        long consumption2 = 3 ;
        question.setFirstOption(new Activity(path0, title0, consumption0));
        question.setSecondOption(new Activity(path1, title1, consumption1));
        question.setThirdOption(new Activity(path2, title2, consumption2));
        question.setCorrectOption(question.getFirstOption());
        assertTrue(ctrl.HMCorrectAnswer(question, 1));
    }

    @Test
    public void testAnswerCorrectME() {
        MostEnergyQuestion question = new MostEnergyQuestion();
        String path0 = "path0";
        String title0 = "title0";
        long consumption0 = 1 ;
        String path1 = "path1";
        String title1 = "title1";
        long consumption1 = 2 ;
        String path2 = "path2";
        String title2 = "title2";
        long consumption2 = 3 ;
        question.setFirstOption(new Activity(path0, title0, consumption0));
        question.setSecondOption(new Activity(path1, title1, consumption1));
        question.setThirdOption(new Activity(path2, title2, consumption2));
        question.setCorrectOption(question.getFirstOption());
        assertTrue(ctrl.MECorrectAnswer(question, 1));
    }

    @Test
    public void testAnswerCorrectInsteadOf() {
        InsteadOfQuestion question = new InsteadOfQuestion();
        String path0 = "path0";
        String title0 = "title0";
        long consumption0 = 1 ;
        String path1 = "path1";
        String title1 = "title1";
        long consumption1 = 2 ;
        String path2 = "path2";
        String title2 = "title2";
        long consumption2 = 3 ;
        String path3 = "path3";
        String title3 = "title3";
        long consumption3 = 0 ;
        question.setPromptedOption(new Activity(path3, title3, consumption3));
        question.setFirstOption(new Activity(path0, title0, consumption0));
        question.setSecondOption(new Activity(path1, title1, consumption1));
        question.setThirdOption(new Activity(path2, title2, consumption2));
        question.setCorrectOption(question.getFirstOption());
        assertTrue(ctrl.insteadOfCorrectAnswer(question, 1));
    }


}
