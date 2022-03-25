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

package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());

    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException,
            IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var instruction = FXML.load(InstructionSceneCtrl.class,
                "client", "scenes", "InstructionPageScreen.fxml");
        var start = FXML.load(StartScreenCtrl.class, "client",
                "scenes", "StartScreen.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        var meQuestion = FXML.load(GameScreenCtrl.class,
                "client", "scenes", "MEQuestion.fxml");
        var hmQuestion = FXML.load(GameScreenCtrl.class,
                "client", "scenes", "HMQuestion.fxml");
        var gxQuestion = FXML.load(GameScreenCtrl.class,
                "client", "scenes", "GXQuestion.fxml");
        var leaderboard = FXML.load(
                leaderboardSceneCtrl.class, "client", "scenes",
                "leaderboardScene.fxml");
        var inBetweenScreen = FXML.load(InBetweenScreenCtrl.class,
                "client", "scenes", "InBetweenScreen.fxml");
        var waitingRoom = FXML.load(waitingRoomController.class, "client", "scenes", "waitingRoomScreen.fxml");

        var gxQuestionMP = FXML.load(GameScreenMPCtrl.class, "client", "scenes", "GXQuestionMP.fxml");
        var hmQuestionMP = FXML.load(GameScreenMPCtrl.class, "client", "scenes", "HMQuestion.fxml");
        var meQuestionMP = FXML.load(GameScreenMPCtrl.class, "client", "scenes", "MEQuestion.fxml");

        mainCtrl.initialize(primaryStage, start ,
                instruction, meQuestion, leaderboard, hmQuestion
                , gxQuestion, inBetweenScreen, waitingRoom, gxQuestionMP, hmQuestionMP, meQuestionMP);

        primaryStage.setOnCloseRequest(e -> {
            waitingRoom.getKey().stop();
        });
    }
}