import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;

public class UserInterface extends Application {

    private final TextField searchTermInputArea = new TextField();
    private final ListView<Card> cardListOutput = new ListView<>();
    private final Button searchButton = new Button("Search");
    private final TextArea cardInfo = new TextArea();

    private final ToggleButton whiteButton = new ToggleButton("W");
    private final ToggleButton blueButton = new ToggleButton("U");
    private final ToggleButton blackButton = new ToggleButton("B");
    private final ToggleButton redButton = new ToggleButton("R");
    private final ToggleButton greenButton = new ToggleButton("G");
    private final ToggleButton colorlessButton = new ToggleButton("C");
    private final Button resetButton = new Button("Reset");
    private final Button standardFormatButton = new Button("Standard");
    private final Button modernFormatButton = new Button("Modern");
    private final Button legacyFormatButton = new Button("Legacy");
    private final Button vintageFormatButton = new Button("Vintage");
    private final Button edhFormatButton = new Button("Commander (EDH)");

    private final EnumSet<CardColor> selectedColors = EnumSet.of(CardColor.EMPTY);
    private final UIController controller = new UIController();

    private final ObservableList<Card> cardObservableList = FXCollections.observableArrayList();
    private final ObservableList<Card> filteredList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Magic: the Gathering Deck Editor");
        primaryStage.setScene(createScene());
        primaryStage.show();
        try {
            controller.setUpListOfAllSets();
            controller.setUpListOfAllCards();
            cardObservableList.addAll(controller.getFullCardList());
            Collections.sort(cardObservableList);
            cardListOutput.setItems(cardObservableList);
            cardListOutput.setCellFactory(lv -> new ListCell<Card>(){
                @Override
                public void updateItem(Card card, boolean empty){
                    super.updateItem(card, empty);
                    if(empty){
                        setText(null);
                    }
                    else{
                        setText(card.getCardName());
                    }
                }
            });
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Scene createScene() {

        cardListOutput.setEditable(false);
        cardInfo.setEditable(false);
        setActionForButtons();
        return designLayoutForBoxes();
    }

    private void resetAllOutputFields() {

        searchTermInputArea.clear();
        cardInfo.clear();
        whiteButton.setSelected(false);
        blueButton.setSelected(false);
        redButton.setSelected(false);
        blackButton.setSelected(false);
        greenButton.setSelected(false);
        cardListOutput.setItems(cardObservableList);
    }

    private void useAllFilters(String term) {

        searchForTerm(term);
        displayFilteredByColorList();
    }

    private void searchForTerm(String text) {

        cardListOutput.setItems(FXCollections.observableArrayList(controller.search(text)));
    }

    private void chooseFormat(Format format){

        cardListOutput.setItems(controller.retrieveLegalCardsForFormat(format));
    }

    private void displayFilteredByColorList(){

        selectedColors.clear();
        if (whiteButton.isSelected() || blueButton.isSelected() || blackButton.isSelected()
                || redButton.isSelected() || greenButton.isSelected() || colorlessButton.isSelected()) {
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
        filteredList.setAll(controller.filterByColor(selectedColors));
        cardListOutput.setItems(filteredList);
    }

    private void setActionForButtons(){

        searchButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        whiteButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        blueButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        blackButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        redButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        greenButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        colorlessButton.setOnAction(event -> useAllFilters(searchTermInputArea.getText()));
        resetButton.setOnAction(event -> resetAllOutputFields());
        standardFormatButton.setOnAction(event -> chooseFormat(controller.buildStandardFormat()));
        modernFormatButton.setOnAction(event -> chooseFormat(controller.buildModernFormat()));
        legacyFormatButton.setOnAction(event -> chooseFormat(controller.buildLegacyFormat()));
        vintageFormatButton.setOnAction(event -> chooseFormat(controller.buildVintageFormat()));
        edhFormatButton.setOnAction(event -> chooseFormat(controller.buildEDHFormat()));
        setActionOnPressed();
    }

    private void setActionOnPressed(){

        cardListOutput.setOnMousePressed((event -> {
            cardInfo.setWrapText(true);
            if(event.isPrimaryButtonDown() && event.getClickCount() == 1){
                cardInfo.clear();
                cardInfo.setText(cardListOutput.getSelectionModel().getSelectedItem()
                        .getAllCardInfo());
            }
        }));
        searchTermInputArea.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                useAllFilters(searchTermInputArea.getText());
            }
        });
    }

    private Scene designLayoutForBoxes(){

        HBox setSearchAndResearchBars = new HBox(resetButton,searchButton);
        setSearchAndResearchBars.setSpacing(140);

        VBox searchBarAndCardListResults = new VBox(new Label("Enter Search Term:"), searchTermInputArea,
                setSearchAndResearchBars, new Label("Color:"),
                new HBox(whiteButton, blueButton, blackButton, redButton, greenButton, colorlessButton),
                new Label("Cards:"), cardListOutput);
        searchBarAndCardListResults.setPadding(new Insets(10,10,10,10));
        searchBarAndCardListResults.setSpacing(10);

        VBox cardInfoDisplay = new VBox( new Label("Card Information:"), cardInfo);
        cardInfoDisplay.setSpacing(10);

        VBox formatBox = new VBox( new Label("Format:"), standardFormatButton, modernFormatButton, legacyFormatButton,
                vintageFormatButton, edhFormatButton);
        formatBox.setSpacing(10);

        HBox base = new HBox( searchBarAndCardListResults, cardInfoDisplay, formatBox);
        base.setPadding(new Insets(10,10,10,10));
        base.setSpacing(5);

        setStylesForButton();
        cardInfo.setPrefSize(200,300);
        cardInfo.setEditable(false);

        return new Scene(base);
    }

    private void setStylesForButton(){

        standardFormatButton.setPrefWidth(150);
        modernFormatButton.setPrefWidth(150);
        legacyFormatButton.setPrefWidth(150);
        vintageFormatButton.setPrefWidth(150);
        edhFormatButton.setPrefWidth(150);
    }
}

