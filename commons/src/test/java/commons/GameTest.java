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

    game = new Game(20, 0);
    Game emptyGame = new Game();
    game.setCurrentQuestion(o1);
    game.updateScore(score);
    }

    @Test
    void getCounter() {
        assertEquals(-1, game.getCounter());
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
        assertEquals(o1, game.getCurrentQuestion());
    }

    @Test
    void getID() {
        assertEquals(20, game.getID());
    }

    @Test
    public void usePointJoker(){
        Game game1 = new Game(1, 2);
        game1.usePointJoker();
        assertFalse(game1.isPointsJoker());
    }

    @Test
    public void useAnswerJoker(){
        Game game1 = new Game(1, 2);
        game1.useAnswerJoker();
        assertFalse(game1.isAnswerJoker());
    }

    @Test
    public void useTimeJoker(){
        Game game1 = new Game(1, 2);
        game1.useTimeJoker();
        assertFalse(game1.isTimeJoker());
    }

    @Test
    public void isPointsJoker(){
        Game game1 = new Game(1, 2);
        boolean pointsJoker = game1.isPointsJoker();
        assertTrue(pointsJoker);
    }

    @Test
    public void isAnswerJoker(){
        Game game1 = new Game(1, 2);
        boolean pointsJoker = game1.isAnswerJoker();
        assertTrue(pointsJoker);
    }

    @Test
    public void isTimeJoker(){
        Game game1 = new Game(1, 2);
        boolean pointsJoker = game1.isTimeJoker();
        assertTrue(pointsJoker);
    }

    @Test
    public void getPlayerCount() {
        Game game1 = new Game(1, 2);
        assertEquals(game1.getPlayerCount(), 2);
    }
}