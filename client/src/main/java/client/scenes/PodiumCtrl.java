package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Score;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PodiumCtrl {

    @FXML
    public Text first;

    @FXML
    public Text second;

    @FXML
    public Text third;

    @FXML
    public Text message;

    private ObjectMapper mapper = new ObjectMapper();

    private final ServerUtils server;

    private final MainCtrl mainCtrl;


    /**
     * constructor for the podium scene
     * @param server
     * @param mainCtrl
     */
    @Inject
    public PodiumCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setPodium(List list, Score score) {
        int place = 0;
        String str;
        List<Score> scores = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            scores.add(mapper.convertValue(list.get(i), Score.class));
        }
        scores.sort((x,y) -> Integer.compare(y.getScore(), x.getScore()));

        for(int i = 0; i < scores.size(); i ++) {
            if(scores.get(i).getUserName().equals(score.getUserName())) {
                place = i + 1;
            }
        }

        switch (place) {
            case 1:
                str = "st";
                break;
            case 2:
                str = "nd";
                break;
            case 3:
                str = "rd";
                break;
            default:
                str = "th";
                break;
        }

        message.setText("Congratulations! You Finished " + place + str + "!");

        if(list.size() > 0) {
            first.setText(String.valueOf(scores.get(0).getUserName()));
        }
        else {
            first.setText("");
        }
        if(list.size() > 1) {
            second.setText(String.valueOf(scores.get(1).getUserName()));
        }
        else {
            second.setText("");
        }
        if(list.size() > 2) {
            third.setText(String.valueOf(scores.get(2).getUserName()));
        }
        else {
            third.setText("");
        }
    }

    public void quitGame(){
        mainCtrl.showStartScreen();
    }
}
