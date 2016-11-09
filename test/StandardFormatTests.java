import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class StandardFormatTests{

    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardSetHandler reader = new CardSetHandler();
    private List<MtgSet> allSetsList;
    private final Format.Builder builder = new Format.Builder();
    private final StandardFormat standardFormat = new StandardFormat(builder);
    private Format standard = new Format(builder);
    private final SetSorter sorter = new SetSorter();

    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = CardSetHandlerTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        try{
            parser.parse(inputStream, reader);
        } catch(SAXException s){
        }
        allSetsList = reader.returnAllSetsList();
        sorter.sort(allSetsList);
        standardFormat.buildStandardLegalSetsList(allSetsList);
        standard = standardFormat.buildStandardFormat();
    }

    @Test
    public void testStandardLegalSets(){
        List<MtgSet> standardLegalMtgSets = Lists.newArrayList();
        standardLegalMtgSets.addAll(allSetsList.stream().filter(set -> set.getMtgSetName().equalsIgnoreCase("BFZ") || set.getMtgSetName().equalsIgnoreCase("OGW") ||
                set.getMtgSetName().equalsIgnoreCase("SOI") || set.getMtgSetName().equalsIgnoreCase("EMN") ||
                set.getMtgSetName().equalsIgnoreCase("KLD")).collect(Collectors.toList()));
        Assert.assertTrue(standard.getLegalSets().containsAll(standardLegalMtgSets));
    }

    @Test
    public void testStandardFormatName(){
        String name = "Standard";
        Assert.assertEquals(name, standard.getFormatName());
    }

    @Test
    public void testStandardBannedList(){
        List<Card> expectedBannedList = Lists.newArrayList();
        Assert.assertEquals(expectedBannedList, standard.getBannedList());
    }

    @Test
    public void testStandardRestrictedList(){
        List<Card> expectedRestrictedList = Lists.newArrayList();
        Assert.assertEquals(expectedRestrictedList, standard.getRestrictedList());
    }
}
