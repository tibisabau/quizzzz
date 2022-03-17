package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class InBetweenScreenCtrl {

    @FXML
    public ProgressBar progressbar;

    @FXML
    public Text qcounter2;

    @FXML
    public Text cscore;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private double progress;


    /**
     * Instantiates a new Game screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public InBetweenScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void showGameScreen(){
        mainCtrl.showGameScreen();

    }

    public synchronized void startTimer(int question, int score) {
        qcounter2.setText("Question " + question + " out of 20");
        cscore.setText("Score: " + score);
        progress = 0;
        progressbar.setStyle("-fx-accent: #1b5e20");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), ev ->{
            progress += 0.001;
            progressbar.setProgress(progress);
            if (progress >= 1){
                showGameScreen();
            }
        }));
        timeline.setCycleCount(1000);
        timeline.play();
    }
}