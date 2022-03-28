package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import commons.*;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.scene.image.ImageView;


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
    public Text ScoreText;

    @FXML
    public TextField guessAnswer;

    @FXML
    public ProgressBar time;

    @FXML
    public ImageView Emoji1;

    @FXML
    public ImageView Emoji2;

    @FXML
    public ImageView Emoji3;

    @FXML
    public ImageView testEmoji;

    @FXML
    public ImageView Pic1;

    @FXML
    public ImageView Pic2;

    @FXML
    public ImageView Pic3;

    @FXML
    public ImageView Pic4;

    @FXML
    public ImageView Pic5;

    @FXML
    public ImageView Pic6;

    @FXML
    public Label User1;

    @FXML
    public Label User2;

    @FXML
    public Label User3;

    @FXML
    public Label User4;

    @FXML
    public Label User5;

    @FXML
    public Label User6;

    @FXML
    private ImageView EmojiMenuPic;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private String correctColor = "-fx-background-color: Green";

    private String incorrectColor = "-fx-background-color: Red";

    private Object currentQuestion;

    private double timer;

    private Timeline bar;

    private Game game;

    private GameScreenCtrl gameScreenCtrl;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Instantiates a new Game screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     * @param gameScreenCtrl the main ctrl
     */
    @Inject
    public GameScreenMPCtrl(ServerUtils server, MainCtrl mainCtrl,
                            GameScreenCtrl gameScreenCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.gameScreenCtrl = gameScreenCtrl;

    }


    public void initialize(){
        //Register for messages and call getTypeOfQuestion
        server.registerForMessages("/topic/nextQuestion", Integer.class, id -> {
            if(id == this.game.getID()){
                Platform.runLater(() -> getTypeOfQuestion());
            }
        });

        EmojiMenuPic.setImage(mainCtrl.getEmoji("emoji1.png"));
        Emoji1.setImage(mainCtrl.getEmoji("emoji1.png"));
        Emoji2.setImage(mainCtrl.getEmoji("emoji2.png"));
        Emoji3.setImage(mainCtrl.getEmoji("emoji3.png"));
    }

    public void test(){
        getTypeOfQuestion();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void getTypeOfQuestion(){
        Boolean found = false;
        currentQuestion = game.getNextQuestion();
        MostEnergyQuestion question = mapper.convertValue(currentQuestion,
                MostEnergyQuestion.class);
        if (question.getIdentity() != null){
            currentQuestion = question;
            found = true;
            mainCtrl.showMEQuestionMP(currentQuestion);
        }
        if (!found){
            HowMuchQuestion question2 = mapper.convertValue(currentQuestion,
                    HowMuchQuestion.class);
            if (question.getSecondOption() != null){
                currentQuestion = question2;
                mainCtrl.showHMQuestionMP(currentQuestion);
                found = true;
            }
        }
        if (!found){
            GuessXQuestion question2 = mapper.convertValue(currentQuestion,
                    GuessXQuestion.class);
            currentQuestion = question2;
            mainCtrl.showGXQuestionMP(currentQuestion);
        }
    }

    public void setMeQuestion() {
        resetStage();
        MostEnergyQuestion question = (MostEnergyQuestion) currentQuestion;
        setImagesME(question);
        Answer1.setText(question.getFirstOption().toStringAnswer());
        Answer2.setText(question.getSecondOption().toStringAnswer());
        Answer3.setText(question.getThirdOption().toStringAnswer());

    }

    public void setHmQuestion() {
        resetStage();
        HowMuchQuestion question = (HowMuchQuestion) currentQuestion;
        setImagesHQ(question);
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

    public void resetStage(){
        qcounter.setText("Question: " + game.getCounter() + "/20");
        ScoreText.setText("Score : " + game.getUser().getScore());
        Answer1.setDisable(false);
        Answer2.setDisable(false);
        Answer3.setDisable(false);
        AnswerA.setDisable(false);
        AnswerB.setDisable(false);
        AnswerC.setDisable(false);
        AnswerA.setStyle("-fx-background-color: WHITE");
        AnswerB.setStyle("-fx-background-color: WHITE");
        AnswerC.setStyle("-fx-background-color: WHITE");
    }

    public void setGxQuestion() {
        qcounter.setText("Question: " + game.getCounter() + "/20");
        ScoreText.setText("Score : " + game.getUser().getScore());
        GuessXQuestion question = (GuessXQuestion) currentQuestion;
        setImagesGX(question);
        textGXQuestion.setText("- "+
                question.getCorrectOption().getTitle()+ " -");
        guessAnswer.setDisable(false);
        guessAnswer.clear();
    }

    public void setImagesME(MostEnergyQuestion question){
        String path1 = question.getFirstOption().getImagePath();
        String path2 = question.getSecondOption().getImagePath();
        String path3 = question.getThirdOption().getImagePath();
        imageView1.setImage(mainCtrl.getImage(path1));
        imageView2.setImage(mainCtrl.getImage(path2));
        imageView3.setImage(mainCtrl.getImage(path3));
        startTimer();
    }

    public void setImagesHQ(HowMuchQuestion question){
        String path2 = question.getSecondOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
        startTimer();
    }

    public void setImagesGX(GuessXQuestion question){
        String path2 = question.getCorrectOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
        startTimer();
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
                if(currentQuestion instanceof MostEnergyQuestion ||
                        currentQuestion instanceof HowMuchQuestion) {
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
        if(currentQuestion instanceof MostEnergyQuestion ||
                currentQuestion instanceof HowMuchQuestion) {
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
        } else {
        //Correct answer for Guess x question
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
            answerPoints(currentQuestion,
                    Integer.parseInt(guessAnswer.getText()));}
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
        if(gameScreenCtrl.answerCorrect(currentQuestion,answer)) {
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

    public void setCurrentQuestion(Object currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void onEmoji1() {
        server.send("/app/emoji", new Activity("emoji1.png",
                game.getUser().getUserName(), 1));
    }

    public void onEmoji2() {
        server.send("/app/emoji", new Activity("emoji2.png",
                game.getUser().getUserName(), 1));
    }

    public void onEmoji3() {
        server.send("/app/emoji", new Activity("emoji3.png",
                game.getUser().getUserName(), 1));
    }

    public void setImageViewPic1(Activity emoji) {
        if(Pic1.getOpacity() != 0) {
            setImageViewPic2(Pic1.getImage(), User1.getText(),
                    Pic1.getOpacity());
        }
        Pic1.setImage(mainCtrl.getEmoji(emoji.getImagePath()));
        User1.setText(emoji.getTitle());
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3), Pic1);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3), User1);
        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }

    public void setImageViewPic2(Image image, String user, double opacity) {
        if(Pic2.getOpacity() != 0) {
            setImageViewPic3(Pic2.getImage(), User2.getText(),
                    Pic2.getOpacity());
        }
        Pic2.setImage(image);
        User2.setText(user);
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3 * opacity), Pic2);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3 * opacity), User2);
        fadeTransition1.setFromValue(opacity);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }

    public void setImageViewPic3(Image image, String user, double opacity) {
        if(Pic3.getOpacity() != 0) {
            setImageViewPic4(Pic3.getImage(), User3.getText(),
                    Pic3.getOpacity());
        }
        Pic3.setImage(image);
        User3.setText(user);
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3 * opacity), Pic3);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3 * opacity), User3);
        fadeTransition1.setFromValue(opacity);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }

    public void setImageViewPic4(Image image, String user, double opacity) {
        if(Pic4.getOpacity() != 0) {
            setImageViewPic5(Pic4.getImage(), User4.getText(),
                    Pic4.getOpacity());
        }
        Pic4.setImage(image);
        User4.setText(user);
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3 * opacity), Pic4);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3 * opacity), User4);
        fadeTransition1.setFromValue(opacity);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }

    public void setImageViewPic5(Image image, String user, double opacity) {
        if(Pic5.getOpacity() != 0) {
            setImageViewPic6(Pic5.getImage(), User5.getText(),
                    Pic5.getOpacity());
        }
        Pic5.setImage(image);
        User5.setText(user);
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3 * opacity), Pic5);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3 * opacity), User5);
        fadeTransition1.setFromValue(opacity);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }

    public void setImageViewPic6(Image image, String user, double opacity) {
        Pic6.setImage(image);
        User6.setText(user);
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3 * opacity), Pic6);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3 * opacity), User6);
        fadeTransition1.setFromValue(opacity);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }
}