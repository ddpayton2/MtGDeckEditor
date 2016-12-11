import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({"unused", "CanBeFinal", "WeakerAccess"})
@RunWith(Parameterized.class)
public class CardHandlerTests {

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardHandler handler = new CardHandler();
    private static List<Card> allCardList;

    public static void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        parser.parse(inputStream, handler);
        allCardList = handler.getCardList();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        setUp();
        Card testCard = findTestCard("Emrakul, the Promised End");
        assert testCard != null;
        return Arrays.asList(new Object[][]{
                {"Emrakul, the Promised End", testCard.getCardName()},
                {"13", testCard.getCardCost()},
                {"Legendary Creature — Eldrazi", testCard.getCardType()},
                {"Emrakul, the Promised End costs 1 less to cast for each card type among cards in your graveyard.  \n" +
                        "When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.\n" +
                        "Flying, trample, protection from instants", testCard.getCardText()}
        });
    }

    @Parameterized.Parameter
    public String expected;

    @Parameterized.Parameter(value = 1)
    public String actual;

    @Test
    public void CardHandlerTest(){
        Assert.assertEquals(expected, actual);
    }

    /**
     * finds a specific card to use for tests
     * @param cardName the name of the card
     * @return a card with the name that matches cardName, null otherwise
     */
    private static Card findTestCard(String cardName){
        for(Card card : allCardList){
            if(card.getCardName().equalsIgnoreCase(cardName)){
                return card;
            }
        }
        return null;
    }
}
