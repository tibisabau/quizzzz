package commons;

import java.util.Timer;

public class MPController {

    int questionNo = 0;

    int gameID;

    Timer timer;

    public MPController(int gameID){
        this.gameID = gameID;
    }

    public boolean nextQuestion(){
        if(questionNo == 19){
            return false;
        }
        questionNo++;
        return  true;
    }

}
