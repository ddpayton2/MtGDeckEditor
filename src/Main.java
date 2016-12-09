import javafx.application.Application;
import javafx.stage.Stage;

@SuppressWarnings("WeakerAccess")
public class Main extends Application{

    final UserInterface ui = new UserInterface();

    @Override
    public void start(Stage primaryStage){
        ui.launch(primaryStage);
    }
}
