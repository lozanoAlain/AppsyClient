/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.logging.Logger;
import entities.Appointment;
import entities.AppointmentId;
import entities.Client;
import entities.Psychologist;
import exceptions.BusinessLogicException;
import exceptions.EmptyFieldsException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
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

    private final static Logger LOGGER = Logger.getLogger(AddAppointmentWindowController.class.getName());
    private AppointmentInterface appointmentInterface;
    private PsychologistInterface psychologistInterface;
    private Client client;
    private Appointment appointment;
    private AppointmentWindowController appointmentWindowController;

    @FXML
    private ImageView imgCerebro;
    @FXML
    private Label lblAppointment;
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

            comboPsychologist.requestFocus();
            psychologistInterface = PsychologistFactory.createPsychologistRestful();

            comboPsychologist.setPromptText("Psychologists");
            ObservableList<Psychologist> psychologists
                    = FXCollections.observableArrayList(psychologistInterface.findAllPsychologist());
            comboPsychologist.setItems(psychologists);

            appointment = new Appointment();
            appointment.setClient(client);

            stage.show();
            LOGGER.info("AddAppointmentWindow opened correctly");
        } catch (OperationNotSupportedException | BusinessLogicException ex) {
            LOGGER.severe("Error with the server1");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            LOGGER.severe("Error with the server2");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    private void handleButtonAdd(ActionEvent event) {
        try {

            Psychologist psychologist = new Psychologist();
            psychologist = (Psychologist) comboPsychologist.getSelectionModel().getSelectedItem();
            if(comboPsychologist.getSelectionModel().getSelectedIndex() == -1){
                throw new EmptyFieldsException();
            }
            AppointmentId appointmentId = new AppointmentId();
            appointmentId.setPsychologistId(psychologist.getId());
            appointmentId.setClientId(client.getId());
            //appointment = new Appointment(psychologist, appointmentId);
            appointment.setClient(client);
            appointment.setPsychologist(new Psychologist());

            datePicker.setPromptText("dd/mm/yyyy");
            LocalDate localDate = datePicker.getValue();
            if(localDate == null){
                throw new EmptyFieldsException();
            }
            java.util.Date date = java.util.Date.from(localDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());

            appointment.setDate(date);
            appointment.setAppointmentId(null);
            appointment.setDiagnose("");
            appointment.setPrice(Float.NaN);
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
        } catch (EmptyFieldsException ex) {
            LOGGER.severe("Error adding the appointment");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The fields canont be empty");
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
