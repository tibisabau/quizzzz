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

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    public ProgressBar progressbar;

    @FXML
    public Text Qcounter2;

    double progress;

    public int counter;

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
        counter = 2;
    }
    public void showGameScreen(){
        mainCtrl.showGameScreen();

    }

    public synchronized void StartTimer() {
        Qcounter2.setText("Question " + counter + " out of 20");
        counter++;
        if (counter == 21){
            counter = 1;
        }
        progress = 0;
        progressbar.setStyle("-fx-accent: #1b5e20");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2), ev -> {
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