package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.awt.*;

public class DisplayImageCtrl {

    @FXML
    public ImageView imageView;

    @FXML
    public Button backButton;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @Inject
    public DisplayImageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void goBack() {
        mainCtrl.showAdminPanel();
    }
}
