/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;

/**
 *
 * @author Usuario
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PsychologistProfileControllerIT extends ApplicationTest {

    private Label lblFullName;
    private TextField txtFullName;
    private Label lblUsername;
    private TextField txtUsername;
    private Label lblMail;
    private TextField txtMail;
    private Label lblPassword;
    private PasswordField txtPassword;
    private Label lblOffice;
    private TextField txtOffice;
    private Label lblSpecialization;
    private TextField txtSpezialitation;
    private Label lblFullNameError;
    private Label lblUsernameError;
    private Label lblMailError;
    private Label lblPasswordError;
    private Label lblOfficeError;
    private Label lblSpecializationError;
    private Button btnBack;
    private Button btnModify;
    private Button btnAdd;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PsychologistWindow.fxml"));

        //Creates a new stage
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());

        //Set the stage that we already created to the sign up controller
        psychologistWindowController.initStage(root);
    }

    @Test
    public void testA_UserAdd() {
        btnAdd = lookup("#btnAdd").query();
        clickOn("Add");
        lblFullName = lookup("#lblFullName").query();
        txtFullName = lookup("#txtFullName").query();
        lblUsername = lookup("#lblUsername").query();
        txtUsername = lookup("#txtUsername").query();
        lblMail = lookup("#lblMail").query();
        txtMail = lookup("#txtMail").query();
        lblPassword = lookup("#lblPassword").query();
        txtPassword = lookup("#txtPassword").query();
        lblOffice = lookup("#lblOffice").query();
        txtOffice = lookup("#txtOffice").query();
        lblSpecialization = lookup("#lblSpecialization").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();
        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        lblMailError = lookup("#lblMailError").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        lblOfficeError = lookup("#lblOfficeError").query();
        lblSpecializationError = lookup("#lblSpecializationError").query();
        btnBack = lookup("#btnBack").query();
        btnModify = lookup("#btnModify").query();
        btnAdd = lookup("#btnAdd").query();

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
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.ENTER);
        verifyThat("lozano", isVisible());

    }

    @Test
    public void testC_UsernameExist() {
        btnAdd = lookup("#btnAdd").query();
        clickOn(btnAdd);
        lblFullName = lookup("#lblFullName").query();
        txtFullName = lookup("#txtFullName").query();
        lblUsername = lookup("#lblUsername").query();
        txtUsername = lookup("#txtUsername").query();
        lblMail = lookup("#lblMail").query();
        txtMail = lookup("#txtMail").query();
        lblPassword = lookup("#lblPassword").query();
        txtPassword = lookup("#txtPassword").query();
        lblOffice = lookup("#lblOffice").query();
        txtOffice = lookup("#txtOffice").query();
        lblSpecialization = lookup("#lblSpecialization").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();
        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        lblMailError = lookup("#lblMailError").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        lblOfficeError = lookup("#lblOfficeError").query();
        lblSpecializationError = lookup("#lblSpecializationError").query();
        btnBack = lookup("#btnBack").query();
        btnModify = lookup("#btnModify").query();
        btnAdd = lookup("#btnAdd").query();
        clickOn(txtFullName);
        write("Lozano Isasi");
        clickOn(txtUsername);
        write("alain");
        clickOn(txtMail);
        write("maxyell2214@gmail.com");
        clickOn(txtPassword);
        write("abcd*1234");
        clickOn(txtOffice);
        write("Urduliz");
        clickOn(txtSpezialitation);
        write("Anxiety");
        clickOn(btnAdd);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.ENTER);
        verifyThat("Server Error", isVisible());
    }

    @Test
    public void testD_MailExist() {
        btnAdd = lookup("#btnAdd").query();
        clickOn(btnAdd);
        lblFullName = lookup("#lblFullName").query();
        txtFullName = lookup("#txtFullName").query();
        lblUsername = lookup("#lblUsername").query();
        txtUsername = lookup("#txtUsername").query();
        lblMail = lookup("#lblMail").query();
        txtMail = lookup("#txtMail").query();
        lblPassword = lookup("#lblPassword").query();
        txtPassword = lookup("#txtPassword").query();
        lblOffice = lookup("#lblOffice").query();
        txtOffice = lookup("#txtOffice").query();
        lblSpecialization = lookup("#lblSpecialization").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();
        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        lblMailError = lookup("#lblMailError").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        lblOfficeError = lookup("#lblOfficeError").query();
        lblSpecializationError = lookup("#lblSpecializationError").query();
        btnBack = lookup("#btnBack").query();
        btnModify = lookup("#btnModify").query();
        btnAdd = lookup("#btnAdd").query();
        clickOn(txtFullName);
        write("Lozano Isasi");
        clickOn(txtUsername);
        write("lozano");
        clickOn(txtMail);
        write("alain@gmail.com");
        clickOn(txtPassword);
        write("abcd*1234");
        clickOn(txtOffice);
        write("Urduliz");
        clickOn(txtSpezialitation);
        write("Anxiety");
        clickOn(btnAdd);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.ENTER);
        verifyThat("Server Error", isVisible());
    }


@Test
    public void testE_ModifyClient(){
        btnModify = lookup("#btnModify").query();
        doubleClickOn("lozanoIsasi@gmail.com");
        clickOn(btnModify);
        lblFullName = lookup("#lblFullName").query();
        txtFullName = lookup("#txtFullName").query();
        lblUsername = lookup("#lblUsername").query();
        txtUsername = lookup("#txtUsername").query();
        lblMail = lookup("#lblMail").query();
        txtMail = lookup("#txtMail").query();
        lblPassword = lookup("#lblPassword").query();
        txtPassword = lookup("#txtPassword").query();
        lblOffice = lookup("#lblOffice").query();
        txtOffice = lookup("#txtOffice").query();
        lblSpecialization = lookup("#lblSpecialization").query();
        txtSpezialitation = lookup("#txtSpezialitation").query();
        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        lblMailError = lookup("#lblMailError").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        lblOfficeError = lookup("#lblOfficeError").query();
        lblSpecializationError = lookup("#lblSpecializationError").query();
        btnBack = lookup("#btnBack").query();
       
        btnAdd = lookup("#btnAdd").query();
        int usernameLength=txtUsername.getText().length();
        clickOn(txtPassword);
        write("aa");
        clickOn(txtUsername);
        eraseText(usernameLength);
        clickOn(txtUsername);
        write("Isasi");
        int mailLength=txtMail.getText().length();
        clickOn(txtMail);
        eraseText(mailLength);
        clickOn(txtMail);
        write("isasi@gmail.com");
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.ENTER);
        verifyThat("Isasi", isVisible());
    }

}
