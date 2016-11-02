
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
public class CardSetHandlerTests {

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardSetHandler reader = new CardSetHandler();
    private static List<Set> allSetsList;

    public static void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        parser.parse(inputStream, reader);
        allSetsList = reader.returnAllSetsList();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        setUp();
        return Arrays.asList(new Object[][]{
                {"BRB", allSetsList.get(0).getSetName()},
                {"ARN", allSetsList.get(1).getSetName()},
                {"Battle Royale Box Set", allSetsList.get(0).getLongname()},
                {"Prophecy", allSetsList.get(3).getLongname()},
                {"Box", allSetsList.get(0).getSetType()},
                {"Duel deck", allSetsList.get(8).getSetType()}
        });
    }

    @SuppressWarnings("WeakerAccess")
    @Parameterized.Parameter
    public String expected;

    @SuppressWarnings("WeakerAccess")
    @Parameterized.Parameter(value = 1)
    public String actual;

    @Test
    public void CardSetHandlerTest(){
        Assert.assertEquals(expected, actual);
    }
}
