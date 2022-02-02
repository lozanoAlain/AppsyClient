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
import view.SignUpController;
import view.WelcomeAdminWindowController;

/**
 *
 * @author Usuario
 */
public class ApplicationAppsy extends Application {
    
    

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserInterface userInterface = UserFactory.createUsersRestful();
        User user = userInterface.find("8");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WelcomeAdmin.fxml"));
        Parent root = (Parent) loader.load();
        WelcomeAdminWindowController psychologistWindowController = loader.getController();
        psychologistWindowController.initialize(root,user);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}
