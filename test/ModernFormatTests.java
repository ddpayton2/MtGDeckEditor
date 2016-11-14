import org.junit.Assert;
import org.junit.Test;
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

public class ModernFormatTests {

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardMtgSetHandler reader = new CardMtgSetHandler();
    private static final Format.Builder builder = new Format.Builder();
    private static final ModernFormat modernFormat = new ModernFormat(builder);
    private static Format modern = new Format(builder);
    private static final SetSorter sorter = new SetSorter();

    public static void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        try{
            parser.parse(inputStream, reader);
        } catch(SAXException s){
        }
        List<MtgSet> allSetsList = reader.returnAllSetsList();
        sorter.sort(allSetsList);
        modernFormat.buildModernLegalSetsList(allSetsList);
        modern = modernFormat.buildModernFormat();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        setUp();
        return Arrays.asList(new Object[][]{
                {"Modern", modern.getFormatName()},
        });
    }

    @SuppressWarnings({"CanBeFinal", "unused"})
    @Parameterized.Parameter
    private String expectedName;

    @SuppressWarnings({"CanBeFinal", "unused"})
    @Parameterized.Parameter(value = 1)
    private String actualName;

    @Test
    public void modernFormatTests (){
        Assert.assertEquals(expectedName, actualName);
    }
}
