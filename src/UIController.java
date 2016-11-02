import com.google.common.collect.Lists;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class UIController {

    private List<Card> fullCardArrayList;
    public final List<Card> filteredCardArrayList = Lists.newArrayList();
    private final List<Card> filteredByColorArrayList = Lists.newArrayList();

    public String setUpArray() throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = spfac.newSAXParser();
        CardHandler handler = new CardHandler();
        sp.parse("cards.xml", handler);
        String output = "";
        fullCardArrayList = handler.getCardList();
        for (Card aFullCardArrayList : fullCardArrayList) {
            output += aFullCardArrayList.getCardName() + "\n";
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
        filteredByColorArrayList.addAll(filteredCardArrayList.stream().filter(card -> card.getCardCost().contains(color)).collect(Collectors.toList()));
    }

    public void filterForColorless(){
        filteredByColorArrayList.addAll(filteredCardArrayList.stream().filter(card -> !card.getCardCost().contains("W") && !card.getCardCost().contains("U") && !card.getCardCost().contains("B")
                && !card.getCardCost().contains("R") && !card.getCardCost().contains("G") && !card.getCardCost().contains("C")).collect(Collectors.toList()));
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
        filteredByColorArrayList.addAll(filteredCardArrayList.stream().filter(card -> card.getCardCost().contains(color)).collect(Collectors.toList()));
    }
}
