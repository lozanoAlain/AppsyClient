/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import entities.Resource;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import entities.User;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.ws.rs.core.GenericType;
import restful.ResourceFactory;
import restful.ResourceInterface;

/**
 * FXML Controller class
 *
 * @author Matteo Fernández
 */
public class ResourcesController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Resource> tableViewResource;
    @FXML
    private TableColumn<Resource, String> columTittle;
    @FXML
    private TableColumn<Resource, String> columLink;
    @FXML
    private TableColumn<Resource, Enum> columPsycho;
    @FXML
    private TableColumn<Resource, String> columDiagnose;
    @FXML
    private TextField txtTittle;
    @FXML
    private TextField txtLink;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<Enum> comboPsycho;
    @FXML
    private TextField txtDiagnose;
    @FXML
    private Button btnReport;
    @FXML
    private Label lblResource;
    private Stage stage;
    private static final Logger logger = Logger.getLogger("package.class");
    private ResourceInterface resourceManager;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root The parent object with the parent window loaded on it.
     */
    public void initStage(Parent root) {

        //Initializes the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Logger.getLogger(ResourcesController.class.getName()).log(Level.INFO, "Initializing stage.");

        //The window title
        stage.setTitle("ResourceWindow");

        //The window (ResourceWindow) is not resizable.
        stage.setResizable(false);

        //Ask for permission when the users closes the application.
        stage.setOnCloseRequest((event) -> {
            this.exitApplicationWhenXPressed(event);
        });

        //The search button (btnSearch) field is focused.
        stage.setOnShowing(this::handleOnWindow);

        //Add button (btnAdd), modify button (btnModify) and delete button (btnDelete) are disabled.
        btnAdd.isDisable();
        btnModify.isDisable();
        btnDelete.isDisable();

        //set table data
        columTittle.setCellValueFactory(new PropertyValueFactory<>("tittle"));
        columDiagnose.setCellValueFactory(new PropertyValueFactory<>("diagnose"));
        columLink.setCellValueFactory(new PropertyValueFactory<>("link"));
        columPsycho.setCellValueFactory(new PropertyValueFactory<>("psychologist"));
        
        resourceManager = ResourceFactory.getResourceManager();

        GenericType<List<Resource>> resourceListType = new GenericType<List<Resource>>() {
        };
        ObservableList<Resource> resourceData = FXCollections.observableArrayList((List<Resource>) resourceManager.findAll(resourceListType));
        tableViewResource.setItems((ObservableList<Resource>) resourceData);

        //handlers button
        btnBack.setOnAction(this::handleButtonBack);
        btnReport.setOnAction(this::handleButtonReport);
        btnAdd.setOnAction(this::handleButtonAdd);
        btnModify.setOnAction(this::handleButtonModify);
        btnDelete.setOnAction(this::handleButtonDelete);

        //handlers texts
        txtTittle.textProperty().addListener(this::handleTxtTittle);

        //some tooltips to help the user
        btnBack.setTooltip(new Tooltip("Click to exit"));
        btnReport.setTooltip(new Tooltip("Click to show the report"));
        btnAdd.setTooltip(new Tooltip("Click to add a row"));
        btnDelete.setTooltip(new Tooltip("Click to delete a row"));
        btnModify.setTooltip(new Tooltip("Click to modify a row"));

        //Shows stage
        stage.show();
    }

    public void initWhenPsychologist(User user) {
        //The psychologist combo box (comboPsycho) is enabled until they write 
        // something in the other fields. If so, the psychologist combo box 
        // (comboPsycho) is disabled and only the add button (btnAdd) is enabled.
        if (!txtTittle.getText().isEmpty() || !txtDiagnose.getText().isEmpty() || !txtLink.getText().isEmpty()) {
            comboPsycho.isDisable();
            btnSearch.isDisable();
            btnModify.isDisable();
            btnDelete.isDisable();
        }
    }

    public void initWhenClient(User user) {
        //Only the search button (btnSearch) and back button (btnBack) are enabled. 
        //The client is not able to do any CRUD action but read.
        txtLink.isDisable();
        btnModify.isDisable();
        btnDelete.isDisable();
        btnAdd.isDisable();
    }

    /**
     * Focus the search button.
     *
     * @param event The event that handles the main windows to request the focus
     * on the btnSearch.
     */
    public void handleOnWindow(WindowEvent event) {
        //The search button (btnSearch) field is focused.
        btnSearch.requestFocus();
    }

    /**
     * When the user clicks the button, it opens a window with the report, and
     * they can download it.
     *
     * @param event
     */
    private void handleButtonReport(ActionEvent event) {

    }

    /**
     *
     * @param event
     */
    private void handleButtonDelete(ActionEvent event) {

    }

    /**
     *
     * @param event
     */
    private void handleTxtTittle(Observable obs) {

    }

    /**
     *
     * @param event
     */
    private void handleButtonModify(ActionEvent event) {

    }

    /**
     *
     * @param event
     */
    private void handleButtonAdd(ActionEvent event) {

    }

    /**
     * The method that opens the Sign In window and sends the user
     *
     * @param user The user that is send to the Sign In window
     */
    private void handleButtonBack(ActionEvent event) {

        /* switch (user.getEnumPrivilege()) {
        case ADMIN:
        break;
        case PSYCHOLOGIST:
        break;
        case CLIENT:
        break;
        
        }*/
 /*     try {
        //Opens the Welcome window
        //  FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
        //   Parent root = (Parent) loader.load();
        
        //Gets Welcome window controller
        //   WelcomeController welcomeController = ((WelcomeController) loader.getController());
        
        welcomeController.setStage(stage);
        
        Logger.getLogger(WelcomeController.class.getName()).log(Level.INFO, "Initializing stage.");
        welcomeController.initStage(root);
        welcomeController.initWhenSignUp(user);
        
        } catch (IOException ex) {
        Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    /**
     * Put the confirmation alert on the button exit of the application
     *
     * @param event The event that handles the exit button above the stage.
     */
    private void exitApplicationWhenXPressed(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setHeaderText(null);
        alert.setTitle("Quit application");
        alert.setContentText(String.format("Are you sure do you want to exit?"));
        alert.initOwner(stage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        if (res.isPresent()) {
            if (res.get().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        }
    }

}
