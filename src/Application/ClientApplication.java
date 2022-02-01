package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.ResourcesController;

/**
 * Main class from the client application
 * @author Matteo Fernández
 */
public class ClientApplication extends Application {

    /**
     *
     * @param primaryStage The stage for the window
     * @throws Exception Throws the exception in case the window doesnt load
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Resources.fxml"));
        
        Parent root = (Parent) loader.load();
        
        ResourcesController resourcesController = (loader.getController());
        primaryStage.setResizable(Boolean.FALSE);
        resourcesController.setStage(primaryStage);
        resourcesController.initStage(root);
       
    }

    /**
     * Main method of the client application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}