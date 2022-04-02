package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Score;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;

public class LeaderboardSceneCtrl {

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


    /**
     * A constructor for the LeaderboardSceneCtrl class.
     * @param server
     * @param mainCtrl
     */
    @Inject
    public LeaderboardSceneCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**
     * Fills the leaderboard with scores queried from the database.
     */
    public void load(){
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

        List<Score> scores = server.getTopScores();
        for(int i = 0; i < 10; i++){
            table.getItems().add(scores.get(i));
        }
    }

    /**
     * Return to start scene.
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

}
