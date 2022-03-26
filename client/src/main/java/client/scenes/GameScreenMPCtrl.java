package client.scenes;

import client.Main;
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

import java.awt.desktop.QuitEvent;
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

    private Game game;

    private GameScreenCtrl gameScreenCtrl;

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


    public void initialize(){
        //Register for messages and call getTypeOfQuestion
        //
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void getTypeOfQuestion(){
        currentQuestion = game.getNextQuestion();
        if (currentQuestion instanceof MostEnergyQuestion){
            MostEnergyQuestion question = (MostEnergyQuestion) currentQuestion;
            setMeQuestion(question);
        } else if (currentQuestion instanceof HowMuchQuestion){
            HowMuchQuestion question = (HowMuchQuestion) currentQuestion;
            setHmQuestion(question);
        } else if (currentQuestion instanceof GuessXQuestion){
            GuessXQuestion question = (GuessXQuestion) currentQuestion;
            setGxQuestion(question);
        }
        startTimer(currentQuestion);
    }

    public void setMeQuestion(MostEnergyQuestion question) {
        setImagesME(question);
        Answer1.setText(question.getFirstOption().toStringAnswer());
        Answer2.setText(question.getSecondOption().toStringAnswer());
        Answer3.setText(question.getThirdOption().toStringAnswer());
        mainCtrl.showMEQuestionMP();
    }

    public void setHmQuestion(HowMuchQuestion question) {
        setImagesHQ(question);
        Answer1.setText(question.getFirstOption().toStringAnswer());
        Answer2.setText(question.getSecondOption().toStringAnswer());
        Answer3.setText(question.getThirdOption().toStringAnswer());
        mainCtrl.showHMQuestionMP();
    }

    public void setGxQuestion(GuessXQuestion question) {
        setImagesGX(question);
        textGXQuestion.setText("- "+ question.getCorrectOption().getTitle()+ " -");
        guessAnswer.setDisable(false);
        guessAnswer.clear();
        mainCtrl.showGXQuestionMP();
    }

    public void setImagesME(MostEnergyQuestion question){
        String path1 = question.getFirstOption().getImagePath();
        String path2 = question.getSecondOption().getImagePath();
        String path3 = question.getThirdOption().getImagePath();
        imageView1.setImage(mainCtrl.getImage(path1));
        imageView2.setImage(mainCtrl.getImage(path2));
        imageView3.setImage(mainCtrl.getImage(path3));
    }

    public void setImagesHQ(HowMuchQuestion question){
        String path2 = question.getSecondOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
    }

    public void setImagesGX(GuessXQuestion question){
        String path2 = question.getCorrectOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
    }

    /**
     * Starts the time bar
     * @param q
     */
    public void startTimer(Object q){
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
                if(q instanceof MostEnergyQuestion ||
                        q instanceof HowMuchQuestion) {
                    disableAnswers();
                    showAnswers();
                }
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

    public void showAnswers() {
        AnswerA.setStyle(incorrectColor);
        AnswerB.setStyle(incorrectColor);
        AnswerC.setStyle(incorrectColor);
        if (gameScreenCtrl.answerCorrect(currentQuestion, 1)) {
            AnswerA.setStyle(correctColor);
        } else if (gameScreenCtrl.answerCorrect(currentQuestion, 2)) {
            AnswerB.setStyle(correctColor);
        } else {
            AnswerC.setStyle(correctColor);
        }
    }

    /**
     * Selecting answer A
     */
    public void selectAnswerA() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion, 1);
    }

    /**
     * Selecting answer B
     */
    public void selectAnswerB() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion,2);
    }

    /**
     * Selecting answer C
     */
    public void selectAnswerC() throws InterruptedException {
        stopTime();
        disableAnswers();
        answerPoints(currentQuestion, 3);
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
     * confirms the answer to the "Guess X" question
     * checks if the client can load a different
     * question
     */
    public void ok() {
        stopTime();
        try{
            answerPoints(currentQuestion, Integer.parseInt(guessAnswer.getText()));}
        catch (Exception e){
            answerPoints(currentQuestion, 0);
        }
    }

    /**
     * Gives points if correct answer is given
     * @param question question to check if correct
     * @param answer answer number from 1 to 3, 1 is for a, 2 for b, 3 for c
     *
     */
    public void answerPoints(Object question, int answer){
        double multiplier = 0.5 + (2 * timer);
        int extraPoints = (int) Math.round(100 * multiplier);

        if(gameScreenCtrl.answerCorrect(question,answer)) {
            game.incrementScore(extraPoints);
        }
        showAnswers();
    }

    /**
     * Stops the time bar
     */
    public void stopTime(){
        if (bar != null){
            bar.stop();
        }
    }

    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }
}