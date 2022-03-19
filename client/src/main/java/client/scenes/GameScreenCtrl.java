package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Activity;
import commons.MostEnergyQuestion;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import commons.Score;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;



public class GameScreenCtrl {
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
    public Text Qcounter;

    @FXML
    public Text countdown;

    @FXML
    public ProgressBar time;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private int counter;

    private List<MostEnergyQuestion> questionList;

    private String correctColor = "-fx-background-color: Green";

    private String incorrectColor = "-fx-background-color: Red";

    private MostEnergyQuestion currentQuestion;

    private double timer;

    private Timeline bar;

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
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion, 1);
        --counter;
        this.createTimer();
    }

    /**
     * Selecting answer B
     */
    public void selectAnswerB() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion,2);
        --counter;
        this.createTimer();
    }

    /**
     * Selecting answer C
     */
    public void selectAnswerC() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion, 3);
        --counter;
        this.createTimer();
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
        stopTime();
        mainCtrl.showStartScreen();
    }


    /**
     * Sets the answer for a new question
     * and adds this question to the question list.
     */
    public void setAnswer() {
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
        setImages(currentQuestion);
        String answerText1 = currentQuestion.getFirstOption().toStringAnswer();
        String answerText2 = currentQuestion.getSecondOption().toStringAnswer();
        String answerText3 = currentQuestion.getThirdOption().toStringAnswer();
        Answer1.setText(answerText1);
        Answer2.setText(answerText2);
        Answer3.setText(answerText3);
        startTimer();
        int x = 21 - counter;
        Qcounter.setText("Question: " + x + "/20");

    }

    public void setImages(MostEnergyQuestion question){
        String path1 = question.getFirstOption().getImagePath();
        String path2 = question.getSecondOption().getImagePath();
        String path3 = question.getThirdOption().getImagePath();
        imageView1.setImage(mainCtrl.getImage(path1));
        imageView2.setImage(mainCtrl.getImage(path2));
        imageView3.setImage(mainCtrl.getImage(path3));
    }


    /**
     * Stops the time bar
     */
    public void stopTime(){
        if (bar != null){
            bar.stop();
        }
    }

    /**
     * Starts the time bar
     */
    public void startTimer(){
        time.setStyle("-fx-accent: #00FF01");
        timer = 1;
        bar = new Timeline(new KeyFrame(Duration.millis(8), ev ->{
            timer -= 0.001;
            time.setProgress(timer);
            countdown.setText(String.valueOf((int) Math.round(timer*10)));
            if (timer < 0.8){
                time.setStyle("-fx-accent: #74FF00");
            }
            if (timer < 0.7){
                time.setStyle("-fx-accent: #81FE00");
            }
            if (timer < 0.6){
                time.setStyle("-fx-accent: #D6FE01");
            }
            if (timer < 0.5){
                time.setStyle("-fx-accent: #FFEB01");
            }
            if (timer < 0.4){
                time.setStyle("-fx-accent: #FFCB00");
            }
            if (timer < 0.3){
                time.setStyle("-fx-accent: #FD5C02");
            }
            if (timer < 0.2){
                time.setStyle("-fx-accent: #FE5600");
            }
            if (timer < 0.1){
                time.setStyle("-fx-accent: #FF0100");
            }
            if (timer <= 0.002){
                bar.stop();
                disableAnswers();
                showAnswers();
                counter--;
                createTimer();
            }
        }));
        bar.setCycleCount(1000);
        bar.play();
    }

    private void disableAnswers() {
        Answer1.setDisable(true);
        AnswerA.setDisable(true);
        AnswerB.setDisable(true);
        Answer2.setDisable(true);
        Answer3.setDisable(true);
        AnswerC.setDisable(true);
    }

    /**
     * It creates a pause between 2 questions
     */

    public void createTimer(){
        Score score = StartScreenCtrl.getOwnScore();
        Timeline wait = new Timeline
                (new KeyFrame(Duration.seconds(1.5), ev -> {
                    if (counter > 0) {
                        mainCtrl.showInBetweenScreen(21-counter,
                                score.getScore());
                    } else {
                        mainCtrl.showLeaderboard();
                    }
        }));
        wait.play();

    }


    /**
     * Gives points if correct answer is given
     * @param question question to check if correct
     * @param answer answer number from 1 to 3, 1 is for a, 2 for b, 3 for c
     *
     */

    public void answerPoints(MostEnergyQuestion question, int answer){
        Score score = StartScreenCtrl.getOwnScore();
        double multiplier = 0.5 + (2 * timer);
        int extraPoints = (int) Math.round(100 * multiplier);

        if(answerCorrect(question,answer)) {
            score.setScore(score.getScore() + extraPoints);
        }
        showAnswers();
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


}
