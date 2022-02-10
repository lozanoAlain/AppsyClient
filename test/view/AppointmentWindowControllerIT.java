/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Application.ApplicationAppsy;
import entities.Appointment;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import static org.testfx.matcher.base.NodeMatchers.isVisible;

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

    
    
    @Test
    public void testD_SearchByPsychologist() {
        comboBox = lookup("#comboBox").query();
        btnSearch = lookup("#btnSearch").query();
        clickOn(comboBox);
        clickOn("psychologist");
        verifyThat("#comboPsychologist", isEnabled());
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.F4).release(KeyCode.F4);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn(btnSearch);
        verifyThat("Sigmund Freud", isVisible());
    }

   
    @Test
    public void testF_SearchByDate() {
        try {
            comboBox = lookup("#comboBox").query();
            btnSearch = lookup("#btnSearch").query();
            clickOn(comboBox);
            clickOn("date");
            verifyThat("#datePicker", isEnabled());
            press(KeyCode.TAB).release(KeyCode.TAB);
            press(KeyCode.F4).release(KeyCode.F4);
            press(KeyCode.UP).release(KeyCode.UP);
            Thread.sleep(1000);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
            clickOn(btnSearch);
            verifyThat("Tabla sin contenido", isVisible());
        } catch (InterruptedException ex) {
            Logger.getLogger(AppointmentWindowControllerIT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testE_DeleteAppointment() {
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
        clickOn("Wilhelm Wundt");
        
        verifyThat("#panel2", isVisible());
 
        //Button modify
        clickOn("Edit");
        verifyThat("User modified correctly", isVisible());
        clickOn("Aceptar");

        assertTrue(tblAppointment.getItems().stream()
                .filter(appointment -> appointment.getPsychologist().getFullName().equalsIgnoreCase("Wilhelm Wundt")).count() > 0);
        
    }

}
