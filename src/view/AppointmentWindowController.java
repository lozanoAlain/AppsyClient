/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Appointment;
import entities.Client;
import entities.Psychologist;
import exceptions.BusinessLogicException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.ClientErrorException;
import logic.AppointmentFactory;
import logic.AppointmentInterface;
import logic.PsychologistFactory;
import logic.PsychologistInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * This window shows all the appointments in a table and lets the client add an
 * appointment, modifiy a selected appointment and delete it. Also lets the user
 * find an appointment by the psychologist name and the date. And finally lets
 * the user have a report of all the appointments.
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

    private static final Logger LOGGER = Logger.getLogger(AppointmentWindowController.class.getName());
    private AppointmentInterface appointmentInterface;
    private PsychologistInterface psychologistInterface;
    private TableView tableView;
    private Client client;
    private Psychologist psychologist;

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private ImageView imgCerebro;
    @FXML
    private Button btnSearch;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField txtSelect;
    @FXML
    private ComboBox<String> comboPsychologist;
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
    @FXML
    private Button btnReport;
    @FXML
    MenuItem menuItemModify;
    @FXML
    MenuItem menuItemDelete;

    /**
     * This method initialize the AppointmentWindow and establishes the visibility and 
     * availability of the components.
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Appointments");
            stage.setResizable(false);

            //Establish the visibility and availability of the components
            comboPsychologist.setVisible(false);
            datePicker.setVisible(false);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            menuItemModify.setDisable(true);
            menuItemDelete.setDisable(true);

            //Handlers are defined with method references
            btnAdd.setOnAction(this::handleButtonAdd);
            btnBack.setOnAction(this::handleButtonBack);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
            btnReport.setOnAction(this::handleButtonReport);

            appointmentInterface = AppointmentFactory.createAppointmentInterface();
            psychologistInterface = PsychologistFactory.createPsychologistRestful();

            //Call to the initWhenClient method with the client as input parameter
            initWhenClient(client);

            //The window is shown
            stage.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sets the values for the table and the colums of the table.
     * It also sets the comboBox data.
     * @param client
     * @throws OperationNotSupportedException 
     */
    public void initWhenClient(Client client) throws OperationNotSupportedException {
        try {
            this.setClient(client);

            //The date formated is inserted on the table column
            tblDate.setCellFactory(column -> {
                TableCell<Appointment, Date> cell = new TableCell<Appointment, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                    /**
                     * This method checks if the date is empty and if it is not,
                     * it inserted the date on the table
                     * @param item
                     * @param empty 
                     */
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            this.setText(format.format(item));

                        }
                    }
                };

                return cell;
            });

            //These are the columns of the table
            tblDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tblPsychologist.setCellValueFactory(new PropertyValueFactory<>("psychologist"));
            tblDiagnose.setCellValueFactory(new PropertyValueFactory<>("diagnose"));

            //The table is fulled
            ObservableList<Appointment> appointments = FXCollections.observableArrayList(appointmentInterface.findAppointmentsOfClient(String.valueOf(client.getId())));
            tblAppointment.setItems(appointments);
            tableView = new TableView<>(appointments);

            //If the table has no elements to display, a message is shown
            if (appointments.isEmpty()) {
                tableView.setPlaceholder(new Label("No rows to display"));
            }

            // set selection mode to only 1 row
            TableViewSelectionModel<Appointment> selectionModel = tableView.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);

            //Event handleTableSelectionChange management with a listener
            tblAppointment.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChange);
            ObservableList<String> items = FXCollections.observableArrayList();
            items.addAll("psychologist", "date");

            //Fill the comboBox
            comboBox.setItems(items);
            
            //Handlers are defined with method references
            btnSearch.setOnAction(this::handleButtonSearch);
            comboBox.setOnAction(this::handleComboBox);
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error with the server");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * This handler is for the search button. The type of search is selected 
     * and the data of the appointment is displayed.
     * @param event 
     */
    public void handleButtonSearch(Event event) {
        try {
            //The search by psychologist is selected
            if (comboPsychologist.isVisible()) {
                String psychologistName = comboPsychologist.getSelectionModel().getSelectedItem();
                psychologist = psychologistInterface.findPsychologistByFullName(psychologistName);
                ObservableList<Appointment> appointmentsbyPsychologist
                        = FXCollections.observableArrayList(appointmentInterface.findAppointmentsOfClientByPsychologist(String.valueOf(psychologist.getId()), String.valueOf(getClient().getId())));

                //The data is shown
                tblAppointment.setItems(appointmentsbyPsychologist);
                tableView = new TableView<>(appointmentsbyPsychologist);
            } else {
                //The search bydate is selected
                Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                ObservableList<Appointment> appointmentsbyDate;
                appointmentsbyDate = FXCollections.observableArrayList(appointmentInterface.findAppointmentsByDate(simpleDateFormat.format(date).toString()));

                //The data is shown
                tblAppointment.setItems(appointmentsbyDate);
                tableView = new TableView<>(appointmentsbyDate);

            }
        } catch (NullPointerException ex) {
            LOGGER.severe("Criteria error");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select a criteria to search an appointment.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (IllegalArgumentException ex) {
            tableView.setPlaceholder(new Label("No rows to display"));
            Alert alertAppointmentModified = new Alert(Alert.AlertType.INFORMATION);
            alertAppointmentModified.setTitle("INCORRECT DATE");
            alertAppointmentModified.setContentText(ex.getMessage());
            alertAppointmentModified.show();
        } catch (ClientErrorException ex) {

        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error with the server");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * This method changes the view depending on the selection of the combo.
     * @param event 
     */
    private void handleComboBox(Event event) {
        //The search by psychologist is selected 
        if (comboBox.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("psychologist")) {
            try {
                //Set the comboPsychologist visible
                txtSelect.setVisible(false);
                comboPsychologist.setVisible(true);
                comboPsychologist.setPromptText("Psychologists");
                datePicker.setVisible(false);
                
                //Get the list of psychologists
                ObservableList<Psychologist> psychologists = FXCollections.observableArrayList(psychologistInterface.findAllPsychologist());

                //Get a list of the names of the psychologists
                ObservableList<String> psychologistsName = FXCollections.observableArrayList();
                for (Psychologist p : psychologists) {
                    psychologistsName.add(p.getFullName());
                }

                //Set the names into the comboPsychologist
                comboPsychologist.setItems(psychologistsName);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        //The search by date is selected
        } else if (comboBox.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("date")) {
            //Set the datePicker visible
            txtSelect.setVisible(false);
            datePicker.setVisible(true);
            comboPsychologist.setVisible(false);

        //There is noselection
        } else {
            txtSelect.setVisible(true);
            comboPsychologist.setVisible(false);
            datePicker.setVisible(false);
        }

    }

    //if there is a row selected, the btnModify and btnDelete will be enable.
    //if there is not a row selected, the btnModify and btnDelete will be disable.
    /**
     * This is the handle for the table when the selected row changes
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void handleTableSelectionChange(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            //There is a selected row
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            menuItemModify.setDisable(false);
            menuItemDelete.setDisable(false);
        } else {
            //There is not a selected row
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            menuItemModify.setDisable(true);
            menuItemDelete.setDisable(true);
        }
    }

    /**
     * This handler is for the modify button. 
     * It opens the ModifyAppointmentWindow with the data of the appointment 
     * selected.
     * @param event 
     */
    public void handleButtonModify(ActionEvent event) {

        try {
            //Gets the AddAppointmentWindow FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAppointmentClient.fxml"));

            //Creates a new stage
            Stage stageModifyAppointmentWindow = new Stage();
            Parent root = (Parent) loader.load();

            //Gets AddAppointment controller
            ModifyAppointmentWindowController modifyAppointmentController = ((ModifyAppointmentWindowController) loader.getController());

            //Set the stage for the addAppojntmentWindow
            modifyAppointmentController.setStage(stageModifyAppointmentWindow);

            //Opening application as modal
            stageModifyAppointmentWindow.initModality(Modality.APPLICATION_MODAL);
            stageModifyAppointmentWindow.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger
                    .getLogger(AddAppointmentWindowController.class
                            .getName()).log(Level.INFO, "Initializing stage.");
            Appointment selectedAppointment = (Appointment) tblAppointment.getSelectionModel().getSelectedItem();

            //Opens the modify window with the modifyAppointmentController, sending the parent, the client and the selected appointment
            modifyAppointmentController.setAppointmentWindowController(this);
            modifyAppointmentController.initModifyWhenClient(root, getClient(), selectedAppointment);
        } catch (IOException ex) {
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method to refresh the table when returning from add and modify windows
     */
    public void refrescarTabla() {
        try {
            tblAppointment.getItems().clear();
            ObservableList<Appointment> appointments = FXCollections.observableArrayList(appointmentInterface.findAppointmentsOfClient(String.valueOf(client.getId())));
            tblAppointment.setItems(appointments);
            tblAppointment.refresh();
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error with the server");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * This handler is for the delete button. 
     * It deletes the data of the selected appointment.
     * @param event 
     */
    public void handleButtonDelete(ActionEvent event) {
        try {
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
                this.appointmentInterface.removeAppointment(String.valueOf(selectedAppointment.getAppointmentId().getPsychologistId()), String.valueOf(selectedAppointment.getAppointmentId().getClientId()));
                //removes selected appointment from table
                tblAppointment.getItems().remove(selectedAppointment);
                tblAppointment.refresh();

            }
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error deleting the appointment");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there was a problem deleting the appointment");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * This handler is for the add button. 
     * It opens the AddAppointmentWindow to create a new appointment
     * @param event 
     */
    public void handleButtonAdd(ActionEvent event) {
        try {
            //Gets the AddAppointmentClient FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointmentClient.fxml"));

            //Creates a new stage
            Stage stageAddAppointmentClient = new Stage();
            Parent root = (Parent) loader.load();

            //Gets AddAppointment controller
            AddAppointmentWindowController addAppointmentController = ((AddAppointmentWindowController) loader.getController());

            //Set the stage for the addAppojntmentWindow
            addAppointmentController.setStage(stageAddAppointmentClient);

            //Opening application as modal
            stageAddAppointmentClient.initModality(Modality.APPLICATION_MODAL);
            stageAddAppointmentClient.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger
                    .getLogger(AddAppointmentWindowController.class
                            .getName()).log(Level.INFO, "Initializing stage.");
            addAppointmentController.setAppointmentWindowController(this);
            addAppointmentController.initAddWhenClient(getClient(), root);

        } catch (IOException ex) {
            Logger.getLogger(AppointmentWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This handler is for the back button. 
     * It closes this window
     * @param event 
     */
    public void handleButtonBack(ActionEvent event) {
        getStage().close();
    }

    /**
     * This handler is for the report button. 
     * It opens the report of the appointment data
     * @param event 
     */
    public void handleButtonReport(ActionEvent event) {
        try {
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/ReportAppointment.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource
            //implementation
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Psychologist>) this.tblAppointment.getItems());
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
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
