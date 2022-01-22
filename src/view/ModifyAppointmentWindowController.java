/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Psychologist;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.AppointmentManager;
import logic.PsychologistManager;

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
    private AppointmentManager appointmentManager;
    private PsychologistManager psychologistManager;
    
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
    private Button btnModify;

    void initStage(Parent root) {
        try {
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add Appointment");
            stage.setResizable(false);
            
            appointmentManager = new AppointmentManager();
            
            ObservableList<Psychologist> psychologists
                    = FXCollections.observableArrayList(psychologistManager.findAllPsychologist());
            comboPsychologist.setItems(psychologists);
            Psychologist psychologist = (Psychologist) comboPsychologist.getSelectionModel().getSelectedItem();
            LocalDate dateSelected = datePicker.getValue();
            
            btnBack.setOnAction(this::handleButtonBack);
            btnModify.setOnAction(this::handleButtonModify);
        } catch (Exception ex) {
            Logger.getLogger(ModifyAppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void handleButtonModify(ActionEvent event){
        
    }
    
    private void handleButtonBack(ActionEvent event){
        getStage().close(); 
    }
}
