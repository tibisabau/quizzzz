package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javax.inject.Inject;
import java.awt.*;

public class InstructionSceneCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private Button gotItButton;

    @Inject
    public InstructionSceneCtrl(ServerUtils server, MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * show the start screen
     * @param event
     */
    public void gotItButtonClick(MouseEvent event){
        mainCtrl.showStartScreen();
    }
}
