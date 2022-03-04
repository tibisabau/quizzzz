package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Score;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;





public class StartScreenCtrl extends Application {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

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

    @Inject
    public StartScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    private void cancel() {
        nicknameField.clear();
    }

    public void quitButtonClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void setNicknameField(KeyEvent keyEvent){

    }

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

    public void ok() {
        try {
            server.addScore(getNewScore());
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

    public Score getNewScore(){
            Score score = new Score(nicknameField.getText(), 0);
            return score;
    }



}
