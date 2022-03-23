package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class AdminPanelCtrl {
        @FXML
        public TableView<Activity> table;

        @FXML
        public TableColumn<Activity,String> id;

        @FXML
        public TableColumn<Activity, String> title;

        @FXML
        public TableColumn<Activity, String> consumptionInWh;

        @FXML
        public TableColumn<Activity, String> imagePath;

        @FXML
        public Button add;

        @FXML
        public Button delete;

        private final ServerUtils server;

        private final MainCtrl mainCtrl;


        /**
         * A constructor for the leaderboardSceneCtrl class.
         * @param server
         * @param mainCtrl
         */
        @Inject
        public AdminPanelCtrl(ServerUtils server, MainCtrl mainCtrl) {
            this.server = server;
            this.mainCtrl = mainCtrl;
        }


        /**
         * Fills the table with activities queried from the database.
         */
        public void load() {
            table.getItems().clear();
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            consumptionInWh.setCellValueFactory(
                    new PropertyValueFactory<>("consumptionInWh"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            imagePath.setCellValueFactory(
                    new PropertyValueFactory<>("imagePath"));
            List<Activity> activities = server.getActivities();
            for (int i = 0; i < activities.size(); i++) {
                table.getItems().add(activities.get(i));
            }
        }

        /**
         * Return to start scene.
         */

        public void goToStartScene(){
            mainCtrl.showStartScreen();
        }

    /**
     * go to the add screen
     */
    public void goToAdd(){
            mainCtrl.showAdd();
        }

    /**
     * go to the edit scene
     */
    public void goToEdit(){
            try{
                Activity activity = table.getSelectionModel().getSelectedItem();
                mainCtrl.showEdit(activity);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * deletes an activity from the list
     */
    public void goToDelete(){
            try{
                Activity activity = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(table.getSelectionModel().
                        getSelectedItem());
                server.deleteActivity(activity.id);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * deletes all activities
     */
    public void deleteAll(){
            table.getItems().clear();
            server.deleteAll();
        }

    /**
     * add all activities
     */
    public void addAll() {
            if(server.getActivities().isEmpty()) {
                for(Activity activity : server.getJson()) {
                    server.addEntry(activity);
                }
                load();
            }
        }

    /**
     * make the image cells clickable
     * to be able to see the image
     */
    public void initialize() {
            imagePath.setCellFactory(tc -> {
                TableCell<Activity, String> cell =
                        new TableCell<Activity, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };
                cell.setCursor(Cursor.HAND);
                cell.setOnMouseClicked(event -> {
                    if (!cell.isEmpty()) {
                        mainCtrl.displayImage(cell.getText());
                    }
                });
                return cell;
            });
        }
}

