package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.MostEnergyQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private int counter;
    private List<MostEnergyQuestion> questionList;
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

    /**
     * Instantiates a new Game screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public GameScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.counter = 20;
        this.questionList = new ArrayList<>();
    }

    /**
     * Selecting answer A
     */
    public void SelectAnswerA() throws InterruptedException {
        AnswerA.setStyle("-fx-background-color: #f6b26b");
        AnswerB.setDisable(true);
        Answer2.setDisable(true);
        Answer3.setDisable(true);
        AnswerC.setDisable(true);
        --counter;
        if(counter > 0) {
            //showLoadingPage  - TO BE IMPLEMENTED
            this.createTimer();

        }else{
            //showLeaderBoardScreen()  - TO BE IMPLEMENTED
        }
    }

    /**
     * Selecting answer B
     */
    public void SelectAnswerB() throws InterruptedException {
        AnswerB.setStyle("-fx-background-color: #f6b26b");
        AnswerA.setDisable(true);
        Answer1.setDisable(true);
        AnswerC.setDisable(true);
        Answer3.setDisable(true);
        --counter;
        if(counter > 0) {
            //showLoading() - TO BE IMPLEMENTED
            this.createTimer();
        }else{
            //showLeaderBoardScreen()  - TO BE IMPLEMENTED
        }
    }

    /**
     * Selecting answer C
     */
    public void SelectAnswerC() throws InterruptedException {
        AnswerC.setStyle("-fx-background-color: #f6b26b");
        AnswerB.setDisable(true);
        Answer2.setDisable(true);
        Answer1.setDisable(true);
        AnswerA.setDisable(true);
        --counter;
        if(counter > 0) {
            //showLoadingPage  - TO BE IMPLEMENTED
            this.createTimer();
        }else{
            //showLeaderBoardScreen()  - TO BE IMPLEMENTED
        }
    }

    /**
     * Change screen to StartScene
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

    public void setAnswer(){
        Answer1.setDisable(false);
        Answer2.setDisable(false);
        Answer3.setDisable(false);
        AnswerA.setDisable(false);
        AnswerB.setDisable(false);
        AnswerC.setDisable(false);
        AnswerA.setStyle("-fx-background-color: WHITE");
        AnswerB.setStyle("-fx-background-color: WHITE");
        AnswerC.setStyle("-fx-background-color: WHITE");
        MostEnergyQuestion question = server.getMEQuestion();
        questionList.add(question);
        String answerText1 = question.getFirstOption().toStringAnswer();
        String answerText2 = question.getSecondOption().toStringAnswer();
        String answerText3 = question.getThirdOption().toStringAnswer();
        Answer1.setText(answerText1);
        Answer2.setText(answerText2);
        Answer3.setText(answerText3);
    }

    public void createTimer(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                setAnswer();
            }
        };
        timer.schedule(timerTask,1500);

    }
}