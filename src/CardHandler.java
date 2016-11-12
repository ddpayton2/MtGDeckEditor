import com.google.common.collect.Lists;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class CardHandler extends DefaultHandler{

    @SuppressWarnings("WeakerAccess")
    private Card card = new Card();
    @SuppressWarnings("WeakerAccess")
    private final StringBuilder builder = new StringBuilder();
    public final List<Card> cardList = Lists.newArrayList();
    private String temp;

    public void characters(char[] buffer, int start, int length) {
        builder.append(buffer, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
            if (qName.equalsIgnoreCase("card")) {
                card = new Card();
            }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        temp = builder.toString().trim();
        if (qName.equalsIgnoreCase("card")) {
            cardList.add(card);
        }
        if (qName.equalsIgnoreCase("name")) {
            card.setCardName(temp);
        }
        else if (qName.equalsIgnoreCase("color")) {
            CardColor color = CardColor.parse(temp);
            if(color != null){
                card.addCardColor(color);
            }
        }
        else if(qName.equalsIgnoreCase("manacost")){
            card.setCardCost(temp);
        }
        else if(qName.equalsIgnoreCase("type")){
            card.setCardType(temp);
        }
        else if(qName.equalsIgnoreCase("text")){
            card.setCardText(temp.replaceAll("\n", "").replaceAll("&quot;", "'").replaceAll("&#39;", "'"));
        }
        else if(qName.equalsIgnoreCase("pt")){
            card.setPT(temp);
        }
        else if(qName.equalsIgnoreCase("set")){
            card.addMtgSet(temp);
        }
        builder.setLength(0);
    }

    public List<Card> getCardList(){
        return cardList;
    }
}
