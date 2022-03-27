package commons;


import javax.persistence.*;
import java.util.List;


public class Game {

    private int id;

    private Score user;

    public List<Object> questions;

    public int counter = 0;

    public Score getUser() {
        return user;
    }

    public Game(){

    }

    public Game(int id, List <Object> questions){
        this.questions = questions;
        this.id = id;
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
}
