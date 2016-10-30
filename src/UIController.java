import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;

public class UIController {

    private ArrayList<Card> fullCardArrayList = new ArrayList<>();
    public ArrayList<Card> filteredCardArrayList = new ArrayList<>();
    private ArrayList<Card> filteredByColorArrayList = new ArrayList<>();

    public String setUpArray() throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = spfac.newSAXParser();
        cardHandler handler = new cardHandler();
        sp.parse("cards.xml", handler);
        String output = "";
        fullCardArrayList = handler.cardList;
        for(int i = 0; i < fullCardArrayList.size(); i++){
            output += fullCardArrayList.get(i).getCardName() + "\n";
        }
        return output;
    }

    public void searchForTerm(String term){
        for(Card card: fullCardArrayList){
            if(card.getCardName().toUpperCase().contains(term.toUpperCase())){
                filteredCardArrayList.add(card);
            }
            else if(card.getCardText().toUpperCase().contains(term.toUpperCase())){
                filteredCardArrayList.add(card);
            }
            else if (card.getCardType().toUpperCase().contains(term.toUpperCase())){
                filteredCardArrayList.add(card);
            }
        }
    }

    public String showOutputOfFilteredList(){
        String output = "";
        for(Card card: filteredCardArrayList){
            output += card.getCardName() + "\n";
        }
        return output;
    }

    public void filterByColor(String color){
        for(Card card: filteredCardArrayList){
            if(card.getCardCost().contains(color)){
                filteredByColorArrayList.add(card);
            }
        }
    }

    public void filterForColorless(){
        for(Card card: filteredCardArrayList){
            if(!card.getCardCost().contains("W") && !card.getCardCost().contains("U") && !card.getCardCost().contains("B")
                    && !card.getCardCost().contains("R") && !card.getCardCost().contains("G") && !card.getCardCost().contains("C")){
                filteredByColorArrayList.add(card);
            }
        }
    }

    public String showColorFilteredList(){
        String output = "";
        for(Card card: filteredByColorArrayList){
            output += card.getCardName() + "\n";
        }
        return output;
    }

    public void filterByMoreThanOneColor(String color){
        filteredByColorArrayList.clear();
        for(Card card: filteredCardArrayList){
            if(card.getCardCost().contains(color)){
                filteredByColorArrayList.add(card);
            }
        }
    }
}
