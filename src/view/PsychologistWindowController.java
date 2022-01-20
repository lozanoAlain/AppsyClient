/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Psychologist;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.PsychologistFactory;
import logica.PsychologistInterface;
import restful.PsychologistRestFul;
import restful.User;

/**
 *
 * @author Alain Lozano
 */
public class PsychologistWindowController {

    @FXML
    private ComboBox comboSearch;
    @FXML
    private TextField txtFieldSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Psychologist> tablePsychologist;
    @FXML
    private TableColumn<Psychologist, String> columnLogin;
    @FXML
    private TableColumn<Psychologist, String> columnEmail;
    @FXML
    private TableColumn<Psychologist, String> columnFullName;
    @FXML
    private TableColumn<Psychologist, String> columnStatus;
    @FXML
    Button btnAdd;
    @FXML
    Button btnModify;
    @FXML
    Button btnDelete;
    @FXML
    Button btnReport;
    @FXML
    Button btnBack;

    public void initStage(Parent root) {
        try {
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
            PsychologistInterface interfacePsychologist = PsychologistFactory.createPsychologistRestful();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            //tfLogin.focusedProperty().addListener(this::focusChanged);
            //Set factories for cell values in users table columns.
            columnLogin.setCellValueFactory(
                    new PropertyValueFactory<>("login"));
            columnEmail.setCellValueFactory(
                    new PropertyValueFactory<>("email"));
            columnFullName.setCellValueFactory(
                    new PropertyValueFactory<>("fullName"));
            columnStatus.setCellValueFactory(
                    new PropertyValueFactory<>("enumStatus"));
            //Create an obsrvable list for users table.
            ObservableList<Psychologist> psychologists = FXCollections.observableArrayList(interfacePsychologist.findAllPsychologist());
            //Set table model.
            tablePsychologist.setItems(psychologists);
            if(psychologists.isEmpty()){
                btnReport.isDisable();
                btnSearch.isDisable();
            }
            //Show window.
            stage.show();

            tablePsychologist.getSelectionModel().selectedItemProperty().addListener(this::handleUserSelectionChanged);
            btnBack.setOnAction(value);

        } catch (Exception ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUserSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if(newValue!=null){
            Psychologist psychologist = (Psychologist)newValue;
            String loginSelected = psychologist.getLogin();
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

}
