/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.utils.ServerUtils;
import commons.*;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javafx.scene.image.Image;


public class MainCtrl {

    public int counter;

    public Set<Object> questionList;

    private boolean pointsJokerUsed;

    private boolean answerJokerUsed;

    private Stage primaryStage;

    private StartScreenCtrl startScreenCtrl;

    private Scene startScreen;

    private InstructionSceneCtrl instructionSceneCtrl;

    private Scene instructionScene;

    private GameScreenCtrl meQuestion;

    private Scene meQuestionScene;

    private GameScreenCtrl hmQuestion;

    private Scene hmQuestionScene;

    private GameScreenCtrl gxQuestion;

    private Scene gxQuestionMP;

    private GameScreenMPCtrl gxQuestionMPCtrl;

    private Scene insteadOfSceneMP;

    private GameScreenMPCtrl insteadOfQuestionMPCtrl;

    private Scene hmQuestionMP;

    private GameScreenMPCtrl hmQuestionMPCtrl;

    private Scene meQuestionMP;

    private GameScreenMPCtrl meQuestionMPCtrl;

    private Scene gxQuestionScene;

    private GameScreenCtrl insteadOfQuestion;

    private Scene insteadOfScene;

    private Scene leaderboardScene;

    private LeaderboardSceneCtrl leaderboardSceneCtrl;

    private Scene adminPanelScene;

    private AdminPanelCtrl adminPanelCtrl;

    private Scene imageScene;

    private DisplayImageCtrl imageCtrl;

    private Scene addScene;

    private AddActivityCtrl addCtrl;

    private InBetweenScreenCtrl inBetweenCtrl;

    private Scene inBetweenScene;

    private Scene waitingRoomScene;

    private WaitingRoomCtrl waitingRoomCtrl;

    private Scene podiumScene;

    private PodiumCtrl podiumCtrl;

    private Score score;

    private String userName;

    @Inject
    private ServerUtils server;

    /**
     * Initialize.
     * @param primaryStage the primary stage
     * @param startScreen  the start screen
     * @param instructionScene
     * @param meQuestion
     * @param leaderboardScreen
     * @param hmQuestion
     * @param gxQuestion
     * @param insteadOfQuestion
     * @param inBetweenScreen
     * @param waitingRoom
     * @param gxQuestionMP
     * @param hmQuestionMP
     * @param meQuestionMP
     * @param insteadOfQuestionMP
     * @param adminPanel
     * @param image
     * @param add
     * @param podium
     */
    public void initialize(Stage primaryStage, Pair<StartScreenCtrl,
            Parent> startScreen
            , Pair<InstructionSceneCtrl, Parent> instructionScene,
                           Pair<GameScreenCtrl, Parent> meQuestion,
                           Pair<LeaderboardSceneCtrl,
                                   Parent> leaderboardScreen,

                           Pair<GameScreenCtrl, Parent> hmQuestion,
                           Pair<GameScreenCtrl, Parent> gxQuestion,
                           Pair<GameScreenCtrl, Parent> insteadOfQuestion,
                           Pair<InBetweenScreenCtrl, Parent> inBetweenScreen,
                           Pair<WaitingRoomCtrl, Parent> waitingRoom,
                           Pair<GameScreenMPCtrl, Parent> gxQuestionMP,
                           Pair<GameScreenMPCtrl, Parent> hmQuestionMP,
                           Pair<GameScreenMPCtrl, Parent> meQuestionMP,
                           Pair<GameScreenMPCtrl, Parent> insteadOfQuestionMP,
                           Pair<AdminPanelCtrl, Parent> adminPanel,
                           Pair<DisplayImageCtrl, Parent> image
            , Pair<AddActivityCtrl, Parent> add, Pair<PodiumCtrl,
            Parent> podium) {
        this.primaryStage = primaryStage;

        this.startScreenCtrl = startScreen.getKey();
        this.startScreen = new Scene(startScreen.getValue());

        this.instructionScene = new Scene(instructionScene.getValue());
        this.instructionSceneCtrl = instructionScene.getKey();

        this.leaderboardScene = new Scene(leaderboardScreen.getValue());
        this.leaderboardSceneCtrl = leaderboardScreen.getKey();

        this.meQuestion = meQuestion.getKey();
        this.meQuestionScene = new Scene(meQuestion.getValue());

        this.hmQuestion = hmQuestion.getKey();
        this.hmQuestionScene = new Scene(hmQuestion.getValue());

        this.gxQuestion = gxQuestion.getKey();
        this.gxQuestionScene = new Scene(gxQuestion.getValue());
        this.insteadOfQuestion = insteadOfQuestion.getKey();
        this.insteadOfScene = new Scene(insteadOfQuestion.getValue());
        this.inBetweenCtrl = inBetweenScreen.getKey();
        this.inBetweenScene = new Scene(inBetweenScreen.getValue());
        this.adminPanelScene = new Scene(adminPanel.getValue());
        this.adminPanelCtrl = adminPanel.getKey();
        this.imageScene = new Scene(image.getValue());
        this.imageCtrl = image.getKey();
        this.addScene = new Scene(add.getValue());
        this.addCtrl = add.getKey();

        this.waitingRoomCtrl = waitingRoom.getKey();
        this.waitingRoomScene = new Scene(waitingRoom.getValue());

        this.gxQuestionMPCtrl = gxQuestionMP.getKey();
        this.gxQuestionMP = new Scene(gxQuestionMP.getValue());

        this.hmQuestionMPCtrl = hmQuestionMP.getKey();
        this.hmQuestionMP = new Scene(hmQuestionMP.getValue());

        this.meQuestionMPCtrl = meQuestionMP.getKey();
        this.meQuestionMP = new Scene(meQuestionMP.getValue());

        this.insteadOfQuestionMPCtrl = insteadOfQuestionMP.getKey();
        this.insteadOfSceneMP = new Scene(insteadOfQuestionMP.getValue());

        this.podiumCtrl = podium.getKey();
        this.podiumScene = new Scene(podium.getValue());

        showStartScreen();
        primaryStage.show();
    }

    /**
     * Show start screen.
     */
    public void showStartScreen() {
        primaryStage.setTitle("Quizzzz");
        if (this.score != null) {
            score.setScore(0);
        }
        startScreenCtrl.setUsername();
        primaryStage.setScene(startScreen);
        startScreenCtrl.disableButtons();
        meQuestion.setCounter(20);
        pointsJokerUsed = false;
        answerJokerUsed = false;
        meQuestion.setQuestionList();
    }

    /**
     * Setter for Score
     * @param score
     */
    public void setScore(Score score){
        this.score = score;
    }


    public Score getScore() { return this.score; }


    /**
     * Setter for userName
     * @param userName
     */

    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Show instruction screen.
     */
    public void showInstructionScreen() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(instructionScene);
    }

    /**
     * Show MEQuestion
     *
     * @param questionType the question type
     */
    public void showMEQuestion(int questionType) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(meQuestionScene);
        meQuestion.setAnswer(questionType);
    }

    /**
     * Show HMQuestion
     *
     * @param questionType the question type
     */
    public void showHMQuestion(int questionType) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(hmQuestionScene);
        hmQuestion.setAnswer(questionType);
    }

    /**
     * Show GXQuestion
     */
    public void showGXQuestion() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(gxQuestionScene);
        gxQuestion.createGXQuestion();
    }

    /**
     * Show instead of question.
     *
     * @param questionType the question type
     */
    public void showInsteadOfQuestion(int questionType) {
        primaryStage.setTitle("Quizzzz");
        insteadOfQuestion.setAnswer(questionType);
        primaryStage.setScene(insteadOfScene);
    }

    /**
     * Show leaderboard screen.
     * @param isSinglePlayer checks if the game is single player
     * @param showButton checks if this is the final display of the leaderboard
     * @param l
     */
    public void showLeaderboard(boolean isSinglePlayer,
                                boolean showButton, List l){
        primaryStage.setTitle("Quizzzz");
        leaderboardSceneCtrl.load(isSinglePlayer, showButton, l);
        primaryStage.setScene(leaderboardScene);
    }

    /**
     * show admin panel
     */
    public void showAdminPanel(){
        adminPanelCtrl.table.getItems().removeAll();
        primaryStage.setTitle("Quizzzz");
        adminPanelCtrl.load();
        primaryStage.setScene(adminPanelScene);
    }

    /**
     * show inBetween Screen
     *
     * @param question the question
     * @param score    the score
     */
    public void showInBetweenScreen(int question, int score) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(inBetweenScene);
        inBetweenCtrl.startTimer(question, score);
    }


    /**
     * decodes the image as path
     *
     * @param path the path
     * @return a new image
     */
    public Image getImage(String path) {
        String imageString;
        try {
            imageString = server.getImage(path);
        }
        catch (Exception e){
            imageString = server.getEmoji("ImageNotFound.png");
        }
        Base64.Decoder encoder = Base64.getDecoder();
        byte[] byteArray = encoder.decode(imageString);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return new Image(inputStream);}





    /**
     * decodes the emoji image as path
     * @param path
     * @return a new image
     */
    public Image getEmoji(String path) {
        String imageString = server.getEmoji(path);
        Base64.Decoder encoder = Base64.getDecoder();
        byte[] byteArray = encoder.decode(imageString);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return new Image(inputStream);
    }

    /**
     * Show the waitting room
     */
    public void showWaitingRoom(){
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(waitingRoomScene);
        waitingRoomCtrl.setScore(this.score);
        waitingRoomCtrl.load();

    }

    /**
     *PointsJoker getter
     * @return pointsJokerUsed
     */
    public boolean isPointsJokerUsed() {
        return pointsJokerUsed;
    }

    /**
     *
     * @return if answerJokerUsed
     */
    public boolean isAnswerJokerUsed() {
        return answerJokerUsed;
    }

    /**
     * Sets pointsJoker to true
     */
    public void usePointsJoker(){
        pointsJokerUsed = true;
    }

    /**
     * Sets answerJoker to true
     */
    public void useAnswerJoker(){
        answerJokerUsed = true;
    }




    /**
     * show the image from path
     *
     * @param path the path
     */
    public void displayImage(String path) {
        imageCtrl.imageView.setImage(getImage(path));
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(imageScene);
    }

    /**
     * show the add scene
     */
    public void showAdd() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(addScene);
        addCtrl.toAdd = true;
    }

    /**
     * show the edit scene
     *
     * @param activity the activity
     */
    public void showEdit(Activity activity) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(addScene);
        addCtrl.title.setText(activity.getTitle());
        addCtrl.consumption.setText(String.valueOf(
                activity.getConsumptionInWh()));
        addCtrl.file.setText(activity.getImagePath()
                .substring(activity.getImagePath().indexOf("/") + 1).trim());
        addCtrl.toAdd = false;
        addCtrl.editActivity = activity;
    }

    /**
     * Show the Multiplayer Game screen
     * and registers for 3 websockets
     * for next question, betweenScreen and Time joker.
     * @param game
     * @param playerCount
     */
    public void showMpGameScreen(Game game, int playerCount){
        meQuestionMPCtrl.setGame(game);
        hmQuestionMPCtrl.setGame(game);
        gxQuestionMPCtrl.setGame(game);
        insteadOfQuestionMPCtrl.setGame(game);

        meQuestionMPCtrl.setPlayerCount(playerCount);
        hmQuestionMPCtrl.setPlayerCount(playerCount);
        gxQuestionMPCtrl.setPlayerCount(playerCount);
        insteadOfQuestionMPCtrl.setPlayerCount(playerCount);

        meQuestionMPCtrl.getTypeOfQuestion();

        server.registerForMessages("/topic/joker", Joker.class, joker -> {
            if (joker.getGameID() == game.getID() &&
                    joker.getUserID() != game.getUser().getUserId()){
                switch (joker.getType()){
                    case 1:
                        Activity answer = new Activity("AnswerJoker.jpg",
                                joker.getUserName(), 1);
                        Platform.runLater(() -> setJokers(answer));
                        break;
                    case 2:
                        Activity points = new Activity("PointsJoker.jpg",
                                joker.getUserName(), 1);
                        Platform.runLater(() -> setJokers(points));
                        break;
                    case 3:
                        Activity time = new Activity("Time.jpg",
                                joker.getUserName(), 1);
                        Platform.runLater(() -> setJokers(time));
                        meQuestionMPCtrl.halfTime();
                        gxQuestionMPCtrl.halfTime();
                        insteadOfQuestionMPCtrl.halfTime();
                        hmQuestionMPCtrl.halfTime();
                        break;
                }

            }
        });
        server.registerForMessages("/topic/nextQuestion", Game.class, g -> {
            if(g.getID() == game.getID()){
                meQuestionMPCtrl.setQuestion(g.getCurrentQuestion());
                hmQuestionMPCtrl.setQuestion(g.getCurrentQuestion());
                gxQuestionMPCtrl.setQuestion(g.getCurrentQuestion());
                insteadOfQuestionMPCtrl.setQuestion(g.getCurrentQuestion());
                Platform.runLater(() -> meQuestionMPCtrl.getTypeOfQuestion());
            }
        });
        server.registerForMessages("/topic/" + game.getID(), List.class, l -> {
            podiumCtrl.setPodium(l, score);
            if(game.getCounter() < 20){
                Platform.runLater(() -> showLeaderboard(false,
                        false, l));
            } else {
                Platform.runLater(() -> showPodium());
            }
        });
        server.registerForMessages("/topic/playerLeft", Integer.class, X -> {
            if(X == game.getID()){
                Platform.runLater(() -> decrementCounter());
            }
        });
    }

    /**
     * show jokers on the game screen
     * @param jokers joker you want to show
     */
    public void setJokers(Activity jokers){
        meQuestionMPCtrl.setImageViewPic1(jokers);
        gxQuestionMPCtrl.setImageViewPic1(jokers);
        insteadOfQuestionMPCtrl.setImageViewPic1(jokers);
        hmQuestionMPCtrl.setImageViewPic1(jokers);
    }

    /**
     * Decrement player count when someone leaves the game
     */
    private void decrementCounter() {
        int playerCount = meQuestionMPCtrl.getPlayerCount() - 1;
        meQuestionMPCtrl.setPlayerCount(playerCount);
        gxQuestionMPCtrl.setPlayerCount(playerCount);
        insteadOfQuestionMPCtrl.setPlayerCount(playerCount);
        hmQuestionMPCtrl.setPlayerCount(playerCount);
    }

    /**
     * Show HMQuestionMP
     * @param currentQuestion
     */
    public void showMEQuestionMP(Object currentQuestion) {
        meQuestionMPCtrl.init();
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(meQuestionMP);
        meQuestionMPCtrl.setCurrentQuestion(currentQuestion);
        meQuestionMPCtrl.setMeQuestion();
        server.registerForMessages("/topic/emoji", Activity.class, emoji -> {
            if (emoji.getConsumptionInWh() ==
                    waitingRoomCtrl.getGame().getID()) {
                Platform.runLater(() -> {
                    meQuestionMPCtrl.setImageViewPic1(emoji);
                });
            }
        });
    }

    /**
     * Show HMQuestionMP
     * @param currentQuestion
     */
    public void showHMQuestionMP(Object currentQuestion) {
        hmQuestionMPCtrl.init();
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(hmQuestionMP);
        hmQuestionMPCtrl.setCurrentQuestion(currentQuestion);
        hmQuestionMPCtrl.setHmQuestion();
        server.registerForMessages("/topic/emoji", Activity.class, emoji -> {
            if (emoji.getConsumptionInWh() ==
                    waitingRoomCtrl.getGame().getID()) {
                Platform.runLater(() -> {
                    hmQuestionMPCtrl.setImageViewPic1(emoji);
                });
            }
        });
    }

    /**
     * Show GXQuestionMP
     * @param currentQuestion
     */
    public void showGXQuestionMP(Object currentQuestion) {
        gxQuestionMPCtrl.init();
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(gxQuestionMP);
        gxQuestionMPCtrl.setCurrentQuestion(currentQuestion);
        gxQuestionMPCtrl.setGxQuestion();
        server.registerForMessages("/topic/emoji", Activity.class, emoji -> {
            if (emoji.getConsumptionInWh() ==
                    waitingRoomCtrl.getGame().getID()) {
                Platform.runLater(() -> {
                    gxQuestionMPCtrl.setImageViewPic1(emoji);
                });
            }
        });
    }

    /**
     * Show InsteadOfQuestionMP
     * @param currentQuestion
     */
    public void showInsteadOfQuestionMP(Object currentQuestion) {
        insteadOfQuestionMPCtrl.init();
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(insteadOfSceneMP);
        insteadOfQuestionMPCtrl.setCurrentQuestion(currentQuestion);
        insteadOfQuestionMPCtrl.setInsteadOfQuestion();
        server.registerForMessages("/topic/emoji", Activity.class, emoji -> {
            if (emoji.getConsumptionInWh() ==
                    waitingRoomCtrl.getGame().getID()) {
                Platform.runLater(() -> {
                    insteadOfQuestionMPCtrl.setImageViewPic1(emoji);
                });
            }
        });
    }

    /**
     * Setter for the session
     * @param url
     */
    public void setServer(String url) {
        server.setSession(url);
    }

    /**
     * disconnect from the websockets
     */
    public void disconnect() {
        server.wsDisconnect();
    }

    public void showPodium() {
        primaryStage.setTitle("Quizzzz");
        server.stop();
        primaryStage.setScene(podiumScene);
    }
}