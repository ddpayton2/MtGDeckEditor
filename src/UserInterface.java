
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class UserInterface extends Application {

    private final TextField searchTermInputArea = new TextField();
    private final TextArea cardListOutput = new TextArea();
    private Button searchButton = new Button("Search");
    private ToggleButton whiteButton = new ToggleButton("W");
    private ToggleButton blueButton = new ToggleButton("U");
    private ToggleButton blackButton = new ToggleButton("B");
    private ToggleButton redButton = new ToggleButton("R");
    private ToggleButton greenButton = new ToggleButton("G");
    private ToggleButton colorlessButton = new ToggleButton("C");
    private UIController controller = new UIController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Magic: the Gathering Deck Editor");
        primaryStage.setScene(createScene());
        primaryStage.show();
        try {
            cardListOutput.setText(controller.setUpArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Scene createScene() {
        cardListOutput.setEditable(false);
        searchButton.setOnAction(event -> {
            try {
                displayCardsBySearchTerm();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        });

        searchTermInputArea.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        displayCardsBySearchTerm();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        whiteButton.setOnAction(event -> displayFilteredByColorList());
        blueButton.setOnAction(event -> displayFilteredByColorList());
        blackButton.setOnAction(event -> displayFilteredByColorList());
        redButton.setOnAction(event -> displayFilteredByColorList());
        greenButton.setOnAction(event -> displayFilteredByColorList());
        colorlessButton.setOnAction(event -> displayFilteredByColorList());

        VBox base = new VBox(
                new Label("Enter Search Term"),
                searchTermInputArea,
                searchButton,
                new HBox(whiteButton, blueButton, blackButton, redButton, greenButton, colorlessButton),
                new Label("Cards"),
                cardListOutput);
        return new Scene(base);
    }

    private void displayCardsBySearchTerm() throws IOException, SAXException {
        searchButton.disarm();
        cardListOutput.clear();
        controller.filteredCardArrayList.clear();
        controller.searchForTerm(searchTermInputArea.getText());
        cardListOutput.setText(controller.showOutputOfFilteredList());
    }

    private void displayFilteredByColorList(){
        if(whiteButton.isSelected()){
            controller.filterByColor("W");
        }
        else if (blueButton.isSelected()){
            controller.filterByColor("U");
        }
        else if(blackButton.isSelected()){
            controller.filterByColor("B");
        }
        else if(redButton.isSelected()){
            controller.filterByColor("R");
        }
        else if(greenButton.isSelected()){
            controller.filterByColor("G");
        }
        else if(colorlessButton.isSelected()){
            controller.filterForColorless();
            controller.filterByColor("C");
        }
        cardListOutput.setText(controller.showColorFilteredList());
    }
}

