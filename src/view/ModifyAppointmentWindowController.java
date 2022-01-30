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
import java.util.Date;
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
import logic.AppointmentFactory;
import logic.AppointmentInterface;
import logic.PsychologistFactory;
import logic.PsychologistInterface;

/**
 *
 * @author HP
 */
public class ModifyAppointmentWindowController {

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
    private Appointment selectedAppointment = null;
    private Appointment modifiedAppointment = null;
    private AppointmentWindowController appointmentWindowController;

    @FXML
    private ImageView imgCerebro;
    @FXML
    private Label lblAppointment;
    @FXML
    private Label lblPsychologist;
    @FXML
    private ComboBox<Psychologist> comboPsychologist;
    @FXML
    private Label lblDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnModify;

    public void initModifyWhenClient(Parent root, Client client, Appointment selectedAppointment) {
        

        try {
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add Appointment");
            stage.setResizable(false);

            this.selectedAppointment = selectedAppointment;
            this.client = client;

            comboPsychologist.requestFocus();

            psychologistInterface = PsychologistFactory.createPsychologistRestful();

            ObservableList<Psychologist> psychologists
                    = FXCollections.observableArrayList(psychologistInterface.findAllPsychologist());
            comboPsychologist.setItems(psychologists);

            comboPsychologist.setOnAction(this::handleComboPsychologist);
            datePicker.setOnAction(this::handleDatePicker);

            //String myFormattedDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            /*Date date = Date.from(localDate.atStartOfDay().toInstant(OffsetDateTime.now().getOffset()));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String dateFormat = format.format(date);
            date = format.parse(dateFormat);*/
            modifiedAppointment = new Appointment();

            btnBack.setOnAction(this::handleButtonBack);
            btnModify.setOnAction(this::handleButtonModify);

            stage.show();
            LOGGER.info("ModifyAppointmentWindow opened correctly");
        
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
        Date date = java.util.Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());

        modifiedAppointment.setDate(date);
    }

    private void handleComboPsychologist(ActionEvent event) {

        Psychologist psychologist = (Psychologist) comboPsychologist.getSelectionModel().getSelectedItem();

        modifiedAppointment.setPsychologist(psychologist);

    }

    private void handleButtonModify(ActionEvent event) {
        try {
            modifiedAppointment.setAppointmentId(selectedAppointment.getAppointmentId());
            modifiedAppointment.setClient(client);
            modifiedAppointment.setDiagnose(selectedAppointment.getDiagnose());
            modifiedAppointment.setNumAppointment(selectedAppointment.getNumAppointment());
            modifiedAppointment.setPrice(selectedAppointment.getPrice());
            appointmentInterface = AppointmentFactory.createAppointmentInterface();
            appointmentInterface.edit(modifiedAppointment, String.valueOf(selectedAppointment.getAppointmentId()));

            Alert alertAppointmentModified = new Alert(Alert.AlertType.INFORMATION);
            alertAppointmentModified.setTitle("MODIFY APPOINTMENT");
            alertAppointmentModified.setHeaderText("User modified correctly");
            alertAppointmentModified.show();

            appointmentWindowController.refrescarTabla();
            LOGGER.info("Appointment modified correctly");
            getStage().close();

        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(ModifyAppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error modifiyng the appointment");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there was a problem modifiying de appointment");
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
