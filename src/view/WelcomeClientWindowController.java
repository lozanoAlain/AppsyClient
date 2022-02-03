/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Matteo Fernández
 */
public class WelcomeClientWindowController implements Initializable {

    @FXML
    private Label lblGreeting;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnLogOut;
  

    //Getters and Setters
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
    Stage stage = new Stage();
    /**
     * Initializes the controller of the Welcome Psychologists window.
     *
     * @param root
     */
    public void initStage(Parent root) {
        //Exit button (btnExit) and Log out button (btnLogOut) are enabled.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Initializing stage.");

        //The window title
        stage.setTitle("Welcome window client");

        //The window (WelcomeWindow) is not resizable.
        stage.setResizable(false);

        //some tooltips to help the user
        btnExit.setTooltip(new Tooltip("Click to exit"));
        btnLogOut.setTooltip(new Tooltip("Click to log out (You will be redirected to log in window)"));

        btnLogOut.setOnAction(this::handleBtnLogOutPressed);
        btnExit.setOnAction(this::handleBtnLogOutPressed);
        stage.show();
    }

    @FXML
    private void handleBtnLogOutPressed(ActionEvent event) {
        //Shows an alert to the user and verifies that they want to exit or not.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to log out of the app?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() != ButtonType.OK) {
            //Doesn´t want to exit
            this.btnLogOut.setText("Cancelling it...");
        } else {
            //They choose to log out
            this.btnLogOut.setText("Bye, thanks for using our app.");

            //Goes to the Sign in window
            /*    FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInWindow.fxml"));
            Parent root = (Parent) loader.load();
            
            SignInWindowController signInWindowController = (loader.getController());
            signInWindowController.setStage(stage);
            signInWindowController.initStage(root);*/
        }

    }

    /**
     *
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleBtnExitPressed(ActionEvent event) throws IOException {
        //An alert is shown to the user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirmation");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() != ButtonType.OK) {
            //Doesn´t want to exit
            this.btnExit.setText("Cancelling it...");
        } else {
            //They choose to exit
            this.btnExit.setText("Bye, thanks for using our app.");
            stage.close();
        }
        Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Exit button pressed.");
        stage.close();
    }

    /**
     *
     * @param event
     */
    @FXML
    public void lblReadResources(ActionEvent event
    ) {
        try {
            //Opens the Manage Psychologists
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources.fxml"));

            //Creates a new stage
            Stage stageManage = new Stage();
            Parent root = (Parent) loader.load();

            //Gets ResourcesManager controller
            //PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());
            //Set the stage that we already created to the manage psychologist controller
            //psychologistWindowController.setStage(stageManage);
            //Opening application as modal
            stageManage.initModality(Modality.APPLICATION_MODAL);
            stageManage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            //.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void lblEditProfiles(ActionEvent event
    ) {
        try {
            //Opens the Manage Psychologists
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));

            //Creates a new stage
            Stage stageManage = new Stage();
            Parent root = (Parent) loader.load();

            //Gets Profile controller
            //PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());
            //Set the stage that we already created to the manage psychologist controller
            //psychologistWindowController.setStage(stageManage);
            //Opening application as modal
            stageManage.initModality(Modality.APPLICATION_MODAL);
            stageManage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            //.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void lblManageApointments(ActionEvent event
    ) {
        try {
            //Opens the Manage Psychologists
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApointmentsWindow.fxml"));

            //Creates a new stage
            Stage stageManage = new Stage();
            Parent root = (Parent) loader.load();

            //Gets ManageApointments controller
            //PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());
            //Set the stage that we already created to the manage psychologist controller
            //psychologistWindowController.setStage(stageManage);
            //Opening application as modal
            stageManage.initModality(Modality.APPLICATION_MODAL);
            stageManage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            //.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void handleMenuResources(ActionEvent event
    ) {
        try {
            //Opens the Manage Psychologists
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources.fxml"));

            //Creates a new stage
            Stage stageManage = new Stage();
            Parent root = (Parent) loader.load();

            //Gets ManageApointments controller
            //PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());
            //Set the stage that we already created to the manage psychologist controller
            //psychologistWindowController.setStage(stageManage);
            //Opening application as modal
            stageManage.initModality(Modality.APPLICATION_MODAL);
            stageManage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            //.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void handleMenuAppointments(ActionEvent event
    ) {
        try {
            //Opens the Manage Psychologists
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ApointmentsWindow.fxml"));

            //Creates a new stage
            Stage stageManage = new Stage();
            Parent root = (Parent) loader.load();

            //Gets ManageApointments controller
            //PsychologistWindowController psychologistWindowController = ((PsychologistWindowController) loader.getController());
            //Set the stage that we already created to the manage psychologist controller
            //psychologistWindowController.setStage(stageManage);
            //Opening application as modal
            stageManage.initModality(Modality.APPLICATION_MODAL);
            stageManage.initOwner(
                    ((Node) event.getSource()).getScene().getWindow());

            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.INFO, "Initializing stage.");
            //.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(WelcomeAdminWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
