/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.AppointmentManager;

/**
 *
 * @author HP
 */
public class AppointmentWindowController {
    @FXML
    private ImageView imgCerebro;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField txtSelect;
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
    
    public void initStage (Parent root){
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Appointments");
        stage.setResizable(false);
        
        AppointmentManager appointmentManager = new AppointmentManager();
        
        tblDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tblPsychologist.setCellValueFactory(new PropertyValueFactory<>("psychologist"));
        tblDiagnose.setCellValueFactory(new PropertyValueFactory<>("diagnose"));
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList(appointmentManager.findAll());
        tblAppointment.setItems(appointments);
        
        stage.show();
    }
    
}
