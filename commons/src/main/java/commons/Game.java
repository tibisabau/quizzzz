package commons;



public class Game {

    public Object currentQuestion;

    public int counter = -1;

    private int id;

    private Score user;

    private boolean pointsJoker;

    private boolean answerJoker;

    private boolean timeJoker;

    private int playerCount;

    /**
     * Constructor for game
     */
    public Game(){

    }

    /**
     * Constructor for Game
     * @param id of the game
     */
    public Game(int id, int playerCount){
        this.id = id;
        pointsJoker = true;
        answerJoker = true;
        timeJoker = true;
        this.playerCount = playerCount;
    }

    /**
     * getter for counter
     * @return int counter
     */
    public int getCounter(){
        return counter;
    }

    /**
     * getter for user
     * @return Score user
     */
    public Score getUser() {
        return user;
    }

    /**
     * Setter for user
     * @param score you want to put in user.
     *
     */
    public void updateScore(Score score){
        this.user = score;
    }

    /**
     * incresse the score value of the user.
     * @param value the value to increse by
     */
    public void incrementScore(int value){
        this.user.incrementScore(value);
    }


    public void setCurrentQuestion(Object question){
        currentQuestion = question;
    }

    public Object getCurrentQuestion() {
        counter ++;
        return currentQuestion;
    }

    /**
     * getter for ID
     * @return Interger id
     */
    public Integer getID(){
        return this.id;
    }

    /**
     * use Points joker makes points joker boolean false
     */
    public void usePointJoker(){
        pointsJoker = false;
    }

    /**
     * use answer joker makes answer joker boolean false
     */
    public void useAnswerJoker(){
        answerJoker = false;
    }

    /**
     * use time joker makes points joker boolean false
     */
    public void useTimeJoker(){
        timeJoker = false;
    }

    /**
     * getter for pointsjoker
     * @return  Boolean pointsjoker
     */
    public boolean isPointsJoker(){
        return pointsJoker;
    }

    /**
     * getter for answerJoker
     * @return boolean answerJoker
     */
    public boolean isAnswerJoker(){
        return answerJoker;
    }

    /**
     * getter for timeJoker
     * @return boolean timeJoker
     */
    public boolean isTimeJoker(){
        return timeJoker;
    }

    /**
     * Getter for the playerCount
     * @return
     */
    public int getPlayerCount() {
        return this.playerCount;
    }
}
