/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import entities.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import logic.UserFactory;
import logic.UserInterface;
import view.ProfileWindowController;
import view.PsychologistWindowController;
import view.SignInWindowController;
import view.SignUpController;
import view.WelcomeAdminWindowController;

/**
 *
 * @author Alain Lozano Isasi, Ilia Consuegra
 */
public class ApplicationAppsy extends Application {
    
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInWindow.fxml"));
        Parent root = (Parent) loader.load();
        SignInWindowController signInWindowController = loader.getController();
        signInWindowController.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}
