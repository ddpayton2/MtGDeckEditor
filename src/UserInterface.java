import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.EnumSet;

public class UserInterface extends Application {

    private final TextField searchTermInputArea = new TextField();
    private final ListView<Card> cardListOutput = new ListView<>();

    private final TableView<Card> deckListOutput = new TableView<>();
    private final TableColumn frequencyOfCard = new TableColumn("Number");
    private final TableColumn cardNames = new TableColumn("Cards");
    private final Button addCardToMainDeck = new Button("Add to Mainboard");
    private final Button removeCardFromMainDeck = new Button ("Remove from Mainboard");
    private final Button addCardToSideboard = new Button("Add to Sideboard");
    private final Button removeCardFromSideboard = new Button("Remove from Sideboard");

    private final Button searchButton = new Button("Search");
    private final TextArea cardInfo = new TextArea();

    private final ToggleButton whiteButton = new ToggleButton();
    private final ToggleButton blueButton = new ToggleButton();
    private final ToggleButton blackButton = new ToggleButton();
    private final ToggleButton redButton = new ToggleButton();
    private final ToggleButton greenButton = new ToggleButton();
    private final ToggleButton colorlessButton = new ToggleButton();
    private final Button resetButton = new Button("Reset");
    private final Button formatButton = new Button("GO");
    private boolean isComboBoxEmpty = true;
    private final ChoiceBox<String> formatsOptions = new ChoiceBox<>(
            FXCollections.observableArrayList("Standard","Modern","Legacy","Vintage","Commander (EDH)"));

    private final EnumSet<CardColor> selectedColors = EnumSet.of(CardColor.EMPTY);

    private final UIController controller = new UIController();

    private final ObservableList<Card> cardObservableList = FXCollections.observableArrayList();
    private final ObservableList<Card> filteredList = FXCollections.observableArrayList();
    private ObservableList<Card> observableDeckList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Magic: the Gathering Deck Editor");
        primaryStage.setScene(createScene());
        primaryStage.show();
        controller.setUpAll();
        controller.buildAllFormats();
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
    }

    private Scene createScene() {

        setUpDeckListOutputTable();
        cardListOutput.setEditable(false);
        cardInfo.setEditable(false);
        deckListOutput.setEditable(false);
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
        colorlessButton.setSelected(false);
        if(isComboBoxEmpty){
            cardListOutput.setItems(cardObservableList);
        }
        else{
            cardListOutput.setItems(controller.getFormatCardList());
        }
    }

    private void useAllFilters(String term) {
        if(!isComboBoxEmpty) {
            controller.searchForTermInFormat(formatsOptions.getValue(), term);
        }
        else{
            searchForTerm(term);
        }
        displayFilteredByColorList();
    }

    private void searchForTerm(String text) {

        cardListOutput.setItems(FXCollections.observableArrayList(controller.search(text)));
    }

    private void chooseFormat(Format format){

        if(format.getFormatName().equalsIgnoreCase("Standard")){
            cardObservableList.clear();
            cardObservableList.setAll(controller.retrieveLegalCardsForFormat(controller.getStandard()));
        }
        else if (format.getFormatName().equalsIgnoreCase("Modern")){
            cardObservableList.clear();
            cardObservableList.setAll(controller.retrieveLegalCardsForFormat(controller.getModern()));
        }
        else if (format.getFormatName().equalsIgnoreCase("Legacy")){
            cardObservableList.clear();
            cardObservableList.setAll(controller.retrieveLegalCardsForFormat(controller.getLegacy()));
        }
        else if(format.getFormatName().equalsIgnoreCase("Vintage")){
            cardObservableList.clear();
            cardObservableList.setAll(controller.retrieveLegalCardsForFormat(controller.getVintage()));
        }
        else if(format.getFormatName().equalsIgnoreCase("Commander (EDH)")){
            cardObservableList.clear();
            cardObservableList.setAll(controller.retrieveLegalCardsForFormat(controller.getEdh()));
        }
        cardListOutput.setItems(cardObservableList);
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
        formatButton.setOnAction(event -> getChoice(formatsOptions));
        addCardToMainDeck.setOnAction(event -> addCardToMain());
        removeCardFromMainDeck.setOnAction(event -> removeFromMain());
        setColorButtonStyles();
        setActionOnPressed();
    }

    private void removeFromMain() {
        observableDeckList.remove(deckListOutput.getSelectionModel().getSelectedItem());
    }

    private void addCardToMain() {

            observableDeckList.add(cardListOutput.getSelectionModel().getSelectedItem());
            deckListOutput.setItems(observableDeckList);
            cardNames.setCellValueFactory(
                    new PropertyValueFactory("cardName")
            );
    }

    private void setColorButtonStyles() {

        Image whiteButtonImage = new Image(getClass().getResourceAsStream("whitePicture.jpg"));
        ImageView whiteButtonImageView = new ImageView(whiteButtonImage);
        whiteButtonImageView.setFitHeight(50);
        whiteButtonImageView.setFitWidth(50);
        whiteButtonImageView.setPreserveRatio(true);
        whiteButton.setGraphic(whiteButtonImageView);

        Image blueButtonImage = new Image(getClass().getResourceAsStream("bluePicture.jpg"));
        ImageView blueButtonImageView = new ImageView(blueButtonImage);
        blueButtonImageView.setFitWidth(50);
        blueButtonImageView.setFitWidth(50);
        blueButtonImageView.setPreserveRatio(true);
        blueButton.setGraphic(blueButtonImageView);

        Image blackButtonImage = new Image(getClass().getResourceAsStream("blackPicture.jpg"));
        ImageView blackButtonImageView = new ImageView(blackButtonImage);
        blackButtonImageView.setFitWidth(50);
        blackButtonImageView.setFitHeight(50);
        blackButtonImageView.setPreserveRatio(true);
        blackButton.setGraphic(blackButtonImageView);

        Image redButtonImage = new Image(getClass().getResourceAsStream("redPicture.jpg"));
        ImageView redButtonImageView = new ImageView(redButtonImage);
        redButtonImageView.setFitWidth(50);
        redButtonImageView.setFitHeight(50);
        redButtonImageView.setPreserveRatio(true);
        redButton.setGraphic(redButtonImageView);

        Image greenButtonImage = new Image(getClass().getResourceAsStream("greenPicture.jpg"));
        ImageView greenButtonImageView = new ImageView(greenButtonImage);
        greenButtonImageView.setFitWidth(50);
        greenButtonImageView.setFitHeight(50);
        greenButtonImageView.setPreserveRatio(true);
        greenButton.setGraphic(greenButtonImageView);

        Image colorlessButtonImage = new Image(getClass().getResourceAsStream("colorlessPicture.jpg"));
        ImageView colorlessButtonImageView = new ImageView(colorlessButtonImage);
        colorlessButtonImageView.setFitWidth(50);
        colorlessButtonImageView.setFitHeight(50);
        colorlessButtonImageView.setPreserveRatio(true);
        colorlessButton.setGraphic(colorlessButtonImageView);
    }

    private void getChoice(ChoiceBox<String> formatsOptions){

        isComboBoxEmpty = false;
        String formats = formatsOptions.getValue();
        if(formats.equalsIgnoreCase("Legacy")){
            chooseFormat(controller.getLegacy());
        }
        else if(formats.equalsIgnoreCase("Modern")){
            chooseFormat(controller.getModern());
        }
        else if(formats.equalsIgnoreCase("Standard")){
            chooseFormat(controller.getStandard());
        }
        else if(formats.equalsIgnoreCase("Vintage")){
            chooseFormat(controller.getVintage());
        }
        else if(formats.equalsIgnoreCase("Commander (EDH)")){
            chooseFormat(controller.getEdh());
        }
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
        deckListOutput.setOnMousePressed((event -> {
            cardInfo.setWrapText(true);
            if(event.isPrimaryButtonDown() && event.getClickCount() == 1){
                cardInfo.clear();
                cardInfo.setText(deckListOutput.getSelectionModel().getSelectedItem()
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

        MenuBar mainMenu = new MenuBar();

        Menu file = new Menu("File");
        Menu help = new Menu("Help");
        mainMenu.getMenus().addAll(file, help);

        MenuItem openFile = new MenuItem("Open File");
        MenuItem saveFile = new MenuItem("Save File");
        MenuItem quit = new MenuItem("Quit");
        file.getItems().addAll(openFile, saveFile, quit);

        //openFile.setOnAction(event -> openDeckFile());
        saveFile.setOnAction(event -> saveDeckFile());
        quit.setOnAction(event -> System.exit(0));

        HBox setSearchAndResearchBars = new HBox(resetButton,searchButton);
        setSearchAndResearchBars.setSpacing(300);

        VBox searchBarAndCardListResults = new VBox(new Label("Enter Search Term:"), searchTermInputArea,
                setSearchAndResearchBars, new Label("Color:"),
                new HBox(whiteButton, blueButton, blackButton, redButton, greenButton, colorlessButton),
                new Label("Cards:"), cardListOutput);
        searchBarAndCardListResults.setPadding(new Insets(10,10,10,10));
        searchBarAndCardListResults.setSpacing(10);

        VBox deckEditButtonsArea = new VBox(addCardToMainDeck, removeCardFromMainDeck, addCardToSideboard, removeCardFromSideboard);
        VBox cardInfoDisplay = new VBox( new Label("Card Information:"), cardInfo, new Label("DeckEdit:"), deckEditButtonsArea);
        cardInfoDisplay.setSpacing(10);
        deckEditButtonsArea.setSpacing(10);

        VBox deckListArea = new VBox( new Label("Deck List:"), deckListOutput);

        HBox formatsArea = new HBox(formatsOptions, formatButton);
        VBox formatBox = new VBox( new Label("Format:"), formatsArea, deckListArea);
        formatBox.setSpacing(10);
        formatsArea.setSpacing(10);

        HBox base = new HBox(searchBarAndCardListResults, cardInfoDisplay, formatBox);
        VBox layout = new VBox(mainMenu,base);
        base.setPadding(new Insets(10,10,10,10));
        base.setSpacing(5);

        setStylesForButton();
        cardInfo.setEditable(false);

        return new Scene(layout);
    }

    private void setStylesForButton(){

        searchButton.setPrefWidth(100);
        resetButton.setPrefWidth(100);
        addCardToMainDeck.setPrefWidth(200);
        removeCardFromMainDeck.setPrefWidth(200);
        addCardToSideboard.setPrefWidth(200);
        removeCardFromSideboard.setPrefWidth(200);
        cardInfo.setMinHeight(410);
        cardInfo.setMaxWidth(200);
        deckListOutput.setMinHeight(530);
    }

    private void setUpDeckListOutputTable(){

        deckListOutput.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        deckListOutput.getColumns().addAll(frequencyOfCard,cardNames);
        frequencyOfCard.prefWidthProperty().bind(deckListOutput.widthProperty().multiply(0.3));
        cardNames.prefWidthProperty().bind(deckListOutput.widthProperty().multiply(0.7));
        frequencyOfCard.setResizable(false);
        cardNames.setResizable(false);
    }

    private void saveDeckFile(){
        try{
            FileOutputStream output = new FileOutputStream("output");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
            objectOutputStream.writeObject(observableDeckList);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

