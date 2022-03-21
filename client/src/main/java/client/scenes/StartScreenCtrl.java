package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Score;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;


/**
 * The type Start screen ctrl.
 */
public class StartScreenCtrl extends Application {

    private static Score ownScore;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private boolean isCompleted;


    @FXML
    private TextField nicknameField;

    @FXML
    private Button singlePlayerButton;

    @FXML
    private Button multiPlayerButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button instructionsButton;

    /**
     * Instantiates a new Start screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public StartScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.isCompleted = false;
    }

    private void cancel() {
        nicknameField.clear();
    }

    /**
     * Display message that check
     * if the user really wants to exit the application.
     *
     */
    public void quitButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You are about to quit the quizzzzz.");
        alert.setContentText("Are you sure you want to exit?");
        if(alert.showAndWait().get() == ButtonType.OK){
            System.exit(0);
        }else {
            mainCtrl.showStartScreen();
        }
    }


    /**
     * Key pressed.
     *
     * @param e the e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    /**
     * Adds the new player with score 0 to the database.
     */
    public void ok() {
        try {
            ownScore = server.addScore(getNewScore());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        cancel();
        mainCtrl.showStartScreen();
    }

    /**
     * Get new score .
     *
     * @return the score
     */
    public Score getNewScore(){
            Score score = new Score(nicknameField.getText(), 0);
            isCompleted = true;
            return score;
    }

    /**
     * Change screen to instruction screen
     */
    public void instructionButtonClick(){
        mainCtrl.showInstructionScreen();
    }

    /**
     * Change screen to Single Player GameScreen
     */
    public void goToGameScreen(){
        if(isCompleted == true){
            int questionType = server.getQuestionType();
            if(questionType == 1) {
                mainCtrl.showMEQuestion(questionType);
            }
            else
                if(questionType == 2) {
                    mainCtrl.showHMQuestion(questionType);
                }
                    else {
                        mainCtrl.showGXQuestion();
                }
            isCompleted = false;
        }else{
            return;
        }

    }

    /**
     * getter for ownScore
     * @return ownScore
     */
    public static Score getOwnScore() {
        return ownScore;
    }
}
