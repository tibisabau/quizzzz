package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Activity;
import commons.MostEnergyQuestion;
import commons.Score;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameScreenCtrl {
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
    private Label scoreId;

    @FXML
    private Label questionNumber;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private int counter;

    private List<MostEnergyQuestion> questionList;

    private String correctColor = "-fx-background-color: Green";

    private String incorrectColor = "-fx-background-color: Red";

    private MostEnergyQuestion currentQuestion;





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
    public void selectAnswerA() throws InterruptedException {
        Answer1.setDisable(true);
        AnswerA.setDisable(true);
        AnswerB.setDisable(true);
        Answer2.setDisable(true);
        Answer3.setDisable(true);
        AnswerC.setDisable(true);
        answerPoints(currentQuestion, 1);
        nextQuestion();
    }

    /**
     * Selecting answer B
     */
    public void selectAnswerB() throws InterruptedException {
        Answer2.setDisable(true);
        AnswerB.setDisable(true);
        AnswerA.setDisable(true);
        Answer1.setDisable(true);
        AnswerC.setDisable(true);
        Answer3.setDisable(true);
        answerPoints(currentQuestion,2);
        nextQuestion();
    }

    /**
     * Selecting answer C
     */
    public void selectAnswerC() throws InterruptedException {
        AnswerC.setDisable(true);
        AnswerB.setDisable(true);
        Answer2.setDisable(true);
        Answer1.setDisable(true);
        AnswerA.setDisable(true);
        Answer3.setDisable(true);
        answerPoints(currentQuestion, 3 );
        nextQuestion();
    }

    /**
     * Shows answers green for the correct ones and red for incorrect
     */

    public void showAnswers(){
        if(answerCorrect(currentQuestion, 1 )){
            AnswerA.setStyle(correctColor);
            AnswerB.setStyle(incorrectColor);
            AnswerC.setStyle(incorrectColor);
        }
        else if(answerCorrect(currentQuestion, 2)){
            AnswerA.setStyle(incorrectColor);
            AnswerB.setStyle(correctColor);
            AnswerC.setStyle(incorrectColor);
        }
        else {
            AnswerA.setStyle(incorrectColor);
            AnswerB.setStyle(incorrectColor);
            AnswerC.setStyle(correctColor);
        }
    }

    /**
     * Change screen to StartScene
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

    /**
     * Sets the answer for a new question
     * and adds this question to the question list.
     */
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
        currentQuestion = server.getMEQuestion();
        questionList.add(currentQuestion);
        String answerText1 = currentQuestion.getFirstOption().toStringAnswer();
        String answerText2 = currentQuestion.getSecondOption().toStringAnswer();
        String answerText3 = currentQuestion.getThirdOption().toStringAnswer();
        Answer1.setText(answerText1);
        Answer2.setText(answerText2);
        Answer3.setText(answerText3);


    }

    /**
     * It creates a pause between 2 questions
     */

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


    /**
     * Gives points if correct answer is given
     * @param question question to check if correct
     * @param answer answer number from 1 to 3, 1 is for a, 2 for b, 3 for c
     *
     */

    public void answerPoints(MostEnergyQuestion question, int answer){
        Score score = StartScreenCtrl.getOwnScore();
        if(answerCorrect(question,answer)) {
            score.setScore(score.getScore() + 100);
        }
        showAnswers();
        scoreId.setText(Integer.toString(score.getScore()));
    }


    /**
     *
     * @param question question to check if correct
     * @param answer Answer given
     * @return boolean if the answer is correct
     */
    public boolean answerCorrect (MostEnergyQuestion question, int answer){
        Activity correct = question.getCorrectOption();
        switch (answer){
            case 1:
                if (question.getFirstOption().equals(correct)){
                    return true;
                }
                break;
            case 2:
                if (question.getSecondOption().equals(correct)){
                    return true;
                }
                break;
            case 3:
                if (question.getThirdOption().equals(correct)){
                    return true;
                }
                break;
        }
        return false;
    }

    public void setCounter(int value){
        this.counter = value;
    }
    /**
     * Checks if new question needs te be show otherwise go to leaderboard.
     */
    public void nextQuestion (){
        --counter;
        if(counter > 0) {
            //showLoadingPage  - TO BE IMPLEMENTED
            questionNumber.setText(Integer.toString(21-counter)+"/20");
            this.createTimer();
        }else{
            //showLeaderBoardScreen()  - TO BE IMPLEMENTED
            server.updateScore(StartScreenCtrl.getOwnScore());
            mainCtrl.showLeaderboard();
        }
    }

}
