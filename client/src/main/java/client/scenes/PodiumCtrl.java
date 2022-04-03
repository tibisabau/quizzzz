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

    public void setPodium(List list) {
        System.out.println(list.size());
        List<Score> scores = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            scores.add(mapper.convertValue(list.get(i), Score.class));
        }
        scores.sort((x,y) -> Integer.compare(y.getScore(), x.getScore()));
        try{
            first.setText(String.valueOf(scores.get(0).getUserName()));
            second.setText(String.valueOf(scores.get(1).getUserName()));
            third.setText(String.valueOf(scores.get(2).getUserName()));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
