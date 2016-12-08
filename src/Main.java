import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    UserInterface ui = new UserInterface();

    @Override
    public void start(Stage primaryStage){
        ui.launch(primaryStage);
    }
}
