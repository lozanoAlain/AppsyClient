/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Application.ApplicationAppsy;
import entities.Appointment;
import entities.Client;
import entities.Psychologist;
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
public class PsychologistWindowControllerIT extends ApplicationTest {

    private TextField txtUsername;
    private TextField txtPassword;
    private TextField txtFullName;
    private TextField txtMail;
    private TextField txtOffice;
    private TextField txtSpezialitation;
    private TextField txtFieldSearch;

    private Button btnLogin;
    private Hyperlink linkPsychologist;
    private ComboBox comboBox;
    private ComboBox<String> comboPsychologist;
    private DatePicker datePicker;
    private TableView<Psychologist> tablePsychologist;
    private TableColumn tblDate;
    private TableColumn tblPsychologist;
    private TableColumn tblDiagnose;
    private Button btnBack;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;
    private Button btnSearch;
    private Client client = new Client();

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationAppsy.class);
    }

    @Test
    public void testA_SignIn() {
        txtUsername = lookup("#txtUsername").query();
        clickOn(txtUsername);
        txtUsername.setText("admin");
        txtPassword = lookup("#txtPassword").query();
        clickOn(txtPassword);
        txtPassword.setText("abcd*1234");
        btnLogin = lookup("#btnLogin").query();
        clickOn(btnLogin);

        linkPsychologist = lookup("#lblManagePsychologists").query();
        clickOn(linkPsychologist);

    }

    @Test
    public void testB_InitialState() {
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnAdd", isEnabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

    }

    @Test
    public void testC_LoadPsychologistWindow() {
        verifyThat("#tablePsychologist", isVisible());
    }
    @Test
    public void testD_DeletePsychologist() {
        try {
            tablePsychologist = lookup("#tablePsychologist").queryTableView();
            btnDelete = lookup("#btnDelete").query();
            int rowCountBefore = tablePsychologist.getItems().size();
            clickOn("Sigmund Freud");
            clickOn(btnDelete);
            verifyThat("Are you sure you want to delete the psychologist?", isVisible());
            clickOn("Aceptar");
            Thread.sleep(1000);
            int rowCountAfter = tablePsychologist.getItems().size();
            System.out.println("delete" + rowCountAfter);
            System.out.println(rowCountBefore);
            assertNotEquals(rowCountAfter, rowCountBefore);
            assertTrue(tablePsychologist.getItems().stream()
                    .filter(psychologist -> psychologist.getFullName().equalsIgnoreCase("Sigmund Freud")).count() == 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(AppointmentWindowControllerIT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testE_AddPsychologist() {
        try {
            press(KeyCode.CONTROL);
            clickOn("Lev Vygotsky");
            release(KeyCode.CONTROL);
            tablePsychologist = lookup("#tablePsychologist").queryTableView();
            btnAdd = lookup("#btnAdd").query();
            int rowCountBefore = tablePsychologist.getItems().size();
            clickOn(btnAdd);
            verifyThat("#profile", isVisible());
            txtFullName = lookup("#txtFullName").query();
            txtUsername = lookup("#txtUsername").query();
            txtMail = lookup("#txtMail").query();
            txtPassword = lookup("#txtPassword").query();
            txtOffice = lookup("#txtOffice").query();
            txtSpezialitation = lookup("#txtSpezialitation").query();
            
            clickOn(txtFullName);
            write("Lozano Isasi");
            clickOn(txtUsername);
            write("lozano");
            clickOn(txtMail);
            write("lozanoIsasi@gmail.com");
            clickOn(txtPassword);
            write("abcd*1234");
            clickOn(txtOffice);
            write("Urduliz");
            clickOn(txtSpezialitation);
            write("Anxiety");
            clickOn("Create");
            Thread.sleep(1000);
            int rowCountAfter = tablePsychologist.getItems().size();
            assertNotEquals(rowCountAfter, rowCountBefore);
            assertTrue(tablePsychologist.getItems().stream()
                    .filter(psychologist -> psychologist.getFullName().equalsIgnoreCase("Lozano Isasi")).count() > 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(AppointmentWindowControllerIT.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Test
    public void testH_EditPsychologist() {
        tablePsychologist = lookup("#tablePsychologist").queryTableView();
        btnModify = lookup("#btnModify").query();
        clickOn("Lev Vygotsky");
        clickOn(btnModify);
        txtFullName = lookup("#txtFullName").query();
        txtUsername = lookup("#txtUsername").query();
        txtMail = lookup("#txtMail").query();
        txtPassword = lookup("#txtPassword").query();
        txtOffice = lookup("#txtOffice").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();

        int usernameLength = txtUsername.getText().length();
        clickOn(txtPassword);
        write("aa");
        clickOn(txtUsername);
        eraseText(usernameLength);
        clickOn(txtUsername);
        write("Isasi");
        int mailLength = txtMail.getText().length();
        clickOn(txtMail);
        eraseText(mailLength);
        clickOn(txtMail);
        write("isasi@gmail.com");

        //Button modify
        clickOn("Edit");

        assertTrue(tablePsychologist.getItems().stream()
                .filter(psychologist -> psychologist.getEmail().equalsIgnoreCase("isasi@gmail.com")).count() > 0);

    }
    @Test
    public void testI_SearchNull(){
        comboBox = lookup("#comboSearch").query();
        clickOn(comboBox);
        clickOn("Name");
        btnSearch = lookup("#btnSearch").query();
        clickOn(btnSearch);
        verifyThat("The search field cant be empty", isVisible());
        clickOn("Aceptar");
    }
    @Test
    public void testJ_SearchUser(){
        tablePsychologist = lookup("#tablePsychologist").queryTableView();
        comboBox = lookup("#comboSearch").query();
        clickOn(comboBox);
        clickOn("Name");
        txtFieldSearch = lookup("#txtFieldSearch").query();
        clickOn(txtFieldSearch);
        write("Lev Vygotsky");
        btnSearch = lookup("#btnSearch").query();
        clickOn(btnSearch);
        sleep(100);
        assertTrue(tablePsychologist.getItems().stream()
                .filter(psychologist -> psychologist.getFullName().equalsIgnoreCase("Lev Vygotsky")).count() > 0);
    }
    @Test
    public void testK_EditPsychologistErrorUsername() {
        comboBox = lookup("#comboSearch").query();
        clickOn(comboBox);
        clickOn("All");
        btnSearch = lookup("#btnSearch").query();
        clickOn(btnSearch);
        tablePsychologist = lookup("#tablePsychologist").queryTableView();
        btnModify = lookup("#btnModify").query();
        clickOn("William James");
        clickOn(btnModify);
        txtFullName = lookup("#txtFullName").query();
        txtUsername = lookup("#txtUsername").query();
        txtMail = lookup("#txtMail").query();
        txtPassword = lookup("#txtPassword").query();
        txtOffice = lookup("#txtOffice").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();

        int usernameLength = txtUsername.getText().length();
        clickOn(txtPassword);
        write("aa");
        clickOn(txtUsername);
        eraseText(usernameLength);
        clickOn(txtUsername);
        write("alain");

        //Button modify
        clickOn("Edit");
        verifyThat("Server Error", isVisible());
        clickOn("Aceptar");
    }
    @Test
    public void testL_EditPsychologistErrorMail() {       
        txtFullName = lookup("#txtFullName").query();
        txtUsername = lookup("#txtUsername").query();
        txtMail = lookup("#txtMail").query();
        txtPassword = lookup("#txtPassword").query();
        txtOffice = lookup("#txtOffice").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();

        
        int mailLength = txtMail.getText().length();
        clickOn(txtMail);
        eraseText(mailLength);
        clickOn(txtMail);
        write("alain@gmail.com");

        //Button modify
        clickOn("Edit");
        verifyThat("Server Error", isVisible());
        clickOn("Aceptar");
    }
    
      
}
