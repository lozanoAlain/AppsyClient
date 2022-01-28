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
import exceptions.EmptyFieldException;
import exceptions.FieldTooLongException;
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
import logic.ClientFactory;
import logic.ClientInterface;
import logic.UserFactory;
import logic.UserInterface;

/**
 * FXML Controller class
 *
 * @author Usuario
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

    Stage stage = new Stage();
    ClientInterface clientInterface;
    UserInterface userInterface;
    int userId = 0;
    Client clientAux = null;

    private final static Logger logger = Logger.getLogger(PsychologistProfileController.class.getName());

    public void initStage(Parent root, int userId) {
        try {
            clientInterface = ClientFactory.createClientRestful();
            userInterface = UserFactory.createUsersRestful();
            this.userId = userId;
            clientAux = clientInterface.find(String.valueOf(userId));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);

            txtFullName.textProperty().addListener(this::fullNameTextChanged);
            txtUsername.textProperty().addListener(this::usernameTextChanged);
            txtMail.textProperty().addListener(this::mailTextChanged);
            btnModify.setOnAction(this::handleButtonModify);
            btnDelete.setOnAction(this::handleButtonDelete);
            btnBack.setOnAction(this::handleButtonBack);

            txtFullName.setText(clientAux.getFullName());
            txtUsername.setText(clientAux.getLogin());
            txtMail.setText(clientAux.getEmail());

            stage.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientErrorException ex) {
            Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    private void check255(String text, Label lbl) throws FieldTooLongException {
        if (text.length() > 255) {
            btnModify.setDisable(true);
            throw new FieldTooLongException();
        } else {
            btnModify.setDisable(false);
            lbl.setVisible(false);
        }
    }

    private void handleButtonModify(ActionEvent event) {
        try {
            Client client = new Client();
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
                    errorLabel(lblMail, ex);
                }
            }
            if (new String(txtPassword.getText()).isEmpty() && new String(txtRepeatPassword.getText()).isEmpty()) {
                client.setLogin(txtUsername.getText());
                client.setFullName(txtFullName.getText());
                client.setEmail(txtMail.getText());
                client.setId(userId);
                client.setEnumPrivilege(EnumPrivilege.CLIENT);
                client.setEnumStatus(EnumStatus.ACTIVE);
                clientInterface.edit(client);
            } else {
                if (new String(txtPassword.getText()).equals(new String(txtRepeatPassword.getText()))) {
                    client.setLogin(txtUsername.getText());
                    client.setFullName(txtFullName.getText());
                    client.setEmail(txtMail.getText());
                    client.setId(userId);
                    client.setEnumPrivilege(EnumPrivilege.CLIENT);
                    client.setEnumStatus(EnumStatus.ACTIVE);

                    TextInputDialog txi = new TextInputDialog();
                    txi.setHeaderText("Password Change");
                    txi.setContentText("Put here your original password");
                    txi.showAndWait();
                    String passwordIntr = txi.getEditor().getText().trim();
                    User userAux = userInterface.findUserByLoginAndPassword(txtUsername.getText().trim(), EncriptDecriptClient.encrypt(passwordIntr));
                    String passwordEncripted = EncriptDecriptClient.encrypt(new String(txtPassword.getText().trim()));
                    userInterface.changePasswordByLogin(clientAux.getLogin(), passwordEncripted);

                } else {
                    Alert alertPasswordMatch = new Alert(Alert.AlertType.INFORMATION);
                    alertPasswordMatch.setHeaderText("Password Error");
                    alertPasswordMatch.setContentText("The password introduced dont match");
                    alertPasswordMatch.show();
                }

            }
        } catch (PasswordDontMatch ex) {
            Alert alertPasswordMatch = new Alert(Alert.AlertType.INFORMATION);
            alertPasswordMatch.setHeaderText("Password Error");
            alertPasswordMatch.setContentText("The password dont match");
            alertPasswordMatch.show();
        } catch (NotAuthorizedException ex) {
            Alert alertPasswordMatch = new Alert(Alert.AlertType.INFORMATION);
            alertPasswordMatch.setHeaderText("User Error");
            alertPasswordMatch.setContentText("The password introduced is incorrect");
            alertPasswordMatch.show();
        } catch (Exception ex) {
            Logger.getLogger(ProfileWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void errorLabel(Label lbl, Exception ex) {
        lbl.setVisible(true);
        lbl.setText(ex.getMessage());
        lbl.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
        logger.severe(ex.getMessage());

    }

    private void handleButtonDelete(ActionEvent event) {
        clientInterface.remove(String.valueOf(userId));
    }

    private void handleButtonBack(ActionEvent event) {
        stage.close();
    }

}
