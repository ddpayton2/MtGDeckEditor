import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SetDateTests {

    private static SAXParserFactory factory = SAXParserFactory.newInstance();
    private static CardSetHandler reader = new CardSetHandler();
    private static ImmutableList<Set> allSetsList;

    public static void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        parser.parse(inputStream, reader);
        allSetsList = reader.returnAllSetsList();
    }

    @Parameters
    public static Collection<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        setUp();
        return Arrays.asList(new Object[][]{
                {LocalDate.of(1999,11,12), allSetsList.get(0).getReleaseDate()},
                {LocalDate.of(2012,03,30), allSetsList.get(2).getReleaseDate()}
        });
    }

    @Parameterized.Parameter
    public LocalDate expectedReleaseDate;

    @Parameterized.Parameter(value = 1)
    public LocalDate actualReleasedDate;

    @Test
    public void CardSetHandlerTest_readReleaseDates(){
        Assert.assertEquals(expectedReleaseDate, actualReleasedDate);
    }
}