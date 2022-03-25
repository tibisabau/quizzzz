package commons;


import javax.persistence.*;
import java.util.List;

@Entity
public class Game {


    @Id
    private int id;

    @Transient
    private Score user;

    @Transient
    private List<Object> questions;

    private int counter = 0;

    public Score getUser() {
        return user;
    }


    public Game(int id, List<Object> questions){
        this.id = id;
        this.questions = questions;
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
}
