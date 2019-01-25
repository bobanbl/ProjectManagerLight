package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class ProjectDetailMainController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button projectTeamButton;

    @FXML
    private DatePicker projectStartDate;

    @FXML
    private ComboBox<?> projectStatusComboBox;

    @FXML
    private DatePicker projectFinishDate;

    @FXML
    private Label projectID;

    @FXML
    void projectTeamButton(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
