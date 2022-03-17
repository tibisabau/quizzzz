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

    private GameScreenCtrl GameScreenCtrl;

    private Scene GameScreenScene;

    private Scene leaderboardScene;

    private leaderboardSceneCtrl leaderboardSceneCtrl;

    private InBetweenScreenCtrl inBetweenCtrl;

    private Scene inBetweenScene;

    /**
     * Initialize.
     *
     * @param primaryStage the primary stage
     * @param startScreen  the start screen
     * @param instructionScene
     * @param gameScreen
     * @param leaderboardScreen
     */
    public void initialize(Stage primaryStage, Pair<StartScreenCtrl,
            Parent> startScreen
            , Pair<InstructionSceneCtrl, Parent> instructionScene,
                           Pair<GameScreenCtrl, Parent> gameScreen,
                           Pair<leaderboardSceneCtrl,
                                   Parent> leaderboardScreen,
                           Pair<InBetweenScreenCtrl, Parent> inBetweenScreen) {
        this.primaryStage = primaryStage;
        this.startScreenCtrl = startScreen.getKey();
        this.startScreen = new Scene(startScreen.getValue());
        this.instructionScene = new Scene(instructionScene.getValue());
        this.instructionSceneCtrl = instructionScene.getKey();
        this.leaderboardScene = new Scene(leaderboardScreen.getValue());
        this.leaderboardSceneCtrl = leaderboardScreen.getKey();
        this.GameScreenCtrl = gameScreen.getKey();
        this.GameScreenScene = new Scene(gameScreen.getValue());
        this.inBetweenCtrl = inBetweenScreen.getKey();
        this.inBetweenScene = new Scene(inBetweenScreen.getValue());

        showStartScreen();
        primaryStage.show();
    }

    /**
     * Show start screen.
     */
    public void showStartScreen() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(startScreen);
    }

    /**
     * Show instruction screen.
     */
    public void showInstructionScreen() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(instructionScene);
    }

    /**
     * Show game screen
     */
    public void showGameScreen() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(GameScreenScene);
        GameScreenCtrl.setAnswer();
    }

    /**
     * Show leaderboard screen.
     */
    public void showLeaderboard(){
        primaryStage.setTitle("Quizzzz");
        leaderboardSceneCtrl.load();
        primaryStage.setScene(leaderboardScene);
    }

    /**
     * Show inBetween screen.
     */
    public void showInBetweenScreen(int question, int score) {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(inBetweenScene);
        inBetweenCtrl.StartTimer(question, score);
    }
}