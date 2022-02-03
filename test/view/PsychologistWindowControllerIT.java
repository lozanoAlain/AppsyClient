package view;

import entities.Psychologist;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.base.WindowMatchers;
import view.PsychologistWindowController;

/**
 *
 * @author Alain Lozano
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PsychologistWindowControllerIT extends ApplicationTest {

    private ComboBox comboSearch;
    private TextField txtFieldSearch;
    private Button btnSearch;
    private TableView<Psychologist> tablePsychologist;
    private TableColumn<Psychologist, String> columnLogin;
    private TableColumn<Psychologist, String> columnEmail;
    private TableColumn<Psychologist, String> columnFullName;
    private TableColumn<Psychologist, String> columnStatus;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;
    private Button btnReport;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PsychologistWindow.fxml"));

        //Creates a new stage
        Parent root = (Parent) loader.load();

        //Gets sign up controller
        PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());

        //Set the stage that we already created to the sign up controller
        psychologistWindowController.initStage(root);
    }

    @Before
    public void initializeLookUp() {

        comboSearch = lookup("#comboSearch").query();
        txtFieldSearch = lookup("#txtFieldSearch").query();
        tablePsychologist = lookup("#tablePsychologist").query();

        btnAdd = lookup("#btnAdd").query();
        btnDelete = lookup("#btnDelete").query();
        btnReport = lookup("#btnReport").query();
        btnBack = lookup("#btnBack").query();
        btnModify = lookup("#btnModify").query();
        btnSearch = lookup("#btnSearch").query();

        release(KeyCode.CONTROL);

    }

    @Test
    public void testA_InitialState() {
        verifyThat("#txtFieldSearch", isEnabled());
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnSearch", isEnabled());

    }

    @Test
    public void testB_SearchButton() {
        clickOn(comboSearch);
        clickOn("Name");
        clickOn(btnSearch);
        verifyThat("The search field cant be empty", isVisible());
        clickOn("Aceptar");
        clickOn(txtFieldSearch);
        write("Lev Vygotsky");
        clickOn(btnSearch);
        verifyThat("lev@gmail.com", isVisible());
    }
    @Ignore
    @Test
    public void testC_DeleteModifyButton() {

        clickOn("Lev Vygotsky");
        verifyThat(btnModify, isEnabled());
        verifyThat(btnDelete, isEnabled());
        press(KeyCode.CONTROL);
        clickOn("Lev Vygotsky");
        verifyThat(btnModify, isDisabled());
        verifyThat(btnDelete, isDisabled());
    }

    @Test
    public void testD_AddButton() {
        clickOn(btnAdd);
        verifyThat(window("Psychologist Profile Window"), WindowMatchers.isShowing());
    }

    @Test
    public void testE_ModifyButton() {
        clickOn("Lev Vygotsky");
        clickOn(btnModify);
        verifyThat(window("Psychologist Profile Window"), WindowMatchers.isShowing());
    }

    @Test
    public void testF_DeleteButton() {
        int rows = tablePsychologist.getItems().size();
        Node row = lookup(".table-row-cell").nth(tablePsychologist.getItems().size() - 1).query();
        clickOn("Lev Vygotsky");
        clickOn(btnDelete);
        verifyThat("Are you sure you want to delete the psychologist?", isVisible());
        clickOn("Cancelar");
        verifyThat("The psychologist hasnt been deleted", isVisible());
        clickOn("Aceptar");
        clickOn(btnDelete);
        verifyThat("Are you sure you want to delete the psychologist?", isVisible());
        clickOn("Aceptar");
        assertNotEquals("Row deleted", rows, tablePsychologist.getItems().size());
    }
    @Ignore
    @Test
    public void testG_ReportButton() {
        clickOn(btnReport);
        verifyThat("JasperView", isVisible());

    }
}
