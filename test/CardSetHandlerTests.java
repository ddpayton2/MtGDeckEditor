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
public class CardSetHandlerTests {

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardMtgSetHandler reader = new CardMtgSetHandler();
    private static List<MtgSet> allSetsList;

    public static void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        try{
            parser.parse(inputStream, reader);
        } catch(SAXException s){
        }
        allSetsList = reader.returnAllSetsList();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        setUp();
        return Arrays.asList(new Object[][]{
                {"BRB", allSetsList.get(0).getMtgSetName()},
                {"ARN", allSetsList.get(1).getMtgSetName()},
                {"Battle Royale Box MtgSet", allSetsList.get(0).getLongname()},
                {"Prophecy", allSetsList.get(3).getLongname()},
                {"Box", allSetsList.get(0).getMtgSetType()},
                {"Duel deck", allSetsList.get(8).getMtgSetType()}
        });
    }

    @Parameterized.Parameter
    public String expected;

    @Parameterized.Parameter(value = 1)
    public String actual;

    @Test
    public void CardSetHandlerTest(){
        Assert.assertEquals(expected, actual);
    }
}
