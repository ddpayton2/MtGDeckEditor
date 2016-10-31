import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

public class CardHandler extends DefaultHandler{

    public Card card = new Card();
    public String temp;
    public ArrayList<Card> cardList = new ArrayList<>();

    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("card")) {
            card = new Card();
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("card")) {
            cardList.add(card);
        }
        if (qName.equalsIgnoreCase("name")) {
            card.setCardName(temp);
        }
        else if (qName.equalsIgnoreCase("set")) {
            card.setCardSet(temp);
        }
        else if (qName.equalsIgnoreCase("color")) {
            card.setCardColor(temp);
        }
        else if(qName.equalsIgnoreCase("manacost")){
            card.setCardCost(temp);
        }
        else if(qName.equalsIgnoreCase("cmc")){
            card.setCardCmc(temp);
        }
        else if(qName.equalsIgnoreCase("type")){
            card.setCardType(temp);
        }
        else if(qName.equalsIgnoreCase("pt")){
            card.setPowerToughness(temp);
        }
        else if(qName.equalsIgnoreCase("text")){
            card.setCardText(temp);
        }
    }
}
