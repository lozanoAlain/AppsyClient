/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Application.ApplicationAppsy;
import entities.Appointment;
import entities.Client;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import logic.ClientFactory;
import logic.ClientInterface;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;

/**
 *
 * @author Ilia Consuegra
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppointmentWindowControllerIT extends ApplicationTest {

    private TextField txtUsername;
    private TextField txtPassword;
    private Button btnLogin;
    private Hyperlink linkManage;
    private ComboBox comboBox;
    private ComboBox<String> comboPsychologist;
    private DatePicker datePicker;
    private TableView<Appointment> tblAppointment;
    private TableColumn tblDate;
    private TableColumn tblPsychologist;
    private TableColumn tblDiagnose;
    private Button btnBack;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;
    private Button btnSearch;
    private Client client = new Client();

    /*
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentWindow.fxml"));

        //Creates a new stage
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        AppointmentWindowController appointmentWindowController = ((AppointmentWindowController) loader.getController());

        ClientInterface clientManager = null;
        clientManager = ClientFactory.createClientRestful();
        client.setId(1);
        client = clientManager.find(String.valueOf(client.getId()));
        appointmentWindowController.setClient(client);
        //Set the stage that we already created to the sign up controller
        appointmentWindowController.initStage(root);
    }*/
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationAppsy.class);
    }


    @Test
    public void testA_SignIn() {
        txtUsername = lookup("#txtUsername").query();
        clickOn(txtUsername);
        txtUsername.setText("alain");
        txtPassword = lookup("#txtPassword").query();
        clickOn(txtPassword);
        txtPassword.setText("abcd*1234");
        btnLogin = lookup("#btnLogin").query();
        clickOn(btnLogin);

        linkManage = lookup("#linkManage").query();
        clickOn(linkManage);

    }

    @Test
    public void testB_InitialState() {
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnSearch", isEnabled());

    }

    @Test
    public void testC_LoadAppointmentWindow() {
        verifyThat("#tblAppointment", isVisible());
    }

    /*
    @Test
    public void testD_SearchByPsychologist() {
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
    public void testE_SearchByDate() {
        clickOn(comboBox);
        clickOn("Date");
        verifyThat("#datePicker", isEnabled());
        verifyThat("#comboPsychologist", isDisabled());
        clickOn(datePicker);
        clickOn(comboPsychologist);
        clickOn(btnSearch);
        verifyThat("Sigmund Freud", isVisible());
    }
     */
    @Test
    public void testF_DeleteAppointment() {
        try {
            tblAppointment = lookup("#tblAppointment").queryTableView();
            btnDelete = lookup("#btnDelete").query();
            int rowCountBefore = tblAppointment.getItems().size();
            clickOn("Sigmund Freud");
            clickOn(btnDelete);
            verifyThat("¿are you sure you want to delete the appointment?", isVisible());
            clickOn("Aceptar");
            Thread.sleep(1000);
            int rowCountAfter = tblAppointment.getItems().size();
            System.out.println("delete" + rowCountAfter);
            System.out.println(rowCountBefore);
            assertNotEquals(rowCountAfter, rowCountBefore);
            assertTrue(tblAppointment.getItems().stream()
                    .filter(appointment -> appointment.getPsychologist().getFullName().equalsIgnoreCase("Sigmund Freud")).count() == 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(AppointmentWindowControllerIT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testG_AddAppointment() {
        try {
            tblAppointment = lookup("#tblAppointment").queryTableView();
            btnAdd = lookup("#btnAdd").query();
            int rowCountBefore = tblAppointment.getItems().size();
            clickOn(btnAdd);
            
            press(KeyCode.F4).release(KeyCode.F4);
            clickOn("Sigmund Freud");

            //Select the date
            press(KeyCode.TAB).release(KeyCode.TAB);
            press(KeyCode.TAB).release(KeyCode.TAB);
            press(KeyCode.TAB).release(KeyCode.TAB);
            press(KeyCode.F4).release(KeyCode.F4);
            press(KeyCode.UP).release(KeyCode.UP);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            verifyThat("#panel", isVisible());
            //Press Add Button
            clickOn("Create");
            /*type(KeyCode.TAB);
            type(KeyCode.TAB);
            type(KeyCode.TAB);
            type(KeyCode.ENTER);*/
            verifyThat("User added correctly", isVisible());
            clickOn("Aceptar");
            Thread.sleep(1000);
            int rowCountAfter = tblAppointment.getItems().size();
            assertNotEquals(rowCountAfter, rowCountBefore);
            assertTrue(tblAppointment.getItems().stream()
                    .filter(appointment -> appointment.getPsychologist().getFullName().equalsIgnoreCase("Sigmund Freud")).count() > 0);
            //assertTrue("The course value does not match with the one of the filter", tblTeachers.getItems().stream().
            //filter(teacher -> teacher.getTeacherCourse().getName().equalsIgnoreCase(tfFilter.getText())).count() > 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(AppointmentWindowControllerIT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testH_EditAppointment() {       
        tblAppointment = lookup("#tblAppointment").queryTableView();
        btnModify = lookup("#btnModify").query();
        clickOn("Sigmund Freud");
        
        clickOn(btnModify);
        
        press(KeyCode.F4).release(KeyCode.F4);
        clickOn("Lev Vygotsky");
        
        verifyThat("#panel2", isVisible());
 
        //Button modify
        clickOn("Edit");
        verifyThat("User modified correctly", isVisible());
        clickOn("Aceptar");

        assertTrue(tblAppointment.getItems().stream()
                .filter(appointment -> appointment.getPsychologist().getFullName().equalsIgnoreCase("Lev Vygotsky")).count() > 0);
        
    }

}
