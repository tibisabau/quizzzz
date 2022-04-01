package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Game;
import commons.Score;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.List;

public class WaitingRoomCtrl {


    @FXML
    public TableView table;

    @FXML
    public TableColumn<Score,String> id;

    @FXML
    public TableColumn<Score, String> username;

    @FXML
    public Button quitButton;

    @FXML
    public  Button startButton;

    private List<Score> players;

    private Score score;

    private Game game;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;


    /**
     * A constructor for the LeaderboardSceneCtrl class.
     * @param server
     * @param mainCtrl
     */
    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.players = new ArrayList<>();
    }

    /**
     * Setter for the score
     * @param score
     */
    public void setScore(Score score){
        this.score = score;
    }

    /**
     * Subscribing the to the server for the lobby and starting the game
     */
    public void load() {
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        id.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Score, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String>
                    call(TableColumn.CellDataFeatures<Score, String> p) {
                        return new ReadOnlyObjectWrapper(
                                (table.getItems().
                                        indexOf(p.getValue()) + 1) + "");
                    }
                });
        id.setSortable(false);
        players.add(score);


        server.registerForUpdates(p -> {
            players = p;
            table.getItems().clear();
            for (int i = 0; i < players.size(); i++) {
                Score score = players.get(i);
                table.getItems().add(score);
            }
        });
        server.joinGame(players);

            server.registerForMessages("/topic/game", Game.class, game -> {
                if(this.game == null)
                {   
                    this.game = game;
                    this.game.updateScore(this.score);
                    quitButton.setDisable(true);
                    startButton.setDisable(true);
                    Platform.runLater(() -> mainCtrl.showMpGameScreen(game));
                }
            });
    }

    /**
     * Stating a new multiplayer game
     */
    public void startGame(){
        server.send("/app/game", "hello from the client");
    }

    /**
     * Getter for the game
     * @return game
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * When someone leaves the lobby
     */
    public void stop(){
        server.stop();
    }

    /**
     * Return to start scene.
     */
    public void goToStartScene(){
        players.remove(score);
        server.quitGame(players);
        this.players = new ArrayList<>();
        server.stop();
        server.wsDisconnect();
        mainCtrl.showStartScreen();
    }

}