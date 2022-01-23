/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Appointment;
import entities.Client;
import entities.Psychologist;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.AppointmentManager;
import logic.PsychologistManager;

/**
 *
 * @author Ilia Consuegra
 */
public class AppointmentWindowController {

    private Stage stage;

    //Getters and Setters
    /**
     * Gets the stage
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage
     *
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final static Logger logger = Logger.getLogger(AppointmentWindowController.class.getName());
    private AppointmentManager appointmentManager;
    private PsychologistManager psychologistManager;
    private TableView tableView;
    private Client client = null;

    @FXML
    private ImageView imgCerebro;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField txtSelect;
    @FXML
    private ComboBox comboPsychologist;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnBack;
    @FXML
    private TableView tblAppointment;
    @FXML
    private TableColumn tblDate;
    @FXML
    private TableColumn tblPsychologist;
    @FXML
    private TableColumn tblDiagnose;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;

    public void initStage(Parent root) {
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Appointments");
        stage.setResizable(false);

        appointmentManager = new AppointmentManager();

        comboPsychologist.setVisible(false);
        datePicker.setVisible(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        btnAdd.setOnAction(this::handleButtonAdd);
        btnBack.setOnAction(this::handleButtonBack);

        stage.show();
    }

    public void initWhenClient(Client client) {
        this.client = client;
        tblDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblPsychologist.setCellValueFactory(new PropertyValueFactory<>("psychologist"));
        tblDiagnose.setCellValueFactory(new PropertyValueFactory<>("diagnose"));

        ObservableList<Appointment> appointments = FXCollections.observableArrayList(appointmentManager.findAppointmentsByClient(client.getId().toString()));
        tblAppointment.setItems(appointments);
        tableView = new TableView<>(appointments);

        //If the table has no elements to display, a message is shown
        if (appointments.isEmpty()) {
            tableView.setPlaceholder(new Label("No rows to display"));
        }

        // set selection mode to only 1 row        
        TableViewSelectionModel<Appointment> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        //ObservableList<Appointment> selectedItems = selectionModel.getSelectedItems();
        //if there is a row selected, the btnModify and btnDelete will be enabled
        tblAppointment.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChange);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("psychologist", "date");

        comboBox.setItems(items);

        comboBox.setOnAction(this::handleComboBox);
    }

    private void handleComboBox(Event event) {
        if (comboBox.getValue().toString().equalsIgnoreCase("psychologist")) {
            try {
                txtSelect.setVisible(false);
                comboPsychologist.setVisible(true);

                //Set psychologist combo data model.
                ObservableList<Psychologist> psychologists
                        = FXCollections.observableArrayList(psychologistManager.findAllPsychologist());
                comboPsychologist.setItems(psychologists);
                Psychologist psychologist = (Psychologist) comboPsychologist.getSelectionModel().getSelectedItem();
                ObservableList<Appointment> appointmentsbyPsychologist
                        = FXCollections.observableArrayList(appointmentManager.findAppointmentsByPsychologist(psychologist.getId().toString()));
                tblAppointment.setItems(appointmentsbyPsychologist);
                tableView = new TableView<>(appointmentsbyPsychologist);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (comboBox.getValue().toString().equalsIgnoreCase("date")) {
            txtSelect.setVisible(false);
            datePicker.setVisible(true);
            LocalDate dateSelected = datePicker.getValue();
            if (dateSelected == null) {
                tableView.setPlaceholder(new Label("No rows to display"));
            } else {
                ObservableList<Appointment> appointmentsbyDate
                        = FXCollections.observableArrayList(appointmentManager.findAppointmentsByDate(date));
                tblAppointment.setItems(appointmentsbyDate);
                tableView = new TableView<>(appointmentsbyDate);
            }
        } else {
            txtSelect.setVisible(true);
            comboPsychologist.setVisible(false);
            datePicker.setVisible(false);
        }

    }

    //if there is a row selected, the btnModify and btnDelete will be enable.
    //if there is not a row selected, the btnModify and btnDelete will be disable.
    private void handleTableSelectionChange(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnModify.setDisable(false);
            btnDelete.setDisable(false);

            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
        } else {
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    private void handleButtonModify(ActionEvent event) {
        try {
            //Gets the AddAppointmentWindow FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAppontmentWindow.fxml"));

            //Creates a new stage
            Stage stageModifyAppointmentWindow = new Stage();
            Parent root = (Parent) loader.load();

            //Gets AddAppointment controller
            ModifyAppointmentWindowController modifyAppointmentController = ((ModifyAppointmentWindowController) loader.getController());

            //Set the stage for the addAppojntmentWindow
            modifyAppointmentController.setStage(stageModifyAppointmentWindow);

            //Opening application as modal
            stageModifyAppointmentWindow.initModality(Modality.APPLICATION_MODAL);

            Logger.getLogger(AddAppointmentWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            modifyAppointmentController.initStage(root);
            Appointment selectedAppointment = (Appointment) tblAppointment.getSelectionModel().getSelectedItem();

            modifyAppointmentController.initModifyWhenClient(client, selectedAppointment);

        } catch (IOException ex) {
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleButtonDelete(ActionEvent event) {
        //Get selected user data from table view model
        Appointment selectedAppointment = ((Appointment) tblAppointment.getSelectionModel()
                .getSelectedItem());
        //Ask user for confirmation on delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿are you sure you want to delete the appointment?",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            //delete appointment from server side
            this.appointmentManager.remove(selectedAppointment.getAppointmentId().toString());
            //removes selected appointment from table
            tblAppointment.getItems().remove(selectedAppointment);
            tblAppointment.refresh();
        }
    }

    private void handleButtonAdd(ActionEvent event) {
        try {
            //Gets the AddAppointmentWindow FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppontmentWindow.fxml"));

            //Creates a new stage
            Stage stageAddAppointmentWindow = new Stage();
            Parent root = (Parent) loader.load();

            //Gets AddAppointment controller
            AddAppointmentWindowController addAppointmentController = ((AddAppointmentWindowController) loader.getController());

            //Set the stage for the addAppojntmentWindow
            addAppointmentController.setStage(stageAddAppointmentWindow);

            //Opening application as modal
            stageAddAppointmentWindow.initModality(Modality.APPLICATION_MODAL);

            Logger.getLogger(AddAppointmentWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            addAppointmentController.initStage(root);
            addAppointmentController.initAddWhenClient(client);
        } catch (IOException ex) {
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleButtonBack(ActionEvent event) {
        getStage().close();
    }

}
