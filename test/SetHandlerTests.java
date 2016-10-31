import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class SetHandlerTests {

    private final SAXParserFactory factory = SAXParserFactory.newInstance();
    private CardSetHandler reader = new CardSetHandler();

    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        parser.parse(inputStream, reader);
    }

    @Test
    public void setHandlerTest_ReadFirstSetName() throws SAXException {
        Assert.assertEquals("BRB", reader.allSetsList.get(0).getSetName());
    }

    @Test
    public void setHandlerTest_ReadSecondSetName(){
        Assert.assertEquals("ARN", reader.allSetsList.get(1).getSetName());
    }

    @Test
    public void setHandlerTest_ReadFirstSetLongname(){
        Assert.assertEquals("Battle Royale Box Set", reader.allSetsList.get(0).getLongname());
    }

    @Test
    public void setHandlerTest_ReadFourthSetLongname(){
        Assert.assertEquals("Prophecy", reader.allSetsList.get(3).getLongname());
    }

    @Test
    public void setHandlerTest_ReadFirstSetSetType(){
        Assert.assertEquals("Box", reader.allSetsList.get(0).getSetType());
    }

    @Test
    public void setHandlerTest_ReadNinthSetType(){
        Assert.assertEquals("Duel deck", reader.allSetsList.get(8).getSetType());
    }

    @Test
    public void setHandlerTest_ReadFirstReleaseDate(){
        Assert.assertEquals("19991112", reader.allSetsList.get(0).getReleaseDate());
    }

    @Test
    public void setHandlerTest_ReadThirdReleaseDate(){
        Assert.assertEquals("20120330", reader.allSetsList.get(2).getReleaseDate());
    }
}
