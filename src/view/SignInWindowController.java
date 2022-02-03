package view;

import crypt.EncriptDecriptClient;
import entities.Client;
import entities.EnumPrivilege;
import entities.User;
import exceptions.BusinessLogicException;
import exceptions.ConnectionErrorException;
import exceptions.EmptyFieldsException;
import exceptions.FieldTooLongException;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotExistException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.NotAuthorizedException;
import logic.UserFactory;
import logic.UserInterface;

/**
 * This is the controller of the Sign In window
 *
 * @author Ilia Consuegra
 */
public class SignInWindowController {

    private Stage stage = new Stage();
    private static final Logger LOGGER = Logger.getLogger(SignInWindowController.class.getName());
    private UserInterface userInterface;

    @FXML
    private Button btnLogin;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblUsernameError;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private Hyperlink hlkRegister;
    @FXML
    private Hyperlink hlkReset;

    //Getters and Setters
    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage The stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializates the main stage.
     *
     * @param root The parent object with the parent window loaded on it.
     */
    public void initStage(Parent root) {
        try {
            //The login button (btnLogin) is enabled.
            //The username (txtUsername) and password (txtPassword) fields are enabled.
            //The here hyperlink (hlkRegister) is enabled.

            //Initializes the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");

            //The window title
            stage.setTitle("Sign In Window");

            //The window (SignInWindow) is not resizable.
            stage.setResizable(false);

            stage.setOnCloseRequest((event) -> {
                this.exitApplicationWhenXPressed(event);
            });

            //The username (txtUsername) field is focused.
            txtUsername.requestFocus();

            //Setting text change listeners to the different text fields.
            txtUsername.textProperty().addListener(this::txtUsernameChanged255);
            txtUsername.textProperty().addListener(this::txtUsernameEmpty);
            txtPassword.textProperty().addListener(this::txtPasswordChanged255);
            txtPassword.textProperty().addListener(this::txtPasswordEmpty);
            hlkRegister.setOnAction(this::hlkRegister);
            hlkReset.setOnAction(this::handleReset);
            btnLogin.setOnAction(this::handleBtnLoginPressed);

            //The error labels (lblUsernameError and lblPasswordError) are not visible.
            lblPasswordError.setVisible(false);
            lblUsernameError.setVisible(false);

            //Some tooltips to help the user
            btnLogin.setTooltip(new Tooltip("Click to log in"));
            hlkRegister.setTooltip(new Tooltip("Click to go to the Sign up window and register"));

            userInterface = UserFactory.createUsersRestful();

            //Shows stage
            stage.show();

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Showing stage");
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Check that the Username field (txtUsername) is no longer than 255
     * characters (checkNoLonger255()). If it is not correct
     * (FieldTooLongException), an error label (lblUsernameError) is shown.
     *
     * @param observable The object that handles the event.
     * @param oldValue The old value of the text field.
     * @param newValue The new value of the text field.
     */
    private void txtUsernameChanged255(ObservableValue observable, String oldValue, String newValue) {
        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                //Calls the method that check the length
                checkNoLonger255(txtUsername, lblUsernameError);
            }
        } catch (FieldTooLongException ex) {
            //The login button is disabled
            btnLogin.setDisable(true);

            //The error label is shown
            lblUsernameError.setVisible(true);
            errorLabel(lblUsernameError, ex);
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Check that the Username field (txtUsername) or Password field
     * (txtPassword) fields are not empty checkEmptyFields(txtUsername,
     * lblUsernameError);
     *
     * @param observable The object that handles the event.
     * @param oldValue The old value of the text field.
     * @param newValue The new value of the text field.
     */
    private void txtUsernameEmpty(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            if (txtUsername.getText().trim().isEmpty()) {
                try {
                    throw new EmptyFieldsException();
                } catch (EmptyFieldsException ex) {
                    errorLabel(lblUsernameError, ex);
                    LOGGER.severe(ex.getMessage());
                }
            }
        }
    }

    /**
     * Check that the Password field (txtPassword) is no longer than 255
     * characters (checkNoLonger255()).
     *
     * @param observable The object that handles the event.
     * @param oldValue The old value of the text field.
     * @param newValue The new value of the text field.
     */
    private void txtPasswordChanged255(ObservableValue observable, String oldValue, String newValue) {
        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                checkNoLonger255(txtPassword, lblPasswordError);
            }
        } catch (FieldTooLongException ex) {
            //The error label is shown   
            btnLogin.setDisable(true);
            lblPasswordError.setVisible(true);
            errorLabel(lblPasswordError, ex);
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * Check that the Password field (txtPassword) is not empty
     * (checkEmptyFields(txt,lbl)).
     *
     * @param observable The object that handles the event.
     * @param oldValue The old value of the text field
     * @param newValue The new value of the text field
     */
    private void txtPasswordEmpty(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            if (txtPassword.getText().trim().isEmpty()) {
                try {
                    throw new EmptyFieldsException();
                } catch (EmptyFieldsException ex) {
                    errorLabel(lblPasswordError, ex);
                    LOGGER.severe(ex.getMessage());
                }
            }
        }
    }

    /**
     * Handles the login button, checks the username and password and shows
     * multiple errors if doesnt match the cryteria. If the User exists, the
     * Welcome window (WelcomeWindow) is opened as modal. And the Sign In window
     * (SignInWindow) is closed.
     *
     * @param event The event that manages when LogIn button is pressed.
     */
    public void handleBtnLoginPressed(ActionEvent event) {
        Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Log in button pressed.");
        try {
            if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                try {
                    throw new EmptyFieldsException();
                } catch (EmptyFieldsException ex) {
                    if (txtUsername.getText().trim().isEmpty()) {
                        errorLabel(lblUsernameError, ex);
                    } else {
                        errorLabel(lblPasswordError, ex);
                    }
                    LOGGER.severe(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            try {
                throw new ConnectionErrorException();
            } catch (ConnectionErrorException ex1) {
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        //Check that the user exist or not
        if (lblUsernameError.isVisible() || lblPasswordError.isVisible()) {
            try {
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            try {
                throw new EmptyFieldsException();
            } catch (EmptyFieldsException ex) {
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                /*
                User user = null;
                user = new User();
                user.setLogin(txtUsername.getText());
                user.setPassword(txtPassword.getText());*/
                String login = txtUsername.getText();
                String password = txtPassword.getText();
                String passwordCifrada = EncriptDecriptClient.encrypt(password);
                //sending the User to the database
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Sending the User to the database...");
                User user = userInterface.findUserByLoginAndPassword(login, passwordCifrada);
                //Opens the client welcome window
                if (user.getEnumPrivilege() == EnumPrivilege.CLIENT) {
                    openWelcomeClientWindow(user);
                } else if (user.getEnumPrivilege() == EnumPrivilege.ADMIN) {
                    openWelcomeAdminWindow(user);
                } else {
                    LOGGER.severe("Error");
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("The psychologist doesnt have a window");
                    alert.show();
                }
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Window open susccefully");
            } catch (BusinessLogicException ex) {
                LOGGER.severe("Error with the server");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("there is a problem on the server");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (NotAuthorizedException ex) {
                LOGGER.severe("SignIn error");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You are not authorized entering to this app");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                Logger.getLogger(SignInWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Handles the hyperlink field.
     *
     * @param event The event that manages when the Hyperlink is pressed.
     */
    public void hlkRegister(ActionEvent event) {
        try {
            //Opens the Sign Up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpWindow.fxml"));

            //Creates a new stage
            Stage stageSignUp = new Stage();
            Parent root = (Parent) loader.load();

            //Gets sign up controller
            SignUpController signUpController = ((SignUpController) loader.getController());

            //Set the stage that we already created to the sign up controller
            signUpController.setStage(stageSignUp);

            //Opening application as modal
            stageSignUp.initModality(Modality.APPLICATION_MODAL);
            stageSignUp.initOwner(((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            signUpController.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(SignInWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleReset(ActionEvent event) {
        try {
            TextInputDialog txi = new TextInputDialog();
            txi.setHeaderText("Reset password");
            txi.setContentText("Insert your email");
            txi.showAndWait();
            String email = txi.getEditor().getText().trim();
            userInterface.resetPasswordByEmail(email);
            LOGGER.info("Email send correctly");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Email sent");
            alert.setContentText("The email was sent correctly");
            alert.showAndWait();
        } catch (BusinessLogicException ex) {
            LOGGER.severe("Error with the server");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("there is a problem on the server");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        }
    }

    /**
     * The username (txtUsername) and password (txtPassword) fields are
     * completed with the information brought it from the Sign Up window.
     *
     * @param user The user that is collected from the sign up
     */
    public void initWhenSignUp(User user) {
        //Fields are completed with the information brought in from the Sign Up window.
        txtUsername.setText(user.getLogin());
        txtPassword.setText(user.getPassword());

        btnLogin.isFocused();
    }

    /**
     * Check that the Username field (txtUsername) or Password field
     * (txtPassword) are no longer than 255 characters
     * (checkNoLonger255(TextField, Label)). If the field is longer
     * (FieldTooLongException), an error label (lblUsernameError) and
     * (lblPasswordError) is shown.
     *
     * @param text The text that is received in the method.
     * @param lblError The label that is shown, showing an exception.
     * @exception FieldTooLongException The exception if the text field length
     * is higher than 255.
     *
     */
    private void checkNoLonger255(TextField text, Label lblError) throws FieldTooLongException {
        // If the field is longer than 255, an error label is shown.
        if (text.getLength() > 255) {
            //An error label is shown.
            lblError.setVisible(true);
            throw new FieldTooLongException();
        } else {
            //An error label is hidden.
            lblError.setVisible(false);
        }
    }

    /**
     * Opens the welcome window sending a user to greet the user that logged in.
     *
     * @param user The user that is sended.
     */
    private void openWelcomeClientWindow(User user) {
        try {
            //Opens the Welcome Client window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeClient.fxml"));
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            WelcomeClientWindowController welcomeClientWindowController = ((WelcomeClientWindowController) loader.getController());

            welcomeClientWindowController.setStage(stage);

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            welcomeClientWindowController.initialize(root, user);

        } catch (IOException ex) {
            LOGGER.severe("Error opening the Client Window");
            LOGGER.severe(ex.getMessage());
            LOGGER.severe(ex.getLocalizedMessage());
            LOGGER.severe(ex.getClass().getSimpleName());
            /*            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was a problem opening the client window");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();*/
        }
    }

    private void openWelcomeAdminWindow(User user) {
        try {
            //Opens the Welcome Admin window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WelcomeAdmin.fxml"));
            Parent root = (Parent) loader.load();

            //Gets Welcome window controller
            WelcomeAdminWindowController welcomeAdminWindowController = ((WelcomeAdminWindowController) loader.getController());

            welcomeAdminWindowController.setStage(stage);

            Logger.getLogger(SignInWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            welcomeAdminWindowController.initialize(root, user);

        } catch (IOException ex) {
            LOGGER.severe("Error opening the Client Window");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was a problem opening the client window");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * The method that put the label Visible with the error message in red
     *
     * @param lbl The label that is shown.
     * @param ex The error of the exception.
     */
    private void errorLabel(Label lbl, Exception ex) {
        lbl.setVisible(true);
        lbl.setText(ex.getMessage());
        LOGGER.severe(ex.getMessage());
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
