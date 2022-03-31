package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Score;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class leaderboardSceneCtrl {

    @FXML
    public TableView table;

    @FXML
    public TableColumn<Score,String> rank;

    @FXML
    public TableColumn<Score, String> name;

    @FXML
    public TableColumn<Score, String> value;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private boolean isFirstQuestion = true;

    /**
     * A constructor for the leaderboardSceneCtrl class.
     * @param server
     * @param mainCtrl
     */
    @Inject
    public leaderboardSceneCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**
     * Fills the leaderboard with scores queried from the database.
     * @param isSinlgePlayer checks if the game is single player or not
     */
    public void load(boolean isSinlgePlayer){
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
        List<Score> scores = new ArrayList<>();
        if( isSinlgePlayer ) {
            scores = server.getTopScores();
        } else {
            List<Score> allScores = server.getMP();
            scores = removeDuplicates(allScores);
        }
        scores.sort((x,y) -> Integer.compare(y.getScore(), x.getScore()));
        ObservableList<Score> list = FXCollections.observableList(scores);
        table.setItems(list);
    }

    /**
     * Return to start scene.
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }


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

}
