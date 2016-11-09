import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ModernFormatTests {

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardSetHandler reader = new CardSetHandler();
    private static List<Set> allSetsList;
    private static final Format.Builder builder = new Format.Builder();
    private static final ModernFormat modernFormat = new ModernFormat(builder);
    private static Format modern = new Format(builder);
    private static final SetSorter sorter = new SetSorter();
    private static final List<Set> modernLegalSets = modern.getLegalSets();
    private LocalDate eighthEditionReleaseDate = LocalDate.of(2003,7,28);

    public static void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        try{
            parser.parse(inputStream, reader);
        } catch(SAXException s){
        }
        allSetsList = reader.returnAllSetsList();
        sorter.sort(allSetsList);
        modernFormat.buildModernLegalSetsList(allSetsList);
        modern = modernFormat.buildModernFormat();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws ParserConfigurationException, SAXException, IOException {
        setUp();
        return Arrays.asList(new Object[][]{
                {"Modern", modern.getFormatName()},
                {modernLegalSets.get(6).getReleaseDate()}
        });
    }

    @Parameterized.Parameter
    private String expectedName;

    @Parameterized.Parameter(value = 1)
    private String actualName;

    @Parameterized.Parameter
    private LocalDate setDate;

    @Test
    public void modernFormatTests (){
        Assert.assertEquals(expectedName, actualName);
        Assert.assertTrue(setDate.isEqual(eighthEditionReleaseDate) || setDate.isAfter(eighthEditionReleaseDate));
    }
}
