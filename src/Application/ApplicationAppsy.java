/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.ProfileWindowController;
import view.PsychologistWindowController;
import view.SignUpController;

/**
 *
 * @author Usuario
 */
public class ApplicationAppsy extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileWindow.fxml"));
        Parent root = (Parent) loader.load();
        ProfileWindowController psychologistWindowController = loader.getController();
        psychologistWindowController.initStage(root,1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}
