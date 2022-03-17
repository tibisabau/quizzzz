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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The type Main ctrl.
 */
public class MainCtrl {

    private Stage primaryStage;

    private StartScreenCtrl startScreenCtrl;

    private Scene startScreen;

    private InstructionSceneCtrl instructionSceneCtrl;

    private Scene instructionScene;

    private QuestionController meQuestion;

    private Scene meQuestionScene;

    private QuestionController hmQuestion;

    private Scene hmQuestionScene;

    private QuestionController gxQuestion;

    private Scene gxQuestionScene;

    private Scene leaderboardScene;

    private leaderboardSceneCtrl leaderboardSceneCtrl;

    /**
     * Initialize.
     *
     * @param primaryStage the primary stage
     * @param startScreen  the start screen
     * @param instructionScene
     * @param meQuestion
     * @param hmQuestion
     * @param gxQuestion
     * @param leaderboardScreen
     */
    public void initialize(Stage primaryStage, Pair<StartScreenCtrl,
            Parent> startScreen
            , Pair<InstructionSceneCtrl, Parent> instructionScene,
                           Pair<QuestionController, Parent> meQuestion,
                           Pair<leaderboardSceneCtrl,
                                   Parent> leaderboardScreen,
                           Pair<QuestionController, Parent> hmQuestion,
                           Pair<QuestionController, Parent> gxQuestion) {
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
    }

    /**
     * Show instruction screen.
     */
    public void showInstructionScreen() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(instructionScene);
    }

    /**
     * Show QuestionController
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
        gxQuestion.guessAnswer.setText("");
    }

    /**
     * Show leaderboard screen.
     */
    public void showLeaderboard(){
        primaryStage.setTitle("Quizzzz");
        leaderboardSceneCtrl.load();
        primaryStage.setScene(leaderboardScene);
    }


}