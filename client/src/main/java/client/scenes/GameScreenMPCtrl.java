package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashSet;
import javafx.scene.image.ImageView;
//import java.util.ArrayList;
//import java.util.List;


public class GameScreenMPCtrl {
    @FXML
    public Text textGXQuestion;

    @FXML
    public Text textHMQuestion;

    @FXML
    public ImageView imageView1;

    @FXML
    public ImageView imageView2;

    @FXML
    public ImageView imageView3;

    @FXML
    public Label questionLabel;

    @FXML
    public Button QuitButton;

    @FXML
    public Button AnswerA;

    @FXML
    public Text Answer1;

    @FXML
    public Button AnswerB;

    @FXML
    public Text Answer2;

    @FXML
    public Button AnswerC;

    @FXML
    public Text Answer3;

    @FXML
    public Text qcounter;

    @FXML
    public Text countdown;

    @FXML
    public TextField guessAnswer;

    @FXML
    public ProgressBar time;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private String correctColor = "-fx-background-color: Green";

    private String incorrectColor = "-fx-background-color: Red";

    private Object currentQuestion;

    private double timer;

    private Timeline bar;

    /**
     * Instantiates a new Game screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public GameScreenMPCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

}