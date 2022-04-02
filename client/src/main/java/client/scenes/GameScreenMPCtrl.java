package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import commons.*;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
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

import java.util.Random;


public class GameScreenMPCtrl {
    @FXML
    public Text textGXQuestion;

    @FXML
    public Text textHMQuestion;

    @FXML
    public Text playerCount;

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
    public Label correctAnswerQX;

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
    public Label insteadOfLabel;

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
    public Button pointsJoker;

    @FXML
    public Button answerJoker;

    @FXML
    public Button timeJoker;

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

    private boolean pointsJokerInUse = false;

    private int answer;

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

    /**
     *  Initialize the pictures for the reactions
     */
    public void init(){
        EmojiMenuPic.setImage(mainCtrl.getEmoji("emoji1.png"));
        Emoji1.setImage(mainCtrl.getEmoji("emoji1.png"));
        Emoji2.setImage(mainCtrl.getEmoji("emoji2.png"));
        Emoji3.setImage(mainCtrl.getEmoji("emoji3.png"));
    }

    public void test(){

    }

    /**
     * Setter for the game object
     * @param game
     */
    public void setGame(Game game){
        this.game = game;
    }

    public void setQuestion(Object question) {
        game.setCurrentQuestion(question);
    }

    /**
     * Gets the type of the next question
     */
    public void getTypeOfQuestion(){
        Boolean found = false;
        currentQuestion = game.getCurrentQuestion();
        InsteadOfQuestion question = mapper.convertValue(currentQuestion,
                InsteadOfQuestion.class);
        if (question.getPromptedOption() != null){
            currentQuestion = question;
            mainCtrl.showInsteadOfQuestionMP(currentQuestion);
            found = true;
        }
        if (!found){
            MostEnergyQuestion question2 = mapper.convertValue(currentQuestion,
                    MostEnergyQuestion.class);
            if (question2.getIdentity() != null){
                currentQuestion = question2;
                found = true;
                mainCtrl.showMEQuestionMP(currentQuestion);
            }
        }
        if (!found){
            HowMuchQuestion question2 = mapper.convertValue(currentQuestion,
                    HowMuchQuestion.class);
            if (question2.getSecondOption() != null){
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

    /**
     * Creates the visuals for the Most Energy question
     */
    public void setMeQuestion() {

        resetStage();
        MostEnergyQuestion question = (MostEnergyQuestion) currentQuestion;
        setImagesME(question);
        Answer1.setText(question.getFirstOption().toStringAnswer());
        Answer2.setText(question.getSecondOption().toStringAnswer());
        Answer3.setText(question.getThirdOption().toStringAnswer());
    }

    /**
     * Creates the visuals for the How Much question
     */
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

    /**
     * Creates the visuals for the Instead of question
     */
    public void setInsteadOfQuestion() {
        resetStage();
        InsteadOfQuestion question = (InsteadOfQuestion) currentQuestion;
        setImageInsteadOfQuestion(question);
        insteadOfLabel.setText("Instead of : "
                + ((InsteadOfQuestion) currentQuestion).
                getPromptedOption().toStringAnswer()
                + " , you could do instead :");
        Answer1.setText(String.valueOf
                (((InsteadOfQuestion)currentQuestion).
                        getFirstOption().getTitle()));
        Answer2.setText(String.valueOf
                (((InsteadOfQuestion)currentQuestion).
                        getSecondOption().getTitle()));
        Answer3.setText(String.valueOf
                (((InsteadOfQuestion)currentQuestion).
                        getThirdOption().getTitle()));
    }

    /**
     * Resets the buttons after each question
     */
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
        Answer1.setStyle("-fx-font-weight: normal");
        Answer2.setStyle("-fx-font-weight: normal");
        Answer3.setStyle("-fx-font-weight: normal");
        answerJoker.setDisable(!game.isAnswerJoker());
        pointsJoker.setDisable(!game.isPointsJoker());
        timeJoker.setDisable(!game.isTimeJoker());
    }

    /**
     * Creates the visuals for the Guess X of question
     */
    public void setGxQuestion() {
        qcounter.setText("Question: " + game.getCounter() + "/20");
        ScoreText.setText("Score : " + game.getUser().getScore());
        GuessXQuestion question = (GuessXQuestion) currentQuestion;
        setImagesGX(question);
        textGXQuestion.setText("- "+
                question.getCorrectOption().getTitle()+ " -");
        guessAnswer.setDisable(false);
        guessAnswer.clear();
        guessAnswer.setStyle("-fx-background-color: WHITE");
        correctAnswerQX.setText("");
        pointsJoker.setDisable(!game.isPointsJoker());
        timeJoker.setDisable(!game.isTimeJoker());

    }

    /**
     * Sets the images for Most Energy question
     * @param question
     */
    public void setImagesME(MostEnergyQuestion question){
        String path1 = question.getFirstOption().getImagePath();
        String path2 = question.getSecondOption().getImagePath();
        String path3 = question.getThirdOption().getImagePath();
        imageView1.setImage(mainCtrl.getImage(path1));
        imageView2.setImage(mainCtrl.getImage(path2));
        imageView3.setImage(mainCtrl.getImage(path3));
        startTimer();
    }

    /**
     * Sets the images for How much question
     * @param question
     */
    public void setImagesHQ(HowMuchQuestion question){
        String path2 = question.getSecondOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
        startTimer();
    }

    /**
     * Sets the images for Guess X question
     * @param question
     */
    public void setImagesGX(GuessXQuestion question){
        String path2 = question.getCorrectOption().getImagePath();
        imageView2.setImage(mainCtrl.getImage(path2));
        startTimer();
    }

    /**
     * Set image instead of question.
     *
     * @param question the question
     */
    public void setImageInsteadOfQuestion(InsteadOfQuestion question){
        String path = question.getPromptedOption().getImagePath();
        imageView1.setImage(mainCtrl.getImage(path));
        startTimer();
    }

    /**
     * Starts the time bar
     */
    public void startTimer(){
        time.setStyle("-fx-accent: #00FF01");
        timer = 1;
        bar = new Timeline(new KeyFrame(Duration.millis(10), ev ->{
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
                timeJoker.setDisable(true);
            }
            if (timer <= 0.002){
                bar.stop();
                if(currentQuestion instanceof MostEnergyQuestion ||
                        currentQuestion instanceof HowMuchQuestion ||
                        currentQuestion instanceof InsteadOfQuestion) {
                    disableAnswers();
                }
                showAnswers();
                answerPoints(currentQuestion,answer);
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
        answerJoker.setDisable(true);
    }

    /**
     * Shows the correct Answer of the current question
     */
    public void showAnswers() {
        pointsJoker.setDisable(true);
        if(currentQuestion instanceof MostEnergyQuestion ||
                currentQuestion instanceof HowMuchQuestion ||
        currentQuestion instanceof InsteadOfQuestion) {
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
            if(gameScreenCtrl.answerCorrect(currentQuestion, answer)){
                guessAnswer.setStyle(correctColor);
            }
            else {
                guessAnswer.setStyle(incorrectColor);
            }
            GuessXQuestion cor = (GuessXQuestion) currentQuestion;
            long corText = cor.getCorrectOption().getConsumptionInWh();
            correctAnswerQX.setText("Correct answer: " + corText);
        }
    }

    /**
     * Selecting answer A
     */
    public void selectAnswerA() throws InterruptedException {
        Answer1.setStyle("-fx-font-weight: bold");
        disableAnswers();
        answer = 1;
    }

    /**
     * Selecting answer B
     */
    public void selectAnswerB() throws InterruptedException {
        Answer2.setStyle("-fx-font-weight: bold");
        disableAnswers();
        answer = 2;
    }

    /**
     * Selecting answer C
     */
    public void selectAnswerC() throws InterruptedException {
        Answer3.setStyle("-fx-font-weight: bold");
        disableAnswers();
        answer = 3;
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
        try{
            answer = Integer.parseInt(guessAnswer.getText());}
        catch (Exception e){
            answer = 0;
        }
    }

    /**
     * Gives points if correct answer is given
     * @param question question to check if correct
     * @param answer answer number from 1 to 3, 1 is for a, 2 for b, 3 for c
     *
     */
    public void answerPoints(Object question, int answer) {
        double multiplier = 0.5 + (2 * timer);
        int extraPoints = (int) Math.round(100 * multiplier);
        if (gameScreenCtrl.answerCorrect(currentQuestion, answer)) {
            if (pointsJokerInUse) {
                game.incrementScore(extraPoints * 2);
                pointsJokerInUse = false;
            } else {
                game.incrementScore(extraPoints);
            }
            showAnswers();
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
     * Change screen to start screen
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

    /**
     * Setter currentQuestion
     * @param currentQuestion
     */
    public void setCurrentQuestion(Object currentQuestion) {
        this.currentQuestion = currentQuestion;
    }


    /**
     * Clicking on the first Emoji
     */
    public void onEmoji1() {
        server.send("/app/emoji", new Activity("emoji1.png",
                game.getUser().getUserName(), 1));
    }

    /**
     * Clicking on the Second Emoji
     */
    public void onEmoji2() {
        server.send("/app/emoji", new Activity("emoji2.png",
                game.getUser().getUserName(), 1));
    }

    /**
     * Clicking on the Third Emoji
     */
    public void onEmoji3() {
        server.send("/app/emoji", new Activity("emoji3.png",
                game.getUser().getUserName(), 1));
    }

    /**
     * Set the first emoji slot to the picture and user
     * @param emoji
     */
    public void setImageViewPic1(Activity emoji) {
        if(Pic1.getOpacity() != 0) {
            setImageViewPic2(Pic1.getImage(), User1.getText(),
                    Pic1.getOpacity());
        }
        Pic1.setImage(mainCtrl.getEmoji(emoji.getImagePath()));
        User1.setText(emoji.getTitle());
        fade(1, Pic1, User1);
    }

    /**
     * Set the second emoji slot to the picture and user
     * @param image
     * @param user
     * @param opacity
     */
    public void setImageViewPic2(Image image, String user, double opacity) {
        if(Pic2.getOpacity() != 0) {
            setImageViewPic3(Pic2.getImage(), User2.getText(),
                    Pic2.getOpacity());
        }
        Pic2.setImage(image);
        User2.setText(user);
        fade(opacity, Pic2, User2);
    }

    /**
     * Set the third emoji slot to the picture and user
     * @param image
     * @param user
     * @param opacity
     */
    public void setImageViewPic3(Image image, String user, double opacity) {
        if(Pic3.getOpacity() != 0) {
            setImageViewPic4(Pic3.getImage(), User3.getText(),
                    Pic3.getOpacity());
        }
        Pic3.setImage(image);
        User3.setText(user);
        fade(opacity, Pic3, User3);
    }

    /**
     * Set the fourth emoji slot to the picture and user
     * @param image
     * @param user
     * @param opacity
     */
    public void setImageViewPic4(Image image, String user, double opacity) {
        if(Pic4.getOpacity() != 0) {
            setImageViewPic5(Pic4.getImage(), User4.getText(),
                    Pic4.getOpacity());
        }
        Pic4.setImage(image);
        User4.setText(user);
        fade(opacity, Pic4, User4);
    }

    /**
     * Set the fifth emoji slot to the picture and user
     * @param image
     * @param user
     * @param opacity
     */
    public void setImageViewPic5(Image image, String user, double opacity) {
        if(Pic5.getOpacity() != 0) {
            setImageViewPic6(Pic5.getImage(), User5.getText(),
                    Pic5.getOpacity());
        }
        Pic5.setImage(image);
        User5.setText(user);
        fade(opacity, Pic5, User5);
    }

    /**
     * Set the sixth emoji slot to the picture and user
     * @param image
     * @param user
     * @param opacity
     */
    public void setImageViewPic6(Image image, String user, double opacity) {
        Pic6.setImage(image);
        User6.setText(user);
        fade(opacity, Pic6, User6);
    }

    /**
     * fading animation
     * @param opacity
     * @param pic
     * @param user
     */
    private void fade(double opacity, ImageView pic, Label user) {
        FadeTransition fadeTransition = new FadeTransition
                (Duration.seconds(3 * opacity), pic);
        fadeTransition.setFromValue(opacity);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        FadeTransition fadeTransition1 = new FadeTransition
                (Duration.seconds(3 * opacity), user);
        fadeTransition1.setFromValue(opacity);
        fadeTransition1.setToValue(0.0);
        fadeTransition1.play();
    }

    /**
     * Uses answerJoker and disables an answer
     */
    public void useAnswerJoker(){
        answerJoker.setDisable(true);
        game.useAnswerJoker();
        Random rand = new Random();
        int answerToDelete = rand.nextInt(3);
        while (gameScreenCtrl.answerCorrect (currentQuestion, answerToDelete+1))
        {
            answerToDelete = answerToDelete + 1;
            if (answerToDelete > 2){
                answerToDelete = 0;
            }
        }
        switch (answerToDelete){
            case 0:
                Answer1.setDisable(true);
                AnswerA.setDisable(true);
                break;
            case 1:
                Answer2.setDisable(true);
                AnswerB.setDisable(true);
                break;
            case 2:
                Answer3.setDisable(true);
                AnswerC.setDisable(true);
                break;
        }

    }

    /**
     * flips a boolean to make dubble points and disables the button.
     */
    public void usePointsJoker(){
        pointsJoker.setDisable(true);
        game.usePointJoker();
        pointsJokerInUse = true;

    }

    /**
     * for time joker, sends a message to the server that it has been used.
      */
    public void useTimeJoker(){
        timeJoker.setDisable(true);
        game.useTimeJoker();
        server.sendGame("/app/joker",
                new Joker(game.getUser().getUserId(), game.getID()));

    }

    /**
     * halfs time on timeline
     */
    public void halfTime(){
        timer = timer/2;
    }

    /**
     * Quit the game
     */
    public void quitGame(){
        server.send("/app/playerLeft", game.getID());
        mainCtrl.disconnect();
        mainCtrl.showStartScreen();
    }

    /**
     * Get the player count
     * @return
     */
    public int getPlayerCount() {
        return Integer.parseInt(playerCount.getText());
    }

    /**
     * set the player count
     * @param count
     */
    public void setPlayerCount(int count) {
        playerCount.setText(String.valueOf(count));
    }
}