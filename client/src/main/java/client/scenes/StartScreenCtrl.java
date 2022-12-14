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

import java.io.IOException;


public class StartScreenCtrl extends Application {

    private static Score ownScore;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;


    @FXML
    private TextField nicknameField;

    @FXML
    private TextField chooseServer;

    @FXML
    private Button singlePlayerButton;

    @FXML
    private Button multiPlayerButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button instructionsButton;

    @FXML
    private Button adminPanel;

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
    }

    /**
     * clear nickname field
     */
    private void cancel() {
        nicknameField.clear();
    }

    /**
     * Reset score
     */
    public static void resetScore(){
        ownScore.setScore(0);
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
            mainCtrl.setScore(ownScore);
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        cancel();
    }

    /**
     * Get new score .
     * @return the score
     */
    public Score getNewScore(){
            Score score = new Score(nicknameField.getText(), 0);
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
    public void goToGameScreen() throws IOException {
        if(ownScore != null){
            int questionType = server.getQuestionType();
            switch (questionType){
                case 1:
                    mainCtrl.showMEQuestion(questionType);
                    break;
                case 2:
                    mainCtrl.showHMQuestion(questionType);
                    break;
                case 3:
                    mainCtrl.showGXQuestion();
                    break;
                case 4:
                    mainCtrl.showInsteadOfQuestion(questionType);
            }
        }
    }

    /**
     * show the waiting room
     * @throws IOException
     */
    public void goToWaitingRoom() throws  IOException{
        if (ownScore != null){
            mainCtrl.showWaitingRoom();
        }
    }

    /**
     * show the admin panel
     */
    public void goToAdminPanel(){
        mainCtrl.showAdminPanel();
    }

    /**
     * Check if the selected server is running
     */
    public void tryServer() {
        try{
            mainCtrl.setServer(chooseServer.getText());
            nicknameField.setDisable(false);
            singlePlayerButton.setDisable(false);
            multiPlayerButton.setDisable(false);
            adminPanel.setDisable(false);
        }
        catch(Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("THE SERVER DOES NOT EXIST!");
            alert.showAndWait();
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

    /**
     * Disable all of the buttons
     */
    public void disableButtons() {
        singlePlayerButton.setDisable(true);
        multiPlayerButton.setDisable(true);
        nicknameField.setDisable(true);
        adminPanel.setDisable(true);
    }

    /**
     * set username
     */
    public void setUsername() {
        if (ownScore != null){
            ownScore = new Score(ownScore.getUserName(), 0);
            nicknameField.setText(ownScore.getUserName());
        }
    }

    /**
     * Playing again with same user
     */
    public void newGame(){
        ownScore = new Score(ownScore.getUserName(), 0);
    }
}
