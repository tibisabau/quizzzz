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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Set;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javafx.scene.image.Image;

/**
 * The type Main ctrl.
 */
public class MainCtrl {

    public int counter;

    public Set<Object> questionList;

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

    private Scene gxQuestionScene;

    private Scene leaderboardScene;

    private leaderboardSceneCtrl leaderboardSceneCtrl;

    private Scene adminPanelScene;

    private AdminPanelCtrl adminPanelCtrl;

    private Scene imageScene;

    private DisplayImageCtrl imageCtrl;

    private InBetweenScreenCtrl inBetweenCtrl;

    private Scene inBetweenScene;

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
     * @param inBetweenScreen
     * @param adminPanel
     * @param image
     */
    public void initialize(Stage primaryStage, Pair<StartScreenCtrl,
            Parent> startScreen
            , Pair<InstructionSceneCtrl, Parent> instructionScene,
                           Pair<GameScreenCtrl, Parent> meQuestion,
                           Pair<leaderboardSceneCtrl,
                                   Parent> leaderboardScreen,

                           Pair<GameScreenCtrl, Parent> hmQuestion,
                           Pair<GameScreenCtrl, Parent> gxQuestion,
                           Pair<InBetweenScreenCtrl, Parent> inBetweenScreen
            , Pair<AdminPanelCtrl, Parent> adminPanel, Pair<DisplayImageCtrl, Parent> image) {
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
        this.inBetweenCtrl = inBetweenScreen.getKey();
        this.inBetweenScene = new Scene(inBetweenScreen.getValue());
        this.adminPanelScene = new Scene(adminPanel.getValue());
        this.adminPanelCtrl = adminPanel.getKey();
        this.imageScene = new Scene(image.getValue());
        this.imageCtrl = image.getKey();

        showStartScreen();
        primaryStage.show();
    }

    /**
     * Show start screen.
     */
    public void showStartScreen() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(startScreen);
        meQuestion.setCounter(20);
        meQuestion.setQuestionList();
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
     * @param questionType
     */
    public void showMEQuestion(int questionType) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(meQuestionScene);
        meQuestion.setAnswer(questionType);
    }

    /**
     * Show HMQuestion
     * @param questionType
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
     * Show leaderboard screen.
     */
    public void showLeaderboard(){
        primaryStage.setTitle("Quizzzz");
        leaderboardSceneCtrl.load();
        primaryStage.setScene(leaderboardScene);
    }

    public void showAdminPanel(){
        primaryStage.setTitle("Quizzzz");
        adminPanelCtrl.load();
        primaryStage.setScene(adminPanelScene);
    }

    /**
     * show inBetween Screen
     * @param question
     * @param score
     */
    public void showInBetweenScreen(int question, int score) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(inBetweenScene);
        inBetweenCtrl.startTimer(question, score);
    }


    public Image getImage(String path) {
        String imageString = server.getImage(path);
        Base64.Decoder encoder = Base64.getDecoder();
        byte[] byteArray = encoder.decode(imageString);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        return new Image(inputStream);
    }

    public void displayImage(String path) {
        imageCtrl.imageView.setImage(getImage(path));
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(imageScene);
    }
}