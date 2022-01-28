/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.Psychologist;
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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import logic.PsychologistFactory;
import logic.PsychologistInterface;
import logic.ResourceFactory;
import logic.ResourceInterface;

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
    private TableColumn<Resource, String> columPsycho;
    @FXML
    private TableColumn<Resource, Date> columDate;
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
    private ComboBox<Psychologist> comboPsycho;
    @FXML
    private TextField txtDiagnose;
    @FXML
    private Button btnReport;
    @FXML
    private Label lblResource;
    private Stage stage;
    private static final Logger logger = Logger.getLogger("package.class");
    private ResourceInterface resourceManager;
    private PsychologistInterface psychologistManager;
    @FXML
    public DatePicker datePicker;

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
     * @throws java.lang.Exception
     */
    public void initStage(Parent root) throws Exception {

        try {
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
            
                
            //Obtains the layout containing the menu bar from the scene node graph
            HBox hBoxMenu= (HBox)root.getChildrenUnmodifiable().get(0);
            //Get the menu bar from the children of the layout got before
            MenuBar menuBar= (MenuBar)hBoxMenu.getChildren().get(0);
            //Get the second menu from the menu bar
            Menu menuHelp=menuBar.getMenus().get(1);
            //Add a listener for the showing property that fires the action event
            //on the first menu item of that menu
            menuHelp.showingProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue) {
                        menuHelp.getItems().get(0).fire();
                    }
                }
            );
            
            //The search button (btnSearch) field is focused.
            stage.setOnShowing(this::handleOnWindow);

            //Add button (btnAdd), modify button (btnModify) and delete button (btnDelete) are disabled.
            btnAdd.isDisable();
            btnModify.isDisable();
            btnDelete.isDisable();

            //set data to combobox
            psychologistManager = PsychologistFactory.createPsychologistRestful();

            List<Psychologist> psycho = FXCollections.observableArrayList(psychologistManager.findAllPsychologist());

            comboPsycho.setItems((ObservableList<Psychologist>) psycho);

            //set table data
            columTittle.setCellValueFactory(new PropertyValueFactory<>("tittle"));
            columDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
            columLink.setCellValueFactory(new PropertyValueFactory<>("link"));
            columPsycho.setCellValueFactory(new PropertyValueFactory<>("psychologist"));

            //formatting the date
            columDate.setCellFactory(column -> {
                TableCell<Resource, Date> cell = new TableCell<Resource, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        empty = (item == null);
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(format.format(item));
                        }
                    }
                };
                return cell;
            });

            resourceManager = ResourceFactory.getResourceManager();

            GenericType<List<Resource>> resourceListType = new GenericType<List<Resource>>() {
            };

            ObservableList<Resource> resourceData = FXCollections.observableArrayList((List<Resource>) resourceManager.findAll(resourceListType));
            tableViewResource.setItems((ObservableList<Resource>) resourceData);

            tableViewResource.getSelectionModel().selectedItemProperty().addListener(this::handleResourceTableSelectionChange);

            //handlers button
            btnBack.setOnAction(this::handleButtonBack);
            btnReport.setOnAction(this::handleButtonReport);
            btnAdd.setOnAction(this::handleButtonAdd);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);

            //some tooltips to help the user
            btnBack.setTooltip(new Tooltip("Click to exit"));
            btnReport.setTooltip(new Tooltip("Click to show the report"));
            btnAdd.setTooltip(new Tooltip("Click to add a row"));
            btnDelete.setTooltip(new Tooltip("Click to delete a row"));
            btnModify.setTooltip(new Tooltip("Click to modify a row"));

            //Shows stage
            stage.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(ResourcesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param observable
     * @param oldVaue
     * @param newValue is when you click on a row
     */
    public void handleResourceTableSelectionChange(ObservableValue observable,
            Object oldVaue, Object newValue) {
        //When i click the labels are filled
        //If the new value for the observable propery is not null, fill the data
        //else delete the fields data
        if (newValue != null) {
            Resource resource = tableViewResource.getSelectionModel().getSelectedItem();
            txtTittle.setText(resource.getTittle());
            txtLink.setText(resource.getLink());
            comboPsycho.getSelectionModel().select(resource.getPsychologist());
            btnSearch.setDisable(true);
            btnDelete.setDisable(false);
            btnModify.setDisable(false);
        } else {
            txtTittle.setText("");
            txtLink.setText("");
            comboPsycho.getSelectionModel().clearSelection();
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
            btnSearch.setDisable(false);
            btnAdd.setDisable(false);
        }

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
        try {
            //Get selected club data from table view model
            Resource resource = ((Resource) tableViewResource.getSelectionModel().getSelectedItem());

            //Ask user for confirmation on delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the selected Resource?\n"
                    + "This option can't be reversed.",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result;
            result = alert.showAndWait();

            //If OK to seletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete user from server side
                ResourceFactory.getResourceManager().remove(resource.getId().toString());

                //Clear selection and refresh table view 
                tableViewResource.getSelectionModel().clearSelection();
                resourceManager = ResourceFactory.getResourceManager();

                GenericType<List<Resource>> resourceListType = new GenericType<List<Resource>>() {
                };

                ObservableList<Resource> resourceData = FXCollections.observableArrayList((List<Resource>) resourceManager.findAll(resourceListType));
                tableViewResource.setItems((ObservableList<Resource>) resourceData);
            }
        } catch (ClientErrorException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
            Logger.getLogger(ResourcesController.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

    }

    /**
     *
     * @param event
     */
    private void handleButtonModify(ActionEvent event) {
        //Get selected data from table view.
        Resource resource = ((Resource) tableViewResource.getSelectionModel().getSelectedItem());

        //Write the new data
        resource.setTittle(txtTittle.getText());
        resource.setLink(txtLink.getText());
        resource.setPsychologist(comboPsycho.getSelectionModel().getSelectedItem());

        ResourceFactory.getResourceManager().edit(resource, resource.getId().toString());

        //Clear selection and refresh table view 
        tableViewResource.getSelectionModel().clearSelection();
        resourceManager = ResourceFactory.getResourceManager();

        GenericType<List<Resource>> resourceListType = new GenericType<List<Resource>>() {
        };

        ObservableList<Resource> resourceData = FXCollections.observableArrayList((List<Resource>) resourceManager.findAll(resourceListType));
        tableViewResource.setItems((ObservableList<Resource>) resourceData);
        tableViewResource.refresh();
    }

    /**
     *
     * @param event
     */
    private void handleButtonAdd(ActionEvent event) {
        //New resource
        Resource resource = new Resource();

        //put data
        resource.setTittle(txtTittle.getText());
        resource.setLink(txtLink.getText());
        resource.setPsychologist(comboPsycho.getSelectionModel().getSelectedItem());

        resource.setDateAdded(Date.from(Instant.now()));

        GenericType<List<Resource>> resourceListType = new GenericType<List<Resource>>() {
        };

        ObservableList<Resource> resourceData = FXCollections.observableArrayList((List<Resource>) resourceManager.findAll(resourceListType));
        tableViewResource.setItems((ObservableList<Resource>) resourceData);

        //Create it
        ResourceFactory.getResourceManager().create(resource);

        //Update the table
        resourceData = FXCollections.observableList(ResourceFactory.getResourceManager().findAll(resourceListType));
        tableViewResource.setItems(resourceData);

        tableViewResource.refresh();
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
