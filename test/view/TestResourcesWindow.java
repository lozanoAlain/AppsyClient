/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

//import Application.ClientApplication;
import Application.ApplicationAppsy;
import entities.Psychologist;
import entities.Resource;
import java.util.Date;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import logic.PsychologistInterface;
import logic.ResourceInterface;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 *
 * @author Matteo Fernández
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestResourcesWindow extends ApplicationTest {

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
    private Label tittleLabel;
    @FXML
    private Button btnDelete;
    @FXML
    private ComboBox<Psychologist> comboPsycho;
    @FXML
    private TextField txtDiagnose;
    @FXML
    private Button btnReport;
    private Stage stage;
    private static final Logger logger = Logger.getLogger("package.class");
    private ResourceInterface resourceManager;
    private PsychologistInterface psychologistManager;
    @FXML
    public DatePicker datePicker;

    private static final String OVERSIZED_TEXT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    @Override
    public void start(Stage stage) throws Exception {
        new ApplicationAppsy().start(stage);
    }

    @Before
    public void initializeLookUp() {

        //Initialize fields
        comboPsycho = lookup("#comboPsycho").query();
        txtTittle = lookup("#txtTittle").query();
        txtLink = lookup("#txtLink").query();

        //Initialize table
        tableViewResource = lookup("#tableViewResource").query();

        //Initialize buttons
        btnAdd = lookup("#btnAdd").query();
        btnDelete = lookup("#btnDelete").query();
        btnReport = lookup("#btnReport").query();
        btnBack = lookup("#btnBack").query();
        btnModify = lookup("#btnModify").query();
        btnSearch = lookup("#btnSearch").query();

        release(KeyCode.CONTROL);
    }

    @Test
    public void testA_TableLoaded() {
        int rows = lookup("#tableViewResource").queryTableView().getItems().size();
        assertNotEquals(rows, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull(row);
    }

    @Test
    public void testB_InitialState() {
        //Buttons
        verifyThat("#btnBack", isEnabled());
        verifyThat("#btnReport", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnAdd", isDisabled());
        verifyThat("#btnSearch", isEnabled());
        //verifyThat("#btnSearch", isFocused());

        //Fields
        verifyThat("#txtTittle", isEnabled());
        verifyThat("#txtLink", isEnabled());
        verifyThat("#comboPsycho", isEnabled());
    }

    @Test
    public void testC_Create() {
        clickOn("#txtTittle");
        write("Depression");
        clickOn("#txtLink");
        write("http://gdhshs");
        clickOn("#comboPsycho");
        clickOn("Sigmund Freud");
        clickOn("#txtTittle");
        txtTittle.clear();
        write("Depression");
        clickOn(btnAdd);
        verifyThat("Resource added correctly",
                isVisible());
        clickOn("Aceptar");
        verifyThat("Depression", isVisible());
    }

    @Test
    public void testD_Modify() {
        clickOn("http://gdhshs");
        clickOn("#txtLink");
        txtLink.clear();
        write("http://hola");
        clickOn(btnModify);
        verifyThat("Resource modified correctly",
                isVisible());
        clickOn("Aceptar");
        verifyThat("http://hola", isVisible());
    }

    @Test
    public void testE_DeleteButtonAceptar() {
        int rowCount = lookup("#tableViewResource").queryTableView().getItems().size();
        clickOn("Depression");
        clickOn(btnDelete);
        verifyThat("Are you sure you want to delete the selected Resource?\n"
                + "This option can't be reversed.",
                isVisible());
        clickOn("Aceptar");
        verifyThat("The resource has been deleted.", isVisible());
        clickOn("Aceptar");
        assertNotEquals(rowCount, lookup("#tableViewResource").queryTableView().getItems().size());
    }

    @Test
    public void testF_verifyFieldsArefilled() {
        verifyThat(btnAdd, isDisabled());
        clickOn("#txtTittle");
        write("OCD");
        clickOn("#txtLink");
        write("http://hola");
        clickOn("#comboPsycho");
        clickOn("Sigmund Freud");
        clickOn("#txtTittle");
        txtTittle.clear();
        write("OCD");
        verifyThat(btnAdd, isEnabled());
        //Just for the later tests
        clickOn("#btnAdd");
        clickOn("Aceptar");
    }

    @Test
    public void testG_verify255() {
        clickOn("#txtTittle");
        write(OVERSIZED_TEXT);
        verifyThat(btnModify, isDisabled());
        verifyThat("#tittleLabel", isVisible());
        verifyThat("#tittleLabel", hasText("The field is too long (255 character max)."));
        doubleClickOn("#txtTittle");
        txtTittle.clear();
    }

    /* @Test
    public void testH_verifyDataGoesAbove() {
    clickOn("OCD");
    //The data from the F test
    verifyThat("#txtTittle", hasText("OCD"));
    verifyThat("#txtLink", hasText("http://hola"));
    verifyThat("#comboPsycho", hasText("Sigmund Freud"));
    }*/

    @Test
    public void testI_verifySearchWorks() {
        int rowCount = lookup("#tableViewResource").queryTableView().getItems().size();
        clickOn("#txtTittle");
        write("OCD");
        clickOn(btnSearch);
        verifyThat("OCD", isVisible());
        assertNotEquals(rowCount, lookup("#tableViewResource").queryTableView().getItems().size());
    }

}
