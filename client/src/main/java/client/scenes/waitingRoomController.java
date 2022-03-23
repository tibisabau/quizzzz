package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Score;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class waitingRoomController implements Initializable{


    @FXML
    public TableView table;

    @FXML
    public TableColumn<Score,String> id;

    @FXML
    public TableColumn<Score, String> username;

    private List<Score> players;



    private final ServerUtils server;

    private final MainCtrl mainCtrl;


    /**
     * A constructor for the leaderboardSceneCtrl class.
     * @param server
     * @param mainCtrl
     */
    @Inject
    public waitingRoomController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.players = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        id.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Score, String>,
                        ObservableValue<String>>() {
                    @Override public ObservableValue<String>
                    call(TableColumn.CellDataFeatures<Score, String> p) {
                        return new ReadOnlyObjectWrapper(
                                (table.getItems().indexOf(p.getValue()) + 1) + "");
                    }
                });
        id.setSortable(false);
        server.registerForUpdates(p -> {
            players.add(p);
            table.getItems().add(p);
        });
    }



    public void stop(){
        server.stop();
    }

    /**
     * Return to start scene.
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

}