/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Psychologist;
import exceptions.BusinessLogicException;
import exceptions.EmptyFieldException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import logic.PsychologistFactory;
import logic.PsychologistInterface;
import logic.UserFactory;
import logic.UserInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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

    private int idSelected = 0;
    Stage stage = new Stage();
    PsychologistInterface interfacePsychologist;
    UserInterface userInterface;
    ObservableList<Psychologist> psychologists;
    private static final Logger LOGGER = Logger.getLogger(PsychologistWindowController.class.getName());

    public void initStage(Parent root) {
        try {
            btnDelete.setDisable(true);
            btnModify.setDisable(true);

            Scene scene = new Scene(root);
            interfacePsychologist = PsychologistFactory.createPsychologistRestful();
            userInterface = UserFactory.createUsersRestful();
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
            psychologists = FXCollections.observableArrayList(interfacePsychologist.findAllPsychologist());
            //Set table model.
            tablePsychologist.setItems(psychologists);
            TableViewSelectionModel<Psychologist> selectionModel = tablePsychologist.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);
            if (psychologists.size() == 0) {
                btnReport.setDisable(true);
            }
            //Show window.

            tablePsychologist.getSelectionModel().selectedItemProperty().addListener(this::handleUserSelectionChanged);

            btnAdd.setOnAction(this::handleButtonAdd);
            btnSearch.setOnAction(this::handleButtonSearch);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
            btnReport.setOnAction(this::handleButtonReport);
            comboSearch.setValue("All");
            comboSearch.getItems().addAll("All", new Separator(), "Name",
                    "Email");
            comboSearch.setTooltip(new Tooltip("Select the search criteria"));
            stage.show();
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUserSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnAdd.setDisable(true);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);

        } else {
            btnAdd.setDisable(false);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    public void handleButtonModify(ActionEvent event) {
        try {
            idSelected = tablePsychologist.getSelectionModel().getSelectedItem().getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PsychologistProfile.fxml"));
            Stage stagePsychologistProfile = new Stage();

            Parent root = (Parent) loader.load();

            PsychologistProfileController psychologistModifyProfileController = ((PsychologistProfileController) loader.getController());

            psychologistModifyProfileController.setStage(stagePsychologistProfile);

            stagePsychologistProfile.initModality(Modality.WINDOW_MODAL);
            stagePsychologistProfile.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(PsychologistProfileController.class.getName()).log(Level.INFO, "Initializing stage.");

            psychologistModifyProfileController.initStage(root, idSelected);
            psychologists = FXCollections.observableArrayList(interfacePsychologist.findAllPsychologist());

        } catch (IOException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void handleButtonDelete(ActionEvent event) {
        try {
            idSelected = tablePsychologist.getSelectionModel().getSelectedItem().getId();
            Alert alertDeletePsychologist = new Alert(AlertType.CONFIRMATION);
            alertDeletePsychologist.setHeaderText("Confirmation");
            alertDeletePsychologist.setContentText("Are you sure you want to delete the psychologist?");
            Optional<ButtonType> action = alertDeletePsychologist.showAndWait();
            interfacePsychologist = PsychologistFactory.createPsychologistRestful();
            if (action.get() != ButtonType.OK) {
                //Doesn´t want to exit
                Alert alertDeletePsychologistCancel = new Alert(AlertType.INFORMATION);
                alertDeletePsychologistCancel.setHeaderText("Confirmation");
                alertDeletePsychologistCancel.setContentText("The psychologist hasnt been deleted");
                alertDeletePsychologistCancel.show();
            } else {
                interfacePsychologist.removePsychologist(String.valueOf(idSelected));
                tablePsychologist.getItems().remove(tablePsychologist.getSelectionModel().getSelectedIndex());
                tablePsychologist.refresh();
            }
        } catch (NotFoundException ex) {
            Alert psychologistNotFound = new Alert(Alert.AlertType.INFORMATION);
            psychologistNotFound.setHeaderText("Psychologist not found");
            psychologistNotFound.setContentText("The psychologist cant be founded ");
            psychologistNotFound.show();
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleButtonAdd(ActionEvent event) {
        try {
            psychologists.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PsychologistProfile.fxml"));
            Stage stagePsychologistProfile = new Stage();
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            PsychologistProfileController psychologistModifyProfileController = ((PsychologistProfileController) loader.getController());

            psychologistModifyProfileController.setStage(stagePsychologistProfile);
            stagePsychologistProfile.initModality(Modality.WINDOW_MODAL);
            stagePsychologistProfile.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(PsychologistProfileController.class.getName()).log(Level.INFO, "Initializing stage.");
            psychologistModifyProfileController.initStage(root, 0);

        } catch (IOException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientErrorException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void handleButtonSearch(ActionEvent event) {
        try {
            psychologists.clear();
            String selectedSearch = comboSearch.getSelectionModel().getSelectedItem().toString();

            switch (selectedSearch) {
                case "Name":
                    checkTextFieldisEmpty(txtFieldSearch);
                    psychologists = FXCollections.observableArrayList(interfacePsychologist.findPsychologistByFullName(txtFieldSearch.getText()));
                    break;
                case "Email":
                    checkTextFieldisEmpty(txtFieldSearch);
                    psychologists = FXCollections.observableArrayList(interfacePsychologist.findPsychologistByMail(txtFieldSearch.getText()));
                    break;
                case "All":
                    psychologists = FXCollections.observableArrayList(interfacePsychologist.findAllPsychologist());
                    break;

            }

            tablePsychologist.setItems(psychologists);
            tablePsychologist.refresh();

        } catch (NullPointerException e) {
            Alert alertDeletePsychologistCancel = new Alert(AlertType.INFORMATION);
            alertDeletePsychologistCancel.setHeaderText("Select criteria");
            alertDeletePsychologistCancel.setContentText("Select a criteria to search a psychologist");
            alertDeletePsychologistCancel.show();
        } catch (EmptyFieldException ex) {
            Alert emptyField = new Alert(AlertType.INFORMATION);
            emptyField.setHeaderText("Field empty");
            emptyField.setContentText("The search field cant be empty");
            emptyField.show();
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (Exception ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void checkTextFieldisEmpty(TextField txt) throws EmptyFieldException {
        if (txtFieldSearch.getText().trim().isEmpty()) {
            txt.requestFocus();
            throw new EmptyFieldException();
        }
    }

    public void handleButtonReport(ActionEvent event) {
        try {
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/ReportPsychologist.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Psychologist>) this.tablePsychologist.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void refrescarTabla() {
        try {
            tablePsychologist.getItems().clear();
            ObservableList<Psychologist> appointments = FXCollections.observableArrayList(interfacePsychologist.findAllPsychologist());
            tablePsychologist.setItems(appointments);
            tablePsychologist.refresh();
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        }

    }

}
