package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.MostEnergyQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class GameScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    public Button QuitButton;

    @FXML
    public Button AnswerA;

    @FXML
    public Button AnswerB;

    @FXML
    public Button AnswerC;

    /**
     * Instantiates a new Game screen ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public GameScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Selecting answer A
     */
    public void SelectAnswerA(){
        AnswerA.setStyle("-fx-background-color: #f6b26b");
        AnswerB.setDisable(true);
        AnswerC.setDisable(true);
    }

    /**
     * Selecting answer B
     */
    public void SelectAnswerB(){
        AnswerB.setStyle("-fx-background-color: #f6b26b");
        AnswerA.setDisable(true);
        AnswerC.setDisable(true);
    }

    /**
     * Selecting answer C
     */
    public void SelectAnswerC(){
        AnswerC.setStyle("-fx-background-color: #f6b26b");
        AnswerB.setDisable(true);
        AnswerA.setDisable(true);
    }

    /**
     * Change screen to StartScene
     */
    public void goToStartScene(){
        mainCtrl.showStartScreen();
    }

    public boolean AnswerCorrect (MostEnergyQuestion question, int answer){
        switch (answer){
            case 1:
                if (question.getFirstOption() == question.getFirstOption()){
                    return true;
                }
                break;
            case 2:
                if (question.getSecondOption() == question.getSecondOption()){
                    return true;
                }
                break;
            case 3:
                if (question.getThirdOption() == question.getThirdOption()){
                    return true;
                }
                break;
        }
        return false;
    }

}
