package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    Object o1;

    Object o2;

    Score score;

    @BeforeEach
    void setUp(){

    List<Object> questions = new ArrayList<>();

    o1 = new Object();

    o2 = new Object();

    score = new Score("user",1000);

    questions.add(o1);
    questions.add(o2);

    game = new Game(20, questions);

    game.updateScore(score);
    }

    @Test
    void getCounter() {
        assertEquals(0, game.getCounter());
    }

    @Test
    void getUser() {
        assertEquals(score, game.getUser());
    }

    @Test
    void updateScore() {
        Score testScore = new Score("test",20000);
        game.updateScore(testScore);
        assertEquals(testScore, game.getUser());
    }

    @Test
    void incrementScore() {
        game.incrementScore(50);
        assertEquals(1050, game.getUser().getScore());
    }

    @Test
    void getNextQuestion() {
        assertEquals(o1, game.getNextQuestion());
    }

    @Test
    void getID() {
        assertEquals(20, game.getID());
    }
}