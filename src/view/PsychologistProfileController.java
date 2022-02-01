/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import crypt.EncriptDecriptClient;
import entities.EnumPrivilege;
import entities.EnumStatus;
import entities.Psychologist;
import exceptions.BusinessLogicException;
import exceptions.EmptyFieldException;
import exceptions.FieldTooLongException;
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
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import logic.PsychologistFactory;
import logic.PsychologistInterface;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class PsychologistProfileController {

    @FXML
    private Label lblFullName;
    @FXML
    private TextField txtFullName;
    @FXML
    private Label lblUsername;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label lblMail;
    @FXML
    private TextField txtMail;
    @FXML
    private Label lblPassword;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblOffice;
    @FXML
    private TextField txtOffice;
    @FXML
    private Label lblSpecialization;
    @FXML
    private TextField txtSpezialitation;
    @FXML
    private Label lblFullNameError;
    @FXML
    private Label lblUsernameError;
    @FXML
    private Label lblMailError;
    @FXML
    private Label lblPasswordError;
    @FXML
    private Label lblOfficeError;
    @FXML
    private Label lblSpecializationError;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnAdd;

    private Stage stage = new Stage();
    PsychologistInterface interfacePsychologist;
    int idSelected = 0;

    private final static Logger LOGGER = Logger.getLogger(PsychologistProfileController.class.getName());

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

    void initStage(Parent root, int idSelected) {
        try {
            this.idSelected = idSelected;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Psychologist Profile Window");
            interfacePsychologist = PsychologistFactory.createPsychologistRestful();

            if (idSelected != 0) {
                Psychologist psychologist = interfacePsychologist.findPsychologist(String.valueOf(idSelected));
                txtFullName.setText(psychologist.getFullName());
                txtUsername.setText(psychologist.getLogin());
                txtMail.setText(psychologist.getEmail());
                txtOffice.setText(psychologist.getOffice());
                txtSpezialitation.setText(psychologist.getSpecialization());
                btnModify.setDisable(false);
                btnAdd.setDisable(true);
            } else {
                btnModify.setDisable(true);
                btnAdd.setDisable(false);
            }

            txtFullName.textProperty().addListener(this::fullNameTextChanged);
            txtUsername.textProperty().addListener(this::usernameTextChanged);
            txtMail.textProperty().addListener(this::mailTextChanged);
            txtOffice.textProperty().addListener(this::officeTextChanged);
            txtPassword.textProperty().addListener(this::passwordTextChanged);
            txtSpezialitation.textProperty().addListener(this::specializationTextChanged);
            btnAdd.setOnAction(this::handleButtonAdd);
            btnModify.setOnAction(this::handleButtonModify);
            btnBack.setOnAction(this::handleButtonBack);

            //Show window.
            stage.show();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(PsychologistProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
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

    private void passwordTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(new String(txtPassword.getText()), lblPasswordError);
            }
        } catch (FieldTooLongException ex) {
            lblPasswordError.setVisible(true);
            lblPasswordError.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
            lblPasswordError.setText(ex.getMessage());

        }
    }

    private void officeTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(new String(txtOffice.getText()), lblOfficeError);
            }
        } catch (FieldTooLongException ex) {
            lblOfficeError.setVisible(true);
            lblOfficeError.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
            lblOfficeError.setText(ex.getMessage());

        }
    }

    private void specializationTextChanged(ObservableValue observable, String oldValue, String newValue) {

        try {
            if (!newValue.equalsIgnoreCase(oldValue)) {
                check255(new String(txtSpezialitation.getText()), lblSpecializationError);
            }
        } catch (FieldTooLongException ex) {
            lblSpecializationError.setVisible(true);
            lblSpecialization.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
            lblSpecializationError.setText(ex.getMessage());

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

    private void handleButtonAdd(ActionEvent event) {
        try {
            if (!checkEmptyFields()) {
                Psychologist psychologistadd = new Psychologist();

                psychologistadd.setId(null);
                psychologistadd.setFullName(txtFullName.getText());
                psychologistadd.setLogin(txtUsername.getText());
                psychologistadd.setEmail(txtMail.getText());
                psychologistadd.setPassword(new String(txtPassword.getText()));
                psychologistadd.setOffice(txtOffice.getText());
                psychologistadd.setSpecialization(txtSpezialitation.getText());
                psychologistadd.setEnumPrivilege(EnumPrivilege.PSYCHOLOGIST);
                psychologistadd.setEnumStatus(EnumStatus.ACTIVE);

                interfacePsychologist.createPsychologist(psychologistadd);
                stage.close();
            }

        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
            errorCreatingThePsychologist.setHeaderText("Server Error");
            errorCreatingThePsychologist.setContentText(ex.getMessage());
            errorCreatingThePsychologist.show();
        }

    }

    private void handleButtonModify(ActionEvent event) {
        if (!checkEmptyFields()) {
            try {
                Psychologist psychologistadd = new Psychologist();

                psychologistadd.setId(idSelected);
                psychologistadd.setFullName(txtFullName.getText());
                psychologistadd.setLogin(txtUsername.getText());
                psychologistadd.setEmail(txtMail.getText());
                String passwordHash = EncriptDecriptClient.hashearTexto(new String(txtPassword.getText()));
                psychologistadd.setPassword(passwordHash);
                psychologistadd.setOffice(txtOffice.getText());
                psychologistadd.setSpecialization(txtSpezialitation.getText());
                psychologistadd.setEnumStatus(EnumStatus.ACTIVE);
                psychologistadd.setEnumPrivilege(EnumPrivilege.PSYCHOLOGIST);

                interfacePsychologist.editPsychologist(psychologistadd);
                stage.close();
            } catch (BusinessLogicException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
                Alert errorCreatingThePsychologist = new Alert(Alert.AlertType.INFORMATION);
                errorCreatingThePsychologist.setHeaderText("Server Error");
                errorCreatingThePsychologist.setContentText(ex.getMessage());
                errorCreatingThePsychologist.show();
            }
        }

    }

    private void handleButtonBack(ActionEvent event) {
        stage.close();
    }

    private void check255(String text, Label lbl) throws FieldTooLongException {
        if (text.length() > 255) {
            btnAdd.setDisable(true);
            btnModify.setDisable(true);
            throw new FieldTooLongException();
        } else {
            if (idSelected == 0) {
                btnModify.setDisable(true);
                btnAdd.setDisable(false);
            } else {
                btnAdd.setDisable(true);
                btnModify.setDisable(false);
            }
            lbl.setVisible(false);
        }
    }

    private boolean checkEmptyFields() {
        boolean check = false;
        if (new String(txtOffice.getText()).isEmpty()) {
            try {
                check = true;
                txtOffice.requestFocus();
                throw new EmptyFieldException();
            } catch (EmptyFieldException ex) {
                errorLabel(lblOfficeError, ex);
                LOGGER.severe(ex.getMessage());
            }
        }
        if (new String(txtSpezialitation.getText()).isEmpty()) {
            try {
                check = true;
                txtSpezialitation.requestFocus();
                throw new EmptyFieldException();
            } catch (EmptyFieldException ex) {
                errorLabel(lblSpecializationError, ex);
                LOGGER.severe(ex.getMessage());
            }
        }
        if (new String(txtPassword.getText()).isEmpty()) {
            try {
                check = true;
                txtPassword.requestFocus();
                throw new EmptyFieldException();
            } catch (EmptyFieldException ex) {
                errorLabel(lblPasswordError, ex);
                LOGGER.severe(ex.getMessage());
            }
        }
        if (txtMail.getText().isEmpty()) {
            try {
                check = true;
                txtMail.requestFocus();
                throw new EmptyFieldException();
            } catch (EmptyFieldException ex) {
                errorLabel(lblMailError, ex);
                LOGGER.severe(ex.getMessage());
            }
        }
        if (txtUsername.getText().isEmpty()) {
            try {
                check = true;
                txtUsername.requestFocus();
                throw new EmptyFieldException();
            } catch (EmptyFieldException ex) {
                errorLabel(lblUsernameError, ex);
                LOGGER.severe(ex.getMessage());
            }
        }
        if (txtFullName.getText().isEmpty()) {
            try {
                check = true;
                txtFullName.requestFocus();
                throw new EmptyFieldException();
            } catch (EmptyFieldException ex) {
                errorLabel(lblFullNameError, ex);
                LOGGER.severe(ex.getMessage());
            }
        }
        return check;
    }

    private void errorLabel(Label lbl, Exception ex) {
        lbl.setVisible(true);
        lbl.setText(ex.getMessage());
        lbl.setStyle("-fx-text-fill: red; -fx-font-size: 13px");
        LOGGER.severe(ex.getMessage());

    }

}
