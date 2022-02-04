/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import crypt.EncriptDecriptClient;
import entities.Client;
import entities.EnumPrivilege;
import entities.EnumStatus;
import entities.User;
import exceptions.BusinessLogicException;
import exceptions.EmptyFieldException;
import exceptions.FieldTooLongException;
import exceptions.FullNameException;
import exceptions.PasswordDontMatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import logic.ClientFactory;
import logic.ClientInterface;
import logic.UserFactory;
import logic.UserInterface;

/**
 * FXML Controller class is for the user to edit their information and also to
 * change their password
 *
 * @author Alain Lozano
 */
public class ProfileWindowController {

    @FXML
    private Label lblFullName;
    @FXML
    private TextField txtFullName;
    @FXML
    private Label lblUserName;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label lblMail;
    @FXML
    private TextField txtMail;
    @FXML
    private Label lblFullNameError;
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblMailError;
    @FXML
    private Label lblPassword;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblRepeatPassword;
    @FXML
    private PasswordField txtRepeatPassword;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblRepeatPasswordError;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBack;

    private Stage stage = new Stage();

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    ClientInterface clientInterface;
    UserInterface userInterface;
    int userId = 0;
    Client clientAux = null;

    private final static Logger LOGGER = Logger.getLogger(PsychologistProfileController.class.getName());

    /**
     * Here we initialize the window and we set the components visibility and
     * also we set some components disable
     *
     * @param root
     * @param userId the user id from the user that enter the aplicattion
     */
    public void initStage(Parent root, int userId) {
        try {
            //We search for the user information
            clientInterface = ClientFactory.createClientRestful();
            userInterface = UserFactory.createUsersRestful();
            this.userId = userId;
            clientAux = clientInterface.find(String.valueOf(userId));
            Scene scene = new Scene(root);
            getStage().setScene(scene);
            getStage().setResizable(false);
            //We set the handlers for the window components
            txtFullName.textProperty().addListener(this::fullNameTextChanged);
            txtFullName.focusedProperty().addListener(this::fullNameFocusChanged);
            txtUsername.textProperty().addListener(this::usernameTextChanged);
            txtMail.textProperty().addListener(this::mailTextChanged);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
            btnBack.setOnAction(this::handleButtonBack);

            //We fill in the data of the found user 
            txtFullName.setText(clientAux.getFullName());
            txtUsername.setText(clientAux.getLogin());
            txtMail.setText(clientAux.getEmail());
            //We show the window
            getStage().show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (Exception ex) {

        }
    }

    /**
     * This handler is for the textfield to check if the text introduced is too
     * long for the database
     *
     * @param observable
     * @param oldValue the old value for the introduces text
     * @param newValue the new value for the introduced text
     */
    private void fullNameTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(txtFullName.getText(), lblFullNameError);
            }
        } catch (FieldTooLongException ex) {
            lblFullNameError.setVisible(true);
            lblFullNameError.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
            lblFullNameError.setText(ex.getMessage());
        }
    }

    /**
     * This handler is for the textfield to check if the text introduced is too
     * long for the database
     *
     * @param observable
     * @param oldValue the old value for the introduces text
     * @param newValue the new value for the introduced text
     */
    private void fullNameFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        if (newValue) {

        } else if (oldValue) {
            try {
                checkWhiteSpace(txtFullName.getText(), lblFullNameError);
            } catch (FullNameException ex) {
                errorLabel(lblFullNameError, ex);
            }
        }
    }

    /**
     * Method that checks if the text has at leat one blank
     *
     * @param text The text that is received
     * @param lblError The label that is shown
     * @throws FullNameException The exception if the text does not have at
     * least one blank
     */
    private void checkWhiteSpace(String text, Label lblError) throws FullNameException {
        if (!text.trim().contains(" ")) {
            btnModify.setDisable(true);
            throw new FullNameException();
        } else {
            btnModify.setDisable(false);
        }

    }

    /**
     * This handler is for the textfield to check if the text introduced is too
     * long for the database
     *
     * @param observable
     * @param oldValue the old value for the introduces text
     * @param newValue the new value for the introduced text
     */
    private void usernameTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(txtUsername.getText(), lblUsernameError);
            }
        } catch (FieldTooLongException ex) {
            lblUsernameError.setVisible(true);
            lblUsernameError.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
            lblUsernameError.setText(ex.getMessage());

        }
    }

    /**
     * This handler is for the textfield to check if the text introduced is too
     * long for the database
     *
     * @param observable
     * @param oldValue the old value for the introduces text
     * @param newValue the new value for the introduced text
     */
    private void mailTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(new String(txtMail.getText()), lblMailError);
            }
        } catch (FieldTooLongException ex) {
            lblMailError.setVisible(true);
            lblMailError.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
            lblMailError.setText(ex.getMessage());

        }
    }

    /**
     * Method to check if the text introduced in the text field are too long
     *
     * @param text the text of the text field
     * @param lbl the error label
     * @throws FieldTooLongException in case the text is longer than 255
     * characters
     */
    private void check255(String text, Label lbl) throws FieldTooLongException {
        if (text.length() > 255) {
            btnModify.setDisable(true);
            throw new FieldTooLongException();
        } else {
            btnModify.setDisable(false);
            lbl.setVisible(false);
        }
    }

    /**
     * The handler for the modify button we colect the user data and we modify
     * the user in the server sending the information
     *
     * @param event
     */
    private void handleButtonModify(ActionEvent event) {
        try {
            Client client = new Client();
            //Here we check if the full name, username and mail text field are empty
            if (txtFullName.getText().isEmpty()) {
                try {
                    txtFullName.requestFocus();
                    throw new EmptyFieldException();
                } catch (EmptyFieldException ex) {
                    errorLabel(lblFullNameError, ex);
                }
            }
            if (txtUsername.getText().isEmpty()) {
                try {
                    txtUsername.requestFocus();
                    throw new EmptyFieldException();
                } catch (EmptyFieldException ex) {
                    errorLabel(lblUsernameError, ex);
                }
            }
            if (txtMail.getText().isEmpty()) {
                txtMail.requestFocus();
                try {
                    throw new EmptyFieldException();
                } catch (EmptyFieldException ex) {
                    errorLabel(lblMailError, ex);
                }
            }
            //If the password fields are empty only the user data is modify
            if (new String(txtPassword.getText()).isEmpty() || new String(txtRepeatPassword.getText()).isEmpty()) {
                client.setLogin(txtUsername.getText());
                client.setFullName(txtFullName.getText());
                client.setPassword(clientAux.getPassword());
                client.setEmail(txtMail.getText());
                client.setId(userId);
                client.setEnumPrivilege(EnumPrivilege.CLIENT);
                client.setEnumStatus(EnumStatus.ACTIVE);
                //We colect all the user data and we edit the user
                clientInterface.edit(client);
                //If the procces is succesfull we show an alert to the user
                Alert alertUserModifiedCorrectly = new Alert(Alert.AlertType.INFORMATION);
                alertUserModifiedCorrectly.setHeaderText("Modifycation correct");
                alertUserModifiedCorrectly.setContentText("The user information has been updated");
                alertUserModifiedCorrectly.show();
            } else {
                //If the password fields are completed we check the two passowrd are the same and we edit the user information and also we update the user password
                if (new String(txtPassword.getText()).equals(new String(txtRepeatPassword.getText()))) {
                    client.setLogin(txtUsername.getText());
                    client.setFullName(txtFullName.getText());
                    client.setEmail(txtMail.getText());
                    client.setId(userId);
                    client.setEnumPrivilege(EnumPrivilege.CLIENT);
                    client.setEnumStatus(EnumStatus.ACTIVE);
                    //We show an alert to the user to put the original password to prove they know their own password
                    TextInputDialog txi = new TextInputDialog();
                    txi.setHeaderText("Password Change");
                    txi.setContentText("Put here your original password");
                    txi.showAndWait();
                    String passwordIntr = txi.getEditor().getText().trim();
                    User userAux = userInterface.findUserByLoginAndPassword(txtUsername.getText().trim(), EncriptDecriptClient.encrypt(passwordIntr));
                    String passwordEncripted = EncriptDecriptClient.encrypt(new String(txtPassword.getText().trim()));
                    //if the password introduces is correct we edit the password and the changed data of the user
                    clientInterface.edit(client);
                    userInterface.changePasswordByLogin(clientAux.getLogin(), passwordEncripted);
                    //We show this alert if the user data is correctly updated
                    Alert alertUserModifiedCorrectly = new Alert(Alert.AlertType.INFORMATION);
                    alertUserModifiedCorrectly.setHeaderText("Modifycation correct");
                    alertUserModifiedCorrectly.setContentText("The user information has been updated");
                    alertUserModifiedCorrectly.show();

                } else {
                    throw new PasswordDontMatch();
                }

            }
        } catch (PasswordDontMatch ex) {
            //In case the password introduces dont match
            Alert alertPasswordMatch = new Alert(Alert.AlertType.INFORMATION);
            alertPasswordMatch.setHeaderText("Password Error");
            alertPasswordMatch.setContentText("The password dont match");
            alertPasswordMatch.show();
        } catch (BusinessLogicException ex) {
            //In case the server throws and error 
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        } catch (Exception ex) {
            //In case the server is down
            Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * This method is to set the lable visible and set the style of the lable, also the set error text in the label 
     * @param lbl the label to set the error
     * @param ex the error
     */
    private void errorLabel(Label lbl, Exception ex) {
        lbl.setVisible(true);
        lbl.setText(ex.getMessage());
        lbl.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
        LOGGER.severe(ex.getMessage());

    }
    /**
     * This handler is for the delete button we delete the user by the id we got
     * @param event 
     */
    private void handleButtonDelete(ActionEvent event) {
        try {
            clientInterface.remove(String.valueOf(userId));
        } catch (BusinessLogicException ex) {
            //In case the server throws an error
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        }
    }
    /**
     * This handler is for the back button this closes the window
     * @param event 
     */
    private void handleButtonBack(ActionEvent event) {
        getStage().close();
    }

}
