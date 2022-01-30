/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entities.Appointment;
import entities.Client;
import entities.Psychologist;
import exceptions.BusinessLogicException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import logic.AppointmentInterface;
import logic.PsychologistFactory;
import logic.PsychologistInterface;

/**
 *
 * @author HP
 */
public class AddAppointmentWindowController {

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
    private AppointmentInterface appointmentInterface;
    private PsychologistInterface psychologistInterface;
    private Client client = null;
    private Appointment appointment;
    private AppointmentWindowController appointmentWindowController;

    @FXML
    private ImageView imgCerebro;
    @FXML
    private Label lblAppointment;
    @FXML
    private Label lblPsychologist;
    @FXML
    private ComboBox comboPsychologist;
    @FXML
    private Label lblDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;

    public void initAddWhenClient(Client client, Parent root) {
        
        try {
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add Appointment");
            stage.setResizable(false);

            btnBack.setOnAction(this::handleButtonBack);
            btnAdd.setOnAction(this::handleButtonAdd);

            this.client = client;

            psychologistInterface = PsychologistFactory.createPsychologistRestful();
            ObservableList<Psychologist> psychologists
                    = FXCollections.observableArrayList(psychologistInterface.findAllPsychologist());
            comboPsychologist.setItems(psychologists);

            appointment.setClient(client);

            comboPsychologist.setOnAction(this::handleComboPsychologist);
            datePicker.setOnAction(this::handleDatePicker);

            stage.show();
            LOGGER.info("AddAppointmentWindow opened correctly");
        } catch (OperationNotSupportedException | BusinessLogicException ex) {
            LOGGER.severe("Error with the server");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            LOGGER.severe("Error with the server");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        
    }

    private void handleDatePicker(ActionEvent event) {
        datePicker.setPromptText("dd/mm/yyyy");
        LocalDate localDate = datePicker.getValue();
        java.util.Date date = java.util.Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        appointment.setDate(date);
    }

    private void handleComboPsychologist(ActionEvent event) {
        Psychologist psychologist = (Psychologist) comboPsychologist.getSelectionModel().getSelectedItem();

        appointment.setPsychologist(psychologist);

    }

    private void handleButtonAdd(ActionEvent event) {
        try {
            appointmentInterface.create(appointment);

            Alert alertAppointmentAdded = new Alert(Alert.AlertType.INFORMATION);
            alertAppointmentAdded.setTitle("SIGN UP");
            alertAppointmentAdded.setHeaderText("User added correctly");
            alertAppointmentAdded.show();

            appointmentWindowController.refrescarTabla();
            LOGGER.info("Appointment added correctly");
            getStage().close();
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error adding the appointment");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there was a problem adding de appointment");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void handleButtonBack(ActionEvent event) {
        getStage().close();
    }

    /**
     * @return the appointmentWindowController
     */
    public AppointmentWindowController getAppointmentWindowController() {
        return appointmentWindowController;
    }

    /**
     * @param appointmentWindowController the apointmentWindowContrller to set
     */
    public void setAppointmentWindowController(AppointmentWindowController appointmentWindowController) {
        this.appointmentWindowController = appointmentWindowController;
    }

}
