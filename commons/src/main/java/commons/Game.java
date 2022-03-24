package commons;


import java.util.Iterator;
import java.util.List;

public class Game {
    private int id;
    private Score user;
    public Iterator<Object> iter;

    public Game(int id, Score user, List<Object> questions){
        this.id = id;
        this.user = user;
        iter = questions.iterator();
    }

    public void incrementScore(int value){
        this.user.incrementScore(value);
    }

    public Object getNextQuestion(){
        return iter.next();
    }
}
