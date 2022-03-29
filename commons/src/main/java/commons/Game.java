package commons;


import javax.persistence.*;
import java.util.List;


public class Game {

    public List<Object> questions;

    public int counter = 0;

    private int id;

    private Score user;

    private boolean pointsJoker;

    private boolean answerJoker;

    private boolean timeJoker;

    public Game(){

    }

    public Game(int id, List <Object> questions){
        this.questions = questions;
        this.id = id;
        pointsJoker = true;
        answerJoker = true;
        timeJoker = true;
    }

    public int getCounter(){
        return counter;
    }

    public Score getUser() {
        return user;
    }

    public void updateScore(Score score){
        this.user = score;
    }

    public void incrementScore(int value){
        this.user.incrementScore(value);
    }

    public Object getNextQuestion(){
        return questions.get(counter++);
    }

    public Integer getID(){
        return this.id;
    }

    public void usePointJoker(){
        pointsJoker = false;
    }

    public void useAnswerJoker(){
        answerJoker = false;
    }

    public void useTimeJoker(){
        timeJoker = false;
    }

    public boolean isPointsJoker(){
        return pointsJoker;
    }

    public boolean isAnswerJoker(){
        return answerJoker;
    }

    public boolean isTimeJoker(){
        return timeJoker;
    }
}
