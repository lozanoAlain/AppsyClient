package application;
import entities.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import logic.AppointmentFactory;
import logic.AppointmentInterface;
import logic.AppointmentManager;
import logic.ClientFactory;
import logic.ClientInterface;
import logic.ClientManager;
import view.AppointmentWindowController;

public class AppointmentMain extends Application {

    
    private ClientInterface clientManager = null;
    private AppointmentInterface appointmentManager = null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        clientManager = ClientFactory.createClientInterface();
        appointmentManager = AppointmentFactory.createAppointmentInterface();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppointmentWindow.fxml"));
        Parent root = (Parent) loader.load();
        AppointmentWindowController appointmentWindowController = loader.getController();
               
        Integer clientId = 1;
        Client client = clientManager.find(String.valueOf(clientId));
        appointmentWindowController.setClient(client);
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