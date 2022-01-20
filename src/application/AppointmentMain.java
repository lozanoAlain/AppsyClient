package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.AppointmentWindowController;

public class AppointmentMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppointmentWindow.fxml"));
        Parent root = (Parent) loader.load();
        AppointmentWindowController appointmentWindowController = loader.getController();
        appointmentWindowController.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }


}