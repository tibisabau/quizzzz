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

//import javax.swing.text.html.parser.Entity;
//import java.util.ArrayList;
//import java.util.List;


/**
 * The type Game screen ctrl.
 */
public class GameScreenCtrl {
    /**
     * The Score text.
     */
    @FXML
    public Text scoreText;

    /**
     * The Text gx question.
     */
    @FXML
    public Text textGXQuestion;

    /**
     * The Text hm question.
     */
    @FXML
    public Text textHMQuestion;

    /**
     * The Image view 1.
     */
    @FXML
    public ImageView imageView1;

    /**
     * The Image view 2.
     */
    @FXML
    public ImageView imageView2;

    /**
     * The Image view 3.
     */
    @FXML
    public ImageView imageView3;

    /**
     * The Question label.
     */
    @FXML
    public Label questionLabel;

    /**
     * The Quit button.
     */
    @FXML
    public Button QuitButton;

    /**
     * The Answer a.
     */
    @FXML
    public Button AnswerA;

    /**
     * The Answer 1.
     */
    @FXML
    public Text Answer1;

    /**
     * The Answer b.
     */
    @FXML
    public Button AnswerB;

    /**
     * The Answer 2.
     */
    @FXML
    public Text Answer2;

    /**
     * The Answer c.
     */
    @FXML
    public Button AnswerC;

    /**
     * The Answer 3.
     */
    @FXML
    public Text Answer3;

    /**
     * The Qcounter.
     */
    @FXML
    public Text qcounter;

    /**
     * The Countdown.
     */
    @FXML
    public Text countdown;

    /**
     * The Guess answer.
     */
    @FXML
    public TextField guessAnswer;

    /**
     * The Time.
     */
    @FXML
    public ProgressBar time;

    @FXML
    public Label scoreDisplay;

    @FXML
    public Label correctAnswerQX;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private String correctColor = "-fx-background-color: Green";

    private String incorrectColor = "-fx-background-color: Red";

    private Object currentQuestion;

    private double timer;

    private Timeline bar;

    private boolean answerIsCorrect;

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
    }

    /**
     * Selecting answer A
     *
     * @throws InterruptedException the interrupted exception
     */
    public void selectAnswerA() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion, 1);
        --mainCtrl.counter;
        this.createTimer();
    }

    /**
     * Selecting answer B
     *
     * @throws InterruptedException the interrupted exception
     */
    public void selectAnswerB() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion,2);
        --mainCtrl.counter;
        this.createTimer();
    }

    /**
     * Selecting answer C
     *
     * @throws InterruptedException the interrupted exception
     */
    public void selectAnswerC() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion, 3);
        --mainCtrl.counter;
        this.createTimer();
    }

    /**
     * Shows answers green for the correct ones and red for incorrect
     */
    public void showAnswers(){
        if (currentQuestion instanceof GuessXQuestion){
            if (answerIsCorrect) {
                guessAnswer.setStyle(correctColor);
            }
            else {
                guessAnswer.setStyle(incorrectColor);
                //extra 2 lines because of checkstyle not above 80 character
                GuessXQuestion cor = (GuessXQuestion)currentQuestion;
                long corText = cor.getCorrectOption().getConsumptionInWh();
                correctAnswerQX.setText("Correct answer: " + corText);
            }
        }
        else {
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
        }}
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
     *
     * @param questionType the question type
     */
    public void setAnswer(int questionType){
        Score score = StartScreenCtrl.getOwnScore();
        setScoreText(score.getScore());
        Answer1.setDisable(false);
        Answer2.setDisable(false);
        Answer3.setDisable(false);
        AnswerA.setDisable(false);
        AnswerB.setDisable(false);
        AnswerC.setDisable(false);
        AnswerA.setStyle("-fx-background-color: WHITE");
        AnswerB.setStyle("-fx-background-color: WHITE");
        AnswerC.setStyle("-fx-background-color: WHITE");
        if(questionType == 1) {
            createMEQuestion();
        }

        else {
            createHMQuestion();
        }
        startTimer();
        int x = 21 - mainCtrl.counter;
        qcounter.setText("Question: " + x + "/20");

    }

    /**
     * client adds a "Which activity takes more energy" question
     * from the server to the question list
     */
    public void createMEQuestion() {
        currentQuestion = server.getMEQuestion();
        while(mainCtrl.questionList.contains(currentQuestion)) {
            currentQuestion = server.getMEQuestion();
        }
        setImagesME((MostEnergyQuestion) currentQuestion);
        mainCtrl.questionList.add(currentQuestion);
        Answer1.setText(((MostEnergyQuestion)currentQuestion).
                getFirstOption().toStringAnswer());
        Answer2.setText(((MostEnergyQuestion)currentQuestion).
                getSecondOption().toStringAnswer());
        Answer3.setText(((MostEnergyQuestion)currentQuestion).
                getThirdOption().toStringAnswer());
    }

    /**
     * client adds a "How much energy does it take" question
     * from the server to the question list
     */
    public void createHMQuestion() {
        currentQuestion = server.getHMQuestion();
        while(mainCtrl.questionList.contains(currentQuestion)) {
            currentQuestion = server.getHMQuestion();
        }
        setImagesHQ((HowMuchQuestion) currentQuestion);
        textHMQuestion.setText("- "+ ((HowMuchQuestion) currentQuestion)
                .getCorrectOption().getTitle()+ " -");
        mainCtrl.questionList.add(currentQuestion);
        Answer1.setText(String.valueOf
                (((HowMuchQuestion)currentQuestion).
                        getFirstOption().getConsumptionInWh()));
        Answer2.setText(String.valueOf
                (((HowMuchQuestion)currentQuestion).
                        getSecondOption().getConsumptionInWh()));
        Answer3.setText(String.valueOf
                (((HowMuchQuestion)currentQuestion).
                        getThirdOption().getConsumptionInWh()));
    }

    /**
     * client adds a "Guess how much energy this activity takes" question
     * from the server to the question list
     */
    public void createGXQuestion() {
        currentQuestion = server.getGXQuestion();
        while(mainCtrl.questionList.contains(currentQuestion)) {
            currentQuestion = server.getGXQuestion();
        }

        setImagesGX((GuessXQuestion) currentQuestion);
        textGXQuestion.setText("- "+ ((GuessXQuestion) currentQuestion)
                .getCorrectOption().getTitle()+ " -");
        mainCtrl.questionList.add(currentQuestion);
        guessAnswer.setDisable(false);
        guessAnswer.clear();
        correctAnswerQX.setText("");
        startTimer();
        int x = 21 - mainCtrl.counter;
        guessAnswer.setStyle("-fx-background-color: WHITE");
        qcounter.setText("Question: " + x + "/20");
        Score score = StartScreenCtrl.getOwnScore();
        setScoreText(score.getScore());

    }

    /**
     * pressing ENTER submits the answer to
     * the "Guess X" question type
     *
     * @param e the e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
            {guessAnswer.setDisable(true);
            ok();}
            break;
            default:
                break;
        }
    }

    /**
     * Set images me.
     *
     * @param question the question
     */
    public void setImagesME(MostEnergyQuestion question){
        String path1 = question.getFirstOption().getImagePath();
        String path2 = question.getSecondOption().getImagePath();
        String path3 = question.getThirdOption().getImagePath();
        imageView1.setImage(mainCtrl.getImage(path1));
        imageView2.setImage(mainCtrl.getImage(path2));
        imageView3.setImage(mainCtrl.getImage(path3));
    }

    /**
     * Set images hq.
     *
     * @param question the question
     */
    public void setImagesHQ(HowMuchQuestion question){
        String path2 = question.getSecondOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
    }

    /**
     * Set images gx.
     *
     * @param question the question
     */
    public void setImagesGX(GuessXQuestion question){
        String path2 = question.getCorrectOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
    }


    /**
     * confirms the answer to the "Guess X" question
     * checks if the client can load a different
     * question
     */
    public void ok() {
        --mainCtrl.counter;
        stopTime();
        try{
        answerPoints(currentQuestion, Integer.parseInt(guessAnswer.getText()));}
        catch (Exception e){
            answerPoints(currentQuestion, 0);
        }

        if(mainCtrl.counter > 0) {

            this.createTimer();
        }else{
            server.updateScore(StartScreenCtrl.getOwnScore());
            mainCtrl.showLeaderboard();
        }
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
        int scoreAmount = StartScreenCtrl.getOwnScore().getScore();
        scoreDisplay.setText(Integer.toString(scoreAmount));
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
                if(currentQuestion instanceof MostEnergyQuestion ||
                        currentQuestion instanceof HowMuchQuestion) {
                    disableAnswers();
                }
                else {
                    guessAnswer.setDisable(true);
                }
                showAnswers();
                mainCtrl.counter--;
                createTimer();
            }
        }));
        bar.setCycleCount(1000);
        bar.play();
    }

    /**
     * disable multiple choice answers
     */
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
                    if (mainCtrl.counter > 0) {
                        mainCtrl.showInBetweenScreen(21- mainCtrl.counter,
                                score.getScore());
                    } else {
                        server.updateScore(score);
                        mainCtrl.showLeaderboard();
                    }
        }));
        wait.play();

    }


    /**
     * Gives points if correct answer is given
     *
     * @param question question to check if correct
     * @param answer   answer number from 1 to 3, 1 is for a, 2 for b, 3 for c
     */
    public void answerPoints(Object question, int answer){
        Score score = StartScreenCtrl.getOwnScore();
        setScoreText(score.getScore());
        double multiplier = 0.5 + (2 * timer);
        int extraPoints = (int) Math.round(100 * multiplier);
        answerIsCorrect = false;
        if(answerCorrect(question,answer)) {
            answerIsCorrect = true;
            score.setScore(score.getScore() + extraPoints);
        }
        showAnswers();
    }


    /**
     * Answer correct boolean.
     *
     * @param question question to check if correct
     * @param answer   Answer given
     * @return boolean if the answer is correct
     */
    public boolean answerCorrect (Object question, int answer){

        if(question instanceof MostEnergyQuestion) {
            return MECorrectAnswer(question, answer);
        }
        else
            if(question instanceof HowMuchQuestion) {
                return HMCorrectAnswer(question, answer);
            }
            else {
                return  GXCorrectAnswer(question, answer);
            }
    }

    /**
     * check for correctness for MEQuestion
     *
     * @param question the question
     * @param answer   the answer
     * @return whether the answer is correct
     */
    public boolean MECorrectAnswer(Object question, int answer) {
        Activity correct = ((MostEnergyQuestion)question).getCorrectOption();
        switch (answer){
            case 1:
                if (((MostEnergyQuestion)question).getFirstOption().
                        equals(correct)){
                    return true;
                }
                break;
            case 2:
                if (((MostEnergyQuestion)question).getSecondOption().
                        equals(correct)){
                    return true;
                }
                break;
            case 3:
                if (((MostEnergyQuestion)question).getThirdOption().
                        equals(correct)){
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Check for correctness HMQuestion
     *
     * @param question the question
     * @param answer   the answer
     * @return whether the answer is correct
     */
    public boolean HMCorrectAnswer(Object question, int answer) {
        Activity correct = ((HowMuchQuestion)question).getCorrectOption();
        switch (answer){
            case 1:
                if (((HowMuchQuestion)question).getFirstOption().
                        equals(correct)){
                    return true;
                }
                break;
            case 2:
                if (((HowMuchQuestion)question).getSecondOption().
                        equals(correct)){
                    return true;
                }
                break;
            case 3:
                if (((HowMuchQuestion)question).getThirdOption().
                        equals(correct)){
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * checks if answer given is correct
     * @param question current question
     * @param answer answer given
     * @return if answer is  correct
     */
    public boolean GXCorrectAnswer(Object question, int answer){
        Activity cor = ((GuessXQuestion) question).getCorrectOption();
        int correctAmount = (int)cor.getConsumptionInWh();
        if ((correctAmount * 1.1) > answer && (correctAmount * 0.9) < answer){
            return true;
        }
        return false;
    }

    /**
     * resets the number of questions to 20 for each game
     *
     * @param value the value
     */
    public void setCounter(int value){
        mainCtrl.counter = value;
    }

    /**
     * resets the current question list
     * to start a new single player game
     */
    public void setQuestionList() {
        mainCtrl.questionList = new HashSet<>();
    }

    /**
     * Sets score text.
     *
     * @param score the score
     */
    public void setScoreText(int score) {
        scoreText.setText("Score : " + String.valueOf(score));
    }


}
