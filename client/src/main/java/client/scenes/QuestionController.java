package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.GuessXQuestion;
import commons.HowMuchQuestion;
import commons.MostEnergyQuestion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Set;


public class QuestionController {
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
    public TextField guessAnswer;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private int counter;

    private Set<Object> questionList;

    /**
     * Instantiates a new Game screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public QuestionController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.counter = 20;
        questionList = new HashSet<>();
    }

    /**
     * Selecting answer A
     */
    public void selectAnswerA() throws InterruptedException {
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
            mainCtrl.showLeaderboard();
        }
    }

    /**
     * Selecting answer B
     */
    public void selectAnswerB() throws InterruptedException {
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
            mainCtrl.showLeaderboard();
        }
    }

    /**
     * Selecting answer C
     */
    public void selectAnswerC() throws InterruptedException {
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
            mainCtrl.showLeaderboard();
        }
    }

    /**
     * Change screen to StartScene
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

    /**
     * refresh the answers for the multiple choice questions
     * @param questionType
     */
    public void setAnswer(int questionType){
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
    }

    /**
     * client adds a "Which activity takes more energy" question
     * from the server to the question list
     */
    public void createMEQuestion() {
        MostEnergyQuestion question = server.getMEQuestion();
        while(questionList.contains(question)) {
            question = server.getMEQuestion();
        }
        questionList.add(question);
        Answer1.setText(question.getFirstOption().toStringAnswer());
        Answer2.setText(question.getSecondOption().toStringAnswer());
        Answer3.setText(question.getThirdOption().toStringAnswer());
    }

    /**
     * client adds a "How much energy does it take" question
     * from the server to the question list
     */
    public void createHMQuestion() {
        HowMuchQuestion question = server.getHMQuestion();
        while(questionList.contains(question)) {
            question = server.getHMQuestion();
        }
        questionList.add(question);
        Answer1.setText(String.valueOf
                (question.getFirstOption().getConsumptionInWh()));
        Answer2.setText(String.valueOf
                (question.getSecondOption().getConsumptionInWh()));
        Answer3.setText(String.valueOf
                (question.getThirdOption().getConsumptionInWh()));
    }

    /**
     * client adds a "Guess how much energy this activity takes" question
     * from the server to the question list
     */
    public void createGXQuestion() {
        GuessXQuestion question = server.getGXQuestion();

    }

    /**
     *@param e the e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            default:
                break;
        }
    }

    public void ok() {
        --counter;
        if(counter > 0) {
            //showLoadingPage  - TO BE IMPLEMENTED
            this.createTimer();
        }else{
            //showLeaderBoardScreen()  - TO BE IMPLEMENTED
            mainCtrl.showLeaderboard();
        }
    }

    /**
     * creates a 1.5 sec delay in-between questions
     */
    public void createTimer(){
        Timeline timeline = new Timeline
                (new KeyFrame(Duration.seconds(1), ev -> {
            changeQuestion();
        }));
        timeline.play();

    }

    /**
     * resets the number of questions to 20 for each game
     * @param value
     */
    public void setCounter(int value){
        this.counter = value;
    }

    public void changeQuestion() {
        int questionType = server.getQuestionType();
        if (questionType == 1) {
            mainCtrl.showMEQuestion(questionType);
        } else if (questionType == 2) {
            mainCtrl.showHMQuestion(questionType);
        } else {
            mainCtrl.showGXQuestion();
        }
    }
}