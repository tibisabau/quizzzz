package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Score;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class leaderboardSceneCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    public Button QuitButton;

    @FXML
    public TableView table;

    @FXML
    public TableColumn<Score, Integer> rank;

    @FXML
    public TableColumn<Score, String> name;

    @FXML
    public TableColumn<Score, String> value;


    @Inject
    public leaderboardSceneCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void load(){
//        for(int i = 0; i < 15; i++){
//            Score score = new Score("Player " + i, i);
//            server.addScore(score);
//        }
        name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        value.setCellValueFactory(new PropertyValueFactory<>("score"));
//        Score score = new Score("name",2137);
//        table.getItems().add(score);
        List<Score> scores = server.getTopScores();
        for(int i = 0; i < scores.size(); i++){
            table.getItems().add((scores.get(i)));
        }


    }
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

}
