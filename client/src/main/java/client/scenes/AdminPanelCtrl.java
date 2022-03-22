package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Score;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class AdminPanelCtrl {
        @FXML
        public TableView table;

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
         * Fills the leaderboard with scores queried from the database.
         */
        public void load() {
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            consumptionInWh.setCellValueFactory(new PropertyValueFactory<>("consumptionInWh"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            imagePath.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
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

    public void initialize() {
        imagePath.setCellFactory(tc -> {
            TableCell<Activity, String> cell = new TableCell<Activity, String>() {
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

