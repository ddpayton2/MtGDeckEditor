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

@SuppressWarnings({"unused", "CanBeFinal"})
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
        return Arrays.asList(new Object[][]{
                {"Kor Line-Slinger", allCardList.get(0).getCardName()},
                {"Rhox Pikemaster", allCardList.get(2).getCardName()},
                {"Pang Tong, \"Young Phoenix\"", allCardList.get(7).getCardName()},
                {"W", allCardList.get(0).getCardColor()},
                {"B", allCardList.get(9).getCardColor()},
                {"C", allCardList.get(4).getCardColor()},
                {"4B", allCardList.get(9).getCardCost()},
                {"9", allCardList.get(4).getCardCost()},
                {"Creature — Eldrazi", allCardList.get(4).getCardType()},
                {"Land", allCardList.get(5).getCardType()},
                {"Spawning Pool enters the battlefield tapped." +
                        "{T}: Add {B} to your mana pool." +
                        "{1}{B}: Spawning Pool becomes a 1/1 black Skeleton creature with \"{B}: Regenerate this creature\" until end of turn. " +
                        "It's still a land. (If it regenerates, the next time it would be destroyed this turn, it isn't. " +
                        "Instead tap it, remove all damage from it, and remove it from combat.)", allCardList.get(5).getCardText()},
                {"Level up {4} ({4}: Put a level counter on this. Level up only as a sorcery.)" +
                "LEVEL 1-3" + "2/6" + "Vigilance" + "LEVEL 4+" + "3/10" + "Vigilance", allCardList.get(3).getCardText()},
        });
    }

    @Parameterized.Parameter
    private String expected;

    @Parameterized.Parameter(value = 1)
    private String actual;

    @Test
    public void CardHandlerTest(){
        Assert.assertEquals(expected, actual);
    }
}
