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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
 * This window shows all the psychologist in one table and lets the user add a
 * psychologist modifie a selected psychologist and delete a selected
 * psychologist. Also lets the user find a psychologist by the full name or the
 * login. And finally lets the user have a report of all the psychologist
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
    @FXML
    MenuItem menuItemModify;
    @FXML
    MenuItem menuItemDelete;

    private int idSelected = 0;
    private Stage stage = new Stage();

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    PsychologistInterface interfacePsychologist;
    UserInterface userInterface;
    ObservableList<Psychologist> psychologists;
    private static final Logger LOGGER = Logger.getLogger(PsychologistWindowController.class.getName());
    TableView tableViewPsychoTableView = tablePsychologist;

    /**
     * This method initialize the window and sets the values for the table and
     * the colums of the table, and also sets the componets disability and their
     * visibility
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
            menuItemModify.setDisable(true);
            menuItemDelete.setDisable(true);

            Scene scene = new Scene(root);
            interfacePsychologist = PsychologistFactory.createPsychologistRestful();
            userInterface = UserFactory.createUsersRestful();
            getStage().setScene(scene);
            getStage().setResizable(false);

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

            tablePsychologist.getSelectionModel().selectedItemProperty().addListener(this::handleUserSelectionChanged);
            //Adds the handler for the components of the window
            btnAdd.setOnAction(this::handleButtonAdd);
            btnSearch.setOnAction(this::handleButtonSearch);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
            btnReport.setOnAction(this::handleButtonReport);
            comboSearch.setValue("All");
            comboSearch.getItems().addAll("All", new Separator(), "Name",
                    "Email");
            comboSearch.setTooltip(new Tooltip("Select the search criteria"));
            //Shows the window
            getStage().show();
        } catch (BusinessLogicException ex) {
            //Catch in case the find all psychologist fails
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //Catch in case the server is down
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText("Error connecting to the server");
            errorCreatingThePsychologist.show();
        }
    }

    /**
     * This is the handle for the table when the selected row changes
     *
     * @param observableValue
     * @param oldValue the old selec
     * @param newValue
     */
    public void handleUserSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {
            //If the user selects a row we can delete or modify that psychologist and the add button is disable to help the user
            btnAdd.setDisable(true);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            menuItemModify.setDisable(false);
            menuItemDelete.setDisable(false);

        } else {
            //If the user deselects a row the modify and delete button are disable and the add button is avariable for the user
            btnAdd.setDisable(false);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            menuItemModify.setDisable(true);
            menuItemDelete.setDisable(true);

        }
    }

    /**
     * This handler is for the button modified we select a row from the table
     * and opens the PsychologistProfileController window with the psychologist
     * data
     *
     * @param event
     */
    public void handleButtonModify(ActionEvent event) {
        try {
            //Here we get the psychologist selected id from the table
            idSelected = tablePsychologist.getSelectionModel().getSelectedItem().getId();
            //Here we initialize the PsychologistProfileController window with the id of the psychologist
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PsychologistProfile.fxml"));
            Stage stagePsychologistProfile = new Stage();

            Parent root = (Parent) loader.load();

            PsychologistProfileController psychologistModifyProfileController = ((PsychologistProfileController) loader.getController());

            psychologistModifyProfileController.setStage(stagePsychologistProfile);

            stagePsychologistProfile.initModality(Modality.APPLICATION_MODAL);
            stagePsychologistProfile.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(PsychologistProfileController.class.getName()).log(Level.INFO, "Initializing stage.");

            psychologistModifyProfileController.setPsychologistWindowController(this);
            psychologistModifyProfileController.initStage(root, idSelected);

        } catch (NullPointerException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This is the handler for the delete button we select a row from the table
     * and we delete that psychologist by the id
     *
     * @param event
     */
    public void handleButtonDelete(ActionEvent event) {
        try {
            //Here we get the psychologist selected id from the table
            idSelected = tablePsychologist.getSelectionModel().getSelectedItem().getId();
            //We show the user a comfirmation alert to delete 
            Alert alertDeletePsychologist = new Alert(AlertType.CONFIRMATION);
            alertDeletePsychologist.setHeaderText("Confirmation");
            alertDeletePsychologist.setContentText("Are you sure you want to delete the psychologist?");
            Optional<ButtonType> action = alertDeletePsychologist.showAndWait();
            interfacePsychologist = PsychologistFactory.createPsychologistRestful();
            if (action.get() != ButtonType.OK) {
                //Doesn´t want to delete the user we show an alert showing that we didnt delete the psychologist
                Alert alertDeletePsychologistCancel = new Alert(AlertType.INFORMATION);
                alertDeletePsychologistCancel.setHeaderText("Confirmation");
                alertDeletePsychologistCancel.setContentText("The psychologist hasnt been deleted");
                alertDeletePsychologistCancel.show();
            } else {
                //The user agress with the delete process and deletes the psychologist
                interfacePsychologist.removePsychologist(String.valueOf(idSelected));
                //We delete the psychologist from the table and we update teh table
                tablePsychologist.getItems().remove(tablePsychologist.getSelectionModel().getSelectedIndex());
                tablePsychologist.refresh();
            }
        } catch (NotFoundException ex) {
            //In case the user id is not found in the database
            Alert psychologistNotFound = new Alert(Alert.AlertType.INFORMATION);
            psychologistNotFound.setHeaderText("Psychologist not found");
            psychologistNotFound.setContentText("The psychologist cant be founded ");
            psychologistNotFound.show();
        } catch (BusinessLogicException ex) {
            //In case the server thows an error
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //In case the server is down
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText("Error connecting to the server");
            errorCreatingThePsychologist.show();
        }
    }

    /**
     * This handler is for the add button it opens the
     * PsychologistProfileController
     *
     * @param event
     */
    public void handleButtonAdd(ActionEvent event) {
        try {
            psychologists.clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PsychologistProfile.fxml"));
            Stage stagePsychologistProfile = new Stage();
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            PsychologistProfileController psychologistModifyProfileController = ((PsychologistProfileController) loader.getController());

            psychologistModifyProfileController.setStage(stagePsychologistProfile);
            stagePsychologistProfile.initModality(Modality.APPLICATION_MODAL);
            stagePsychologistProfile.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(PsychologistProfileController.class.getName()).log(Level.INFO, "Initializing stage.");
            psychologistModifyProfileController.setPsychologistWindowController(this);
            psychologistModifyProfileController.initStage(root, 0);

        } catch (IOException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientErrorException ex) {
            Logger.getLogger(PsychologistWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //In case the server is down
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText("Error connecting to the server");
            errorCreatingThePsychologist.show();
        }

    }

    /**
     * This handler is for the button search we get the type of search and we
     * show the psychologist found
     *
     * @param event
     */
    public void handleButtonSearch(ActionEvent event) {
        try {
            psychologists.clear();
            //we get the search criteria
            String selectedSearch = comboSearch.getSelectionModel().getSelectedItem().toString();
            //We switch the search by the criteria
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
            //We show in the table the result of the search

            tablePsychologist.setItems(psychologists);
            tablePsychologist.refresh();

        } catch (NullPointerException e) {
            //In case the there are no psychologist found by the data introduced by the user
            Alert alertDeletePsychologistCancel = new Alert(AlertType.INFORMATION);
            alertDeletePsychologistCancel.setHeaderText("Select criteria");
            alertDeletePsychologistCancel.setContentText("Select a criteria to search a psychologist");
            alertDeletePsychologistCancel.show();
        } catch (EmptyFieldException ex) {
            //In case the textfield to search the psychologist is empty
            Alert emptyField = new Alert(AlertType.INFORMATION);
            emptyField.setHeaderText("Field empty");
            emptyField.setContentText("The search field cant be empty");
            emptyField.show();
        } catch (BusinessLogicException ex) {
            //In case the server thorws and error
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (Exception ex) {
            //In case the server is down
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText("Error connecting to the server");
            errorCreatingThePsychologist.show();
        }

    }

    /**
     * The method to chech if the search textfield is empty
     *
     * @param txt the TextField
     * @throws EmptyFieldException
     */
    public void checkTextFieldisEmpty(TextField txt) throws EmptyFieldException {
        if (txtFieldSearch.getText().trim().isEmpty()) {
            txt.requestFocus();
            throw new EmptyFieldException();
        }
    }

    /**
     * This handler is for the report button it opens a report with the table
     * data and generates a report to download
     *
     * @param event
     */
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
    /**
     * This method is used to refresh the table in other windows
     */
    public void refrescarTabla() {
        try {
            tablePsychologist.getItems().clear();
            ObservableList<Psychologist> psychologists = FXCollections.observableArrayList(interfacePsychologist.findAllPsychologist());
            tablePsychologist.setItems(psychologists);
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
