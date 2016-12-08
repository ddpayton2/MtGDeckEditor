import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

@SuppressWarnings("WeakerAccess")
public class UserInterface extends UIBuilder{

    public void launch(Stage primaryStage) {
        super.main(primaryStage);
        setActionForButtons();
        setActionOnPressed();
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
        newDeck.setOnAction(event -> startNewDeck());
        openFile.setOnAction(event -> openDeckFile());
        saveFile.setOnAction(event -> saveDeckFile());
        quit.setOnAction(event -> System.exit(0));
    }

    private void useAllFilters(String term) {

        if(!isComboBoxEmpty) {
            cardListOutput.setItems(FXCollections.observableArrayList(controller.searchForTermInFormat(formatsOptions.getValue(), term)));
        }
        else{
            searchForTerm(term);
        }
        displayFilteredList();
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

    private void addCardToMain() {

        observableDeckList.add(cardListOutput.getSelectionModel().getSelectedItem());
        deckListOutput.setItems(observableDeckList);
        //noinspection unchecked
        cardNames.setCellValueFactory(
                new PropertyValueFactory("cardName")
        );
    }

    private void removeFromMain() {
        observableDeckList.remove(deckListOutput.getSelectionModel().getSelectedItem());
    }

    private void searchForTerm(String text) {

        cardListOutput.setItems(FXCollections.observableArrayList(controller.search(text)));
    }

    private void displayFilteredList(){

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

    private void startNewDeck(){
        observableDeckList.clear();
    }

    private void openDeckFile(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter =new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null){
            try {
                observableDeckList.clear();
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                while((line = reader.readLine()) != null){
                    for(Card card : controller.getFullCardList()){
                        if(card.getCardName().equalsIgnoreCase(line)){
                            observableDeckList.add(card);
                        }
                    }
                }
                deckListOutput.setItems(observableDeckList);
                //noinspection unchecked
                cardNames.setCellValueFactory(
                        new PropertyValueFactory("cardName")
                );
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void saveDeckFile(){
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter =new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File file = fileChooser.showSaveDialog(null);

            if (file != null){
                BufferedWriter fileWriter;

                fileWriter = new BufferedWriter(new FileWriter(file));
                for(int i = 0; i < observableDeckList.size(); i++){
                    fileWriter.write(observableDeckList.get(i).getCardName());
                    fileWriter.newLine();
                }
                fileWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
}
