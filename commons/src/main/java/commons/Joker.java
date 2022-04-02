package commons;


public class Joker {

    private long userID;

    private Integer gameID;

    /**
     * Constructor for Joker
     */
    public Joker (){}

    /**
     * Constructor for Joker
     * @param userID user who send the joker
     * @param gameID the game to send to
     */
    public Joker (long userID, Integer gameID){
        this.userID = userID;
        this.gameID = gameID;
    }

    /**
     * getter for userID
     * @return long userID
     */
    public long getUserID() {
        return userID;
    }

    /**
     * getter for gameID
     * @return Integer gameID
     */
    public Integer getGameID() {
        return gameID;
    }
}
