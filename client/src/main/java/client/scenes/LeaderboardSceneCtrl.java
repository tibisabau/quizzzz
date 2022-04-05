package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import commons.Score;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class LeaderboardSceneCtrl {

    @FXML
    public Button playAgainButton;

    @FXML
    public Button homeButton;

    @FXML
    public TableView table;

    @FXML
    public TableColumn<Score,String> rank;

    @FXML
    public TableColumn<Score, String> name;

    @FXML
    public TableColumn<Score, String> value;

    private ObjectMapper mapper = new ObjectMapper();

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private final StartScreenCtrl startScreenCtrl;

    private boolean isFirstQuestion = true;

    /**
     * A constructor for the LeaderboardSceneCtrl class.
     * @param server
     * @param mainCtrl
     * @param startScreenCtrl
     */
    @Inject
    public LeaderboardSceneCtrl(ServerUtils server, MainCtrl mainCtrl,
                                StartScreenCtrl startScreenCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.startScreenCtrl = startScreenCtrl;
    }


    /**
     * Fills the leaderboard with scores queried from the database.
     * @param isSinglePlayer checks if the game is single player or not
     * @param showButton checks if the 'play again'
     * button should be displayed or not
     * @param l list of scores
     */
    public void load(boolean isSinglePlayer, boolean showButton, List l){
        name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        value.setCellValueFactory(new PropertyValueFactory<>("score"));
        rank.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Score, String>,
                        ObservableValue<String>>() {
                    @Override public ObservableValue<String>
                    call(TableColumn.CellDataFeatures<Score, String> p) {
                return new ReadOnlyObjectWrapper(
                        (table.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });

        rank.setSortable(false);
        try {
            table.getItems().remove(0, 10);
        } catch (Exception e){
        List<Score> scores = new ArrayList<>();
        if(isSinglePlayer) {
            List<Score> allScores = server.getTopScores();
            for(int i = 0; i < 10; i++){
                try{
                    scores.add(allScores.get(i));
                } catch (Exception b){
                    break;
                }
            }
        } else {
            for(int i = 0; i < l.size(); i++){
                scores.add(mapper.convertValue(l.get(i), Score.class));
            }
        }
        if(showButton == false) {
            playAgainButton.setVisible(false);
            playAgainButton.setDisable(true);
            homeButton.setVisible(false);
            homeButton.setDisable(true);

        }else {
            playAgainButton.setVisible(true);
            playAgainButton.setDisable(false);
            homeButton.setVisible(true);
            homeButton.setDisable(false);
        }


        scores.sort((x,y) -> Integer.compare(y.getScore(), x.getScore()));
        ObservableList<Score> list = FXCollections.observableList(scores);
        table.setItems(list);
        }
        
    }

    /**
     * Return to start scene.
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

    /**
     * Remove the duplicates from the list
     * @param scoreList
     * @return new list
     */
    public List<Score> removeDuplicates(List<Score> scoreList) {
        List<Score> result = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for(int i = scoreList.size() - 1 ; i >=0; i--) {
            if(!names.contains(scoreList.get(i).getUserName())) {
                names.add(scoreList.get(i).getUserName());
                result.add(scoreList.get(i));
            }
        }
        return result;
    }

    /**
     * Play again
     * @throws IOException
     */
    public void playAgain() throws IOException {
        mainCtrl.counter = 20;
        StartScreenCtrl.resetScore();
        startScreenCtrl.newGame();
        startScreenCtrl.goToGameScreen();
    }

}
