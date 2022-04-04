package commons;


public class Joker {

    private long userID;

    private Integer gameID;

    private int type;

    private String userName;

    /**
     * Constructor for Joker
     */
    public Joker (){}

    /**
     * Constructor for Joker
     * @param userID user who send the joker
     * @param gameID the game to send to
     */
    public Joker (long userID, Integer gameID, int type, String userName){
        this.userID = userID;
        this.gameID = gameID;
        this.type = type;
        this.userName = userName;

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

    /**
     * getter for Type
     * @return int type
     */
    public int getType() {
        return type;
    }

    /**
     * getter username
     * @return String userName
     */
    public String getUserName() {
        return userName;
    }
}
