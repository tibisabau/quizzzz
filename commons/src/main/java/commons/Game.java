package commons;


public class Game {
    private int id;
    private int Score;
    private int questionNumber;

    public Game(int id){
        this.id = id;
        this.Score = 0;
        this.questionNumber = 0;
    }

    public void incrementScore(int value){
        this.Score += value;
    }

    public void nextQuestion(){
        questionNumber++;
    }
}
