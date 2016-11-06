import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class UserInterface extends Application {

    private final TextField searchTermInputArea = new TextField();
    private final TextArea cardListOutput = new TextArea();
    private final Button searchButton = new Button("Search");
    private final ToggleButton whiteButton = new ToggleButton("W");
    private final ToggleButton blueButton = new ToggleButton("U");
    private final ToggleButton blackButton = new ToggleButton("B");
    private final ToggleButton redButton = new ToggleButton("R");
    private final ToggleButton greenButton = new ToggleButton("G");
    private final ToggleButton colorlessButton = new ToggleButton("C");
    private final TextArea cardInfo = new TextArea();
    private final ObservableList<String> formats =
            FXCollections.observableArrayList(
                    "Standard",
                    "Modern",
                    "Legacy",
                    "Vintage",
                    "Commander (EDH)"
            );
    @SuppressWarnings("unchecked")
    private final ComboBox formatList = new ComboBox(formats);
    private final UIController controller = new UIController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Magic: the Gathering Deck Editor");
        primaryStage.setScene(createScene());
        primaryStage.show();
        try {
            cardListOutput.setText(controller.setUpArray());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Scene createScene() {
        cardListOutput.setEditable(false);
        searchButton.setOnAction(event ->
                displayCardsBySearchTerm()
        );

        searchTermInputArea.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                    displayCardsBySearchTerm();
            }
        });

        whiteButton.setOnAction(event -> displayFilteredByColorList());
        blueButton.setOnAction(event -> displayFilteredByColorList());
        blackButton.setOnAction(event -> displayFilteredByColorList());
        redButton.setOnAction(event -> displayFilteredByColorList());
        greenButton.setOnAction(event -> displayFilteredByColorList());
        colorlessButton.setOnAction(event -> displayFilteredByColorList());

        VBox searchBarAndCardListResults = new VBox(
                        new Label("Enter Search Term"),
                        searchTermInputArea,
                        searchButton,
                        new HBox(whiteButton, blueButton, blackButton, redButton, greenButton, colorlessButton),
                        new Label("Cards"),
                        cardListOutput
        );

        cardInfo.setMaxSize(200,400);
        cardInfo.setEditable(false);

        VBox cardInfoDisplay = new VBox(
                new Label("Card Information"),
                cardInfo
        );

        cardInfoDisplay.setAlignment(Pos.TOP_CENTER);

        VBox formatBox = new VBox(
                new Label("Format"),
                new VBox(formatList)
        );

        formatBox.setAlignment(Pos.TOP_RIGHT);

        HBox base = new HBox(
                searchBarAndCardListResults,
                cardInfoDisplay,
                formatBox
        );
        return new Scene(base);
    }

    private void displayCardsBySearchTerm() {
        searchButton.disarm();
        cardListOutput.clear();
        controller.filteredCardArrayList.clear();
        controller.searchForTerm(searchTermInputArea.getText());
        displayFilteredByColorList();
    }

    private void displayFilteredByColorList(){
        String filterColors = "";

        if (whiteButton.isSelected() || blueButton.isSelected() || blackButton.isSelected()
                || redButton.isSelected() || greenButton.isSelected()) {
            if (whiteButton.isSelected()) {
                filterColors = filterColors + "W";
            }
            if (blueButton.isSelected()) {
                filterColors = filterColors + "U";
            }
            if (blackButton.isSelected()) {
                filterColors = filterColors + "B";
            }
            if (redButton.isSelected()) {
                filterColors = filterColors + "R";
            }
            if (greenButton.isSelected()) {
                filterColors = filterColors + "G";
            }

        }

        if (colorlessButton.isSelected()){
            controller.filterForColorless();
        }
        controller.filterByMoreThanOneColor(filterColors);
        cardListOutput.setText(controller.showColorFilteredList());
    }
}

