/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author HP
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppointmentWindowControllerTest extends ApplicationTest {
    
    private Button btnSearch;
    private ComboBox comboBox;
    private TextField txtSelect;
    private ComboBox<String> comboPsychologist;
    private DatePicker datePicker;
    private TableView tblAppointment;
    private TableColumn tblDate;
    private TableColumn tblPsychologist;
    private TableColumn tblDiagnose;
    private Button btnBack;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentWindow.fxml"));

        //Creates a new stage
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        AppointmentWindowController appointmentWindowController = ((AppointmentWindowController) loader.getController());

        //Set the stage that we already created to the sign up controller
        appointmentWindowController.initStage(root);
    }
    
    @Before
    public void initializeLookUp() {

        btnSearch = lookup("#btnSearch").query();
        comboBox = lookup("#comboBox").query();
        txtSelect = lookup("#txtSelect").query();
        comboPsychologist = lookup("#comboPsychologist").query();
        datePicker = lookup("#datePicker").query();
        tblAppointment = lookup("#tblAppointment").query();
        btnBack = lookup("#btnBack").query();
        btnAdd = lookup("#btnAdd").query();
        btnModify = lookup("#btnModify").query();
        btnDelete = lookup("#btnDelete").query();
        
        release(KeyCode.CONTROL);

    }
    
    @Test
    public void testA_InitialState() {
        verifyThat("#txtSearch", isEnabled());
        verifyThat("#comboPsychologist", isDisabled());
        verifyThat("#datePicker", isDisabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnSearch", isEnabled());

    }
    
    @Test
    public void testB_SearchByPsychologist() {
        clickOn(comboBox);
        clickOn("Psychologist");
        verifyThat("#comboPsychologist", isEnabled());
        verifyThat("#datePicker", isDisabled());
        clickOn(comboPsychologist);
        clickOn(comboPsychologist.getItems().get(0));
        clickOn(btnSearch);        
        verifyThat("Sigmund Freud", isVisible());
    }
    
    @Test
    public void testC_SearchByDate() {
        clickOn(comboBox);
        clickOn("Date");
        verifyThat("#datePicker", isEnabled());
        verifyThat("#comboPsychologist", isDisabled());
        clickOn(datePicker);
        clickOn(comboPsychologist);
        clickOn(btnSearch);        
        verifyThat("Sigmund Freud", isVisible());
    }
    
}
