package view;

import entities.Psychologist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import view.PsychologistWindowController;

/**
 *
 * @author Alain Lozano
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileWindowControllerIT extends ApplicationTest {

    private Label lblFullName;
    private TextField txtFullName;
    private Label lblUserName;
    private TextField txtUsername;
    private Label lblMail;
    private TextField txtMail;
    private Label lblFullNameError;
    private Label lblUsernameError;
    private Label lblMailError;
    private Label lblPassword;
    private PasswordField txtPassword;
    private Label lblRepeatPassword;
    private PasswordField txtRepeatPassword;
    private Label lblPasswordError;
    private Label lblRepeatPasswordError;
    private Button btnModify;
    private Button btnDelete;
    private Button btnBack;

    private static final String OVERSIZED_TEXT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    /*
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ClientApplication.class);
    }
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileWindow.fxml"));

        //Creates a new stage
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        ProfileWindowController profileWindowController = ((ProfileWindowController) loader.getController());

        //Set the stage that we already created to the sign up controller
        profileWindowController.initStage(root, 1);
    }

    @Before
    public void initializeLookUp() {
        lblFullName = lookup("#lblFullName").query();
        txtFullName = lookup("#txtFullName").query();
        lblUserName = lookup("#lblUserName").query();
        txtUsername = lookup("#txtUsername").query();
        lblMail = lookup("#lblMail").query();
        txtMail = lookup("#txtMail").query();
        lblFullNameError = lookup("#lblFullNameError").query();
        lblUsernameError = lookup("#lblUsernameError").query();
        lblMailError = lookup("#lblMailError").query();
        lblPassword = lookup("#lblPassword").query();
        txtPassword = lookup("#txtPassword").query();
        lblRepeatPassword = lookup("#lblRepeatPassword").query();
        txtRepeatPassword = lookup("#txtRepeatPassword").query();
        lblPasswordError = lookup("#lblPasswordError").query();
        lblRepeatPasswordError = lookup("#lblRepeatPasswordError").query();
        btnModify = lookup("#btnModify").query();
        btnDelete = lookup("#btnDelete").query();
        btnBack = lookup("#btnBack").query();

    }

    @Test
    public void testA_InitialState() {
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnModify", isEnabled());
        verifyThat("#btnDelete", isEnabled());
        verifyThat("#txtFullName", isFocused());
        verifyThat("Alain Lozano", isVisible());
        verifyThat("alain", isVisible());
        verifyThat("alain@gmail.com", isVisible());

    }

    @Ignore
    @Test
    public void testB_TextLongerThan255() {
        cleanAll();
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        textLongerThan255(txtFullName, lblFullNameError);
        textLongerThan255(txtUsername, lblUsernameError);
        textLongerThan255(txtMail, lblMailError);
        textLongerThan255(txtPassword, lblPasswordError);
        textLongerThan255(txtRepeatPassword, lblRepeatPasswordError);
    }

    private void textLongerThan255(TextField txt, Label lbl) {
        clickOn(txt);
        write(OVERSIZED_TEXT);
        verifyThat("#btnModify", isDisabled());
        verifyThat(lbl, isEnabled());
        verifyThat(lbl, hasText("The field is too long (255 character max)."));
        doubleClickOn(txt);
        eraseText(1);

    }

    @Test
    public void testC_BlankSpacesError() {
        clean(txtFullName);
        clickOn(txtFullName);
        write("AAA");
        clickOn(txtUsername);
        sleep(1000);
        verifyThat(btnModify, isDisabled());
        verifyThat(lblFullNameError, hasText("The full name is incomplete."));
        
    }

    @Test
    public void testD_BlankSpacesCorrect() {
        clean(txtFullName);
        clickOn(txtFullName);
        write("AA A");
        clickOn(txtUsername);
        sleep(1000);
        verifyThat(btnModify, isEnabled());
        verifyThat(lblFullNameError, hasText(""));

    }

    private void clean(TextField txt) {
        int textLength = txt.getLength();
        clickOn(txt);
        eraseText(textLength);
    }

    private void cleanAll() {
        //Delete data
        clean(txtFullName);
        clean(txtUsername);
        clean(txtMail);
    }
}
