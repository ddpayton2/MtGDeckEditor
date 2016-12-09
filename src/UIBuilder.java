import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("WeakerAccess")
public class UIBuilder {

    public final TextField searchTermInputArea = new TextField();
    public final ListView<Card> cardListOutput = new ListView<>();

    public final TableView<Card> deckListOutput = new TableView<>();
    public final TableColumn frequencyOfCard = new TableColumn("Number");
    public final TableColumn cardNames = new TableColumn("Cards");
    public final Button addCardToMainDeck = new Button("Add to Mainboard");
    public final Button removeCardFromMainDeck = new Button ("Remove from Mainboard");

    public final Button searchButton = new Button("Search");
    public final TextArea cardInfo = new TextArea();

    public final ToggleButton whiteButton = new ToggleButton();
    public final ToggleButton blueButton = new ToggleButton();
    public final ToggleButton blackButton = new ToggleButton();
    public final ToggleButton redButton = new ToggleButton();
    public final ToggleButton greenButton = new ToggleButton();
    public final ToggleButton colorlessButton = new ToggleButton();
    public final Button resetButton = new Button("Reset");
    public final Button formatButton = new Button("GO");
    public boolean isComboBoxEmpty = true;
    public final ChoiceBox<String> formatsOptions = new ChoiceBox<>(
            FXCollections.observableArrayList("Standard","Modern","Legacy","Vintage","Commander (EDH)"));

    public final UIController controller = new UIController();

    public final EnumSet<CardColor> selectedColors = EnumSet.of(CardColor.EMPTY);
    public final ObservableList<Card> cardObservableList = FXCollections.observableArrayList();
    public final ObservableList<Card> filteredList = FXCollections.observableArrayList();
    public final ObservableList<Card> observableDeckList = FXCollections.observableArrayList();

    private final Tooltip searchBarToolTip = new Tooltip();
    private final Tooltip whiteButtonToolTip = new Tooltip();
    private final Tooltip blueButtonToolTip = new Tooltip();
    private final Tooltip blackButtonToolTip = new Tooltip();
    private final Tooltip redButtonToolTip = new Tooltip();
    private final Tooltip greenButtonToolTip = new Tooltip();
    private final Tooltip colorlessButtonToolTip = new Tooltip();
    private final Tooltip resetButtonToolTip = new Tooltip();
    private final Tooltip searchButtonToolTip = new Tooltip();
    private final Tooltip addCardToMainDeckToolTip = new Tooltip();
    private final Tooltip removeCardFromMainDeckToolTip = new Tooltip();
    private final Tooltip formatBoxToolTip = new Tooltip();
    private final Tooltip formatGoButtonToolTip = new Tooltip();

    public final MenuItem newDeck = new MenuItem("New Deck");
    public final MenuItem openFile = new MenuItem("Open File");
    public final MenuItem saveFile = new MenuItem("Save File");
    public final MenuItem quit = new MenuItem("Quit");


    public void main(Stage primaryStage) {

        primaryStage.setTitle("Magic: the Gathering Deck Editor");
        primaryStage.setScene(designLayoutForLoading());
        primaryStage.show();

        ExecutorService service= Executors.newCachedThreadPool();
        service.submit(new Runnable(){

            @Override
            public void run() {
                controller.setUpAll();
                cardObservableList.addAll(controller.getFullCardList());
                Collections.sort(cardObservableList);
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        primaryStage.setTitle("Magic: the Gathering Deck Editor");
                        primaryStage.setScene(createScene());
                        primaryStage.show();

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
                });
            }
        });
    }

    private Scene createScene() {
        createAllToolTipText();
        setAllToolTips();
        setUpDeckListOutputTable();
        cardListOutput.setEditable(false);
        cardInfo.setEditable(false);
        deckListOutput.setEditable(false);
        setColorButtonStyles();
        return designLayoutForBoxes();
    }

    private void setAllToolTips(){
        searchTermInputArea.setTooltip(searchBarToolTip);
        whiteButton.setTooltip(whiteButtonToolTip);
        blueButton.setTooltip(blueButtonToolTip);
        blackButton.setTooltip(blackButtonToolTip);
        redButton.setTooltip(redButtonToolTip);
        greenButton.setTooltip(greenButtonToolTip);
        colorlessButton.setTooltip(colorlessButtonToolTip);
        resetButton.setTooltip(resetButtonToolTip);
        searchButton.setTooltip(searchBarToolTip);
        addCardToMainDeck.setTooltip(addCardToMainDeckToolTip);
        removeCardFromMainDeck.setTooltip(removeCardFromMainDeckToolTip);
        formatsOptions.setTooltip(formatBoxToolTip);
        formatButton.setTooltip(formatGoButtonToolTip);
    }

    private void createAllToolTipText(){
        searchBarToolTip.setText("Enter a word you wish to search for.");
        whiteButtonToolTip.setText("This lets you see cards that contain the color white.");
        blueButtonToolTip.setText("This lets you see cards that contain the color blue.");
        blackButtonToolTip.setText("This lets you see cards that contain the color black.");
        redButtonToolTip.setText("This lets you see cards that contain the color red.");
        greenButtonToolTip.setText("This lets you see cards that contain the color green.");
        colorlessButtonToolTip.setText("This lets you see cards that are colorless or need waste mana.");
        resetButtonToolTip.setText("This resets the search term field, all buttons," +
                " and outputted card list area from the selected format.");
        searchButtonToolTip.setText("Lets you search for the term you have" +
                " written in the text box in your selected format.");
        addCardToMainDeckToolTip.setText("Adds one the selected card to deck list box.");
        removeCardFromMainDeckToolTip.setText("Removes the selected card in the deck list from the deck list");
        formatBoxToolTip.setText("Select the format you want to build a deck in.");
        formatGoButtonToolTip.setText("Changes the outputted list of cards to " +
                "ones that are legal in your selected format.");
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

    private Scene designLayoutForBoxes(){

        MenuBar main = buildMenuBar();
        VBox searchBarAndColorButtonsArea = buildSearchBarAndColorButtonsArea();
        VBox cardInfoDisplayArea = buildCardInfoDisplayAndDeckEditButtonsArea();
        VBox formatAndDeckListArea = buildFormatAndDeckListArea();

        HBox base = new HBox(searchBarAndColorButtonsArea, cardInfoDisplayArea, formatAndDeckListArea);
        VBox layout = new VBox(main,base);
        base.setPadding(new Insets(10,10,10,10));
        base.setSpacing(5);

        String image = getClass().getResource("backgroundImage.jpg").toExternalForm();
        base.setStyle("-fx-background-image: url('" + image + "'); " + "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");

        setStylesForButton();
        cardInfo.setEditable(false);
        layout.setPrefSize(1000,650);
        return new Scene(layout);
    }

    private Scene designLayoutForLoading(){

        Image loadingImage = new Image(getClass().getResourceAsStream("mana.gif"));
        ImageView loadingImageView = new ImageView(loadingImage);
        VBox base = new VBox(loadingImageView);

        base.setAlignment(Pos.CENTER);
        base.setPrefSize(1000,650);
        base.setStyle("-fx-background-color: #000000;");
        loadingImageView.setFitHeight(360);
        loadingImageView.setFitWidth(360);

        base.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(base);
    }

    private VBox buildFormatAndDeckListArea() {
        VBox deckListArea = new VBox( new Label("Deck List:"), deckListOutput);
        HBox formatsArea = new HBox(formatsOptions, formatButton);
        VBox formatBox = new VBox( new Label("Format:"), formatsArea, deckListArea);
        formatBox.setSpacing(10);
        formatsArea.setSpacing(10);
        return formatBox;
    }

    private VBox buildCardInfoDisplayAndDeckEditButtonsArea() {
        VBox deckEditButtonsArea = new VBox(addCardToMainDeck, removeCardFromMainDeck);
        VBox cardInfoAndDeckEdit = new VBox( new Label("Card Information:"), cardInfo, new Label("DeckEdit:"), deckEditButtonsArea);
        cardInfoAndDeckEdit.setSpacing(10);
        deckEditButtonsArea.setSpacing(10);
        return cardInfoAndDeckEdit;
    }

    private VBox buildSearchBarAndColorButtonsArea() {
        HBox setSearchAndResearchBars = new HBox(resetButton,searchButton);
        setSearchAndResearchBars.setSpacing(300);

        HBox colorButtons = new HBox(whiteButton, blueButton, blackButton, redButton, greenButton, colorlessButton);
        VBox searchBarAndCardListResults = new VBox(new Label("Enter Search Term:"), searchTermInputArea,
                setSearchAndResearchBars, new Label("Color:"), colorButtons, new Label("Cards:"), cardListOutput);
        searchBarAndCardListResults.setPadding(new Insets(10,10,10,10));
        colorButtons.setSpacing(10);
        searchBarAndCardListResults.setSpacing(10);
        return searchBarAndCardListResults;
    }

    protected MenuBar buildMenuBar() {

        MenuBar mainMenu = new MenuBar();
        Menu file = new Menu("File");
        file.getItems().addAll(newDeck, openFile, saveFile, quit);
        mainMenu.getMenus().addAll(file);
        return mainMenu;
    }

    private void setStylesForButton(){

        searchButton.setPrefWidth(100);
        resetButton.setPrefWidth(100);
        addCardToMainDeck.setPrefWidth(200);
        removeCardFromMainDeck.setPrefWidth(200);
        cardInfo.setMinHeight(410);
        cardInfo.setMaxWidth(200);
        deckListOutput.setMinHeight(530);
    }

    private void setUpDeckListOutputTable(){

        deckListOutput.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        deckListOutput.getColumns().addAll(cardNames);
        cardNames.prefWidthProperty().bind(deckListOutput.widthProperty().multiply(0.7));
        frequencyOfCard.setResizable(false);
        cardNames.setResizable(false);
    }
}

