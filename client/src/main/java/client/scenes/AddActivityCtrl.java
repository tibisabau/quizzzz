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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AddActivityCtrl {

    public List<String> fileList;

    public File imageFile;

    public MultipartFile multipartFile;

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
            if(title.getText().isEmpty() || consumption.getText().isEmpty()) {
                throw new Exception();
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
        } catch (Exception e) {
            e.printStackTrace();
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Not all fields are completed!");
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

    public void chooseFile() throws IOException {
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

    /**
     * uploads an image on the activity bank
     * @param multipartFile
     * @return a file name
     * @throws IOException
     */
    public String save(MultipartFile multipartFile) throws IOException {

        System.out.println(multipartFile);
        String fileName = multipartFile.getName();

        String uploadDir = "server/activity_bank/79";

        saveFile(uploadDir, fileName, multipartFile);

        return "79/" + fileName;
    }

    /**
     * creates the directory for all new images
     * @param uploadDir
     * @param fileName
     * @param multipartFile
     * @throws IOException
     */
    public void saveFile(String uploadDir, String fileName,
                         MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.
                getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.
                    REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " +
                    fileName, ioe);
        }
    }

}
