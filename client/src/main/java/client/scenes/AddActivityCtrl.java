package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddActivityCtrl {

    public List<String> fileList;

    public File imageFile;

    public boolean toAdd;

    public Activity editActivity;

    @FXML
    public TextField title;

    @FXML
    public TextField consumption;

    @FXML
    public Label file;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    /**
     * Instantiates a new Add activity ctrl.
     *
     * @param server   the server
     * @param mainCtrl the main ctrl
     */
    @Inject
    public AddActivityCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Cancel.
     */
    public void cancel() {
        clearFields();
        mainCtrl.showAdminPanel();
    }

    /**
     * Ok.
     */
    public void ok() {
        try {
            if(title.getText().isEmpty() || consumption.getText().isEmpty()
                    || imageFile == null) {
                throw new IllegalStateException();
            }
            if(toAdd) {
                server.addEntry(new Activity(server.addImage(imageFile),
                        title.getText(), Long.parseLong
                        (consumption.getText())));
            }
            else {
                if(imageFile != null) {
                    editActivity.setImagePath(server.addImage(imageFile));
                }
                editActivity.setTitle(title.getText());
                editActivity.setConsumptionInWh
                        (Long.parseLong(consumption.getText()));
                server.updateEntry(editActivity);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Not all fields are completed!");
            alert.showAndWait();
            return;
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The consumption should be a number!");
            alert.showAndWait();
            return;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("The image should be of the type: " +
                    "\"name.extension\"");
            alert.showAndWait();
            return;
        }
        clearFields();
        mainCtrl.showAdminPanel();
    }

    private void clearFields() {
        title.clear();
        consumption.clear();
        file.setText("No file selected");
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

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.
                ExtensionFilter("Image Files", fileList));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            file.setText(f.getName());
            imageFile = f;
        }
    }

    /**
     * sets the available file types
     */
    public void initialize() {
        fileList = new ArrayList<>();
        fileList.add("*jpg");
        fileList.add("*png");
        fileList.add("*jpeg");
    }

}
