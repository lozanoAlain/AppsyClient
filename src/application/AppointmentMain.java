package application;
import entities.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import logic.AppointmentManager;
import logic.ClientManager;
import view.AppointmentWindowController;

public class AppointmentMain extends Application {

    private ClientManager clientManager;
    private AppointmentManager appointmentManager;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppointmentWindow.fxml"));
        Parent root = (Parent) loader.load();
        AppointmentWindowController appointmentWindowController = loader.getController();
               
        //Integer clientId = 1;
        //Client client = clientManager.find(clientId.toString());
        //appointmentWindowController.setClient(client);
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