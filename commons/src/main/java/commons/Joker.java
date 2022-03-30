package commons;


public class Joker {

    private long userID;

    private Integer gameID;

    public Joker (){}

    public Joker (long userID, Integer gameID){
        this.userID = userID;
        this.gameID = gameID;
    }

    public long getUserID() {
        return userID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
