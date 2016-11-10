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

import java.io.IOException;
import java.util.EnumSet;

public class UserInterface extends Application {

    private final TextField searchTermInputArea = new TextField();
    private final ListView cardListOutput = new ListView();
    private final Button searchButton = new Button("Search");
    private final TextArea cardInfo = new TextArea();

    private final ToggleButton whiteButton = new ToggleButton("W");
    private final ToggleButton blueButton = new ToggleButton("U");
    private final ToggleButton blackButton = new ToggleButton("B");
    private final ToggleButton redButton = new ToggleButton("R");
    private final ToggleButton greenButton = new ToggleButton("G");
    private final ToggleButton colorlessButton = new ToggleButton("C");

    private final EnumSet<CardColor> selectedColors = EnumSet.of(CardColor.EMPTY);
    private final UIController controller = new UIController();

    private ObservableList<Card> cardObservableList = FXCollections.observableArrayList(
            controller.getFullCardList()
    );

    private final ObservableList<Format> formats =
            FXCollections.observableArrayList(
                    controller.getAllFormatList()
            );

    private Format currentSelectedFormat;

    @SuppressWarnings("unchecked")
    private final ComboBox formatList = new ComboBox(formats);

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Magic: the Gathering Deck Editor");
        primaryStage.setScene(createScene());
        primaryStage.show();
        try {
            controller.setUpListOfAllSets();
            controller.setUpListOfAllCards();
            controller.buildAllFormatList();
            cardListOutput.setItems(cardObservableList);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Scene createScene() {
        cardListOutput.setEditable(false);

        searchTermInputArea.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
            }
        });

        formatList.setOnAction(event -> chooseFormat());
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

    private void chooseFormat(){
        currentSelectedFormat = (Format)formatList.getSelectionModel().getSelectedItem();
    }

    private void displayFilteredByColorList(){
        if (whiteButton.isSelected() || blueButton.isSelected() || blackButton.isSelected()
                || redButton.isSelected() || greenButton.isSelected()) {
            if(selectedColors.contains(CardColor.EMPTY)){
                selectedColors.remove(CardColor.EMPTY);
            }
            if (whiteButton.isSelected()) {
                selectedColors.add(CardColor.WHITE);
            }
            if (blueButton.isSelected()) {
                selectedColors.add(CardColor.BLUE);
            }
            if (blackButton.isSelected()) {
                selectedColors.add(CardColor.BLACK);
            }
            if (redButton.isSelected()) {
                selectedColors.add(CardColor.RED);
            }
            if (greenButton.isSelected()) {
                selectedColors.add(CardColor.GREEN);
            }
            if (colorlessButton.isSelected()){
                selectedColors.add(CardColor.COLORLESS);
            }
        }
        controller.filterByColor(selectedColors);
    }
}

