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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

    public void initStage(Parent root) {
        try {
            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Appointments");
            stage.setResizable(false);

            comboPsychologist.setVisible(false);
            datePicker.setVisible(false);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);

            btnAdd.setOnAction(this::handleButtonAdd);
            btnBack.setOnAction(this::handleButtonBack);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
            btnReport.setOnAction(this::handleButtonReport);

            appointmentInterface = AppointmentFactory.createAppointmentInterface();
            psychologistInterface = PsychologistFactory.createPsychologistRestful();

            initWhenClient(client);

            stage.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initWhenClient(Client client) throws OperationNotSupportedException {
        try {
            this.setClient(client);

            tblDate.setCellFactory(column -> {
                TableCell<Appointment, Date> cell = new TableCell<Appointment, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

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

            tblDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tblPsychologist.setCellValueFactory(new PropertyValueFactory<>("psychologist"));
            tblDiagnose.setCellValueFactory(new PropertyValueFactory<>("diagnose"));

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

            //ObservableList<Appointment> selectedItems = selectionModel.getSelectedItems();
            //if there is a row selected, the btnModify and btnDelete will be enabled
            tblAppointment.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChange);
            ObservableList<String> items = FXCollections.observableArrayList();
            items.addAll("psychologist", "date");

            comboBox.setItems(items);
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

    private void handleButtonSearch(Event event) {
        try {
            if (comboPsychologist.isVisible()) {
                String psychologistName = comboPsychologist.getSelectionModel().getSelectedItem();
                psychologist = psychologistInterface.findPsychologistByFullName(psychologistName);
                ObservableList<Appointment> appointmentsbyPsychologist
                        = FXCollections.observableArrayList(appointmentInterface.findAppointmentsOfClientByPsychologist(String.valueOf(psychologist.getId()), String.valueOf(getClient().getId())));

                tblAppointment.setItems(appointmentsbyPsychologist);
                tableView = new TableView<>(appointmentsbyPsychologist);
            } else {
                Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                ObservableList<Appointment> appointmentsbyDate;
                appointmentsbyDate = FXCollections.observableArrayList(appointmentInterface.findAppointmentsByDate(simpleDateFormat.format(date).toString()));

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

    private void handleComboBox(Event event) {
        if (comboBox.getValue().toString().equalsIgnoreCase("psychologist")) {
            try {
                txtSelect.setVisible(false);
                comboPsychologist.setVisible(true);
                comboPsychologist.setPromptText("Psychologists");
                ObservableList<Psychologist> psychologists = FXCollections.observableArrayList(psychologistInterface.findAllPsychologist());

                ObservableList<String> psychologistsName = FXCollections.observableArrayList();
                for (Psychologist p : psychologists) {
                    psychologistsName.add(p.getFullName());
                }

                comboPsychologist.setItems(psychologistsName);
            } catch (Exception ex) {
                Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (comboBox.getValue().toString().equalsIgnoreCase("date")) {
            txtSelect.setVisible(false);
            datePicker.setVisible(true);

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
        } else {
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    private void handleButtonModify(ActionEvent event) {

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

            Logger
                    .getLogger(AddAppointmentWindowController.class
                            .getName()).log(Level.INFO, "Initializing stage.");
            Appointment selectedAppointment = (Appointment) tblAppointment.getSelectionModel().getSelectedItem();

            modifyAppointmentController.setAppointmentWindowController(this);
            modifyAppointmentController.initModifyWhenClient(root, getClient(), selectedAppointment);
        } catch (IOException ex) {
            Logger.getLogger(AppointmentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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

    private void handleButtonDelete(ActionEvent event) {
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

    private void handleButtonAdd(ActionEvent event) {
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

            Logger
                    .getLogger(AddAppointmentWindowController.class
                            .getName()).log(Level.INFO, "Initializing stage.");
            addAppointmentController.initAddWhenClient(getClient(), root);

        } catch (IOException ex) {
            Logger.getLogger(AppointmentWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleButtonBack(ActionEvent event) {
        getStage().close();
    }

    private void handleButtonReport(ActionEvent event) {
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
