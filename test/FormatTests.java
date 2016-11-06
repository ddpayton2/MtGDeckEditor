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
import java.util.ArrayList;
import java.util.List;

public class FormatTests {

    private SetSorter sorter = new SetSorter();
    private List<Set> legalSets;
    private List<Card> bannedList = Lists.newArrayList();
    private List<Card> restrictedList = Lists.newArrayList();
    private static final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static final CardSetHandler reader = new CardSetHandler();
    private static List<Set> allSetsList;

    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = FormatTests.class.getResourceAsStream("cards.xml");
        SAXParser parser = factory.newSAXParser();
        parser.parse(inputStream, reader);
        allSetsList = reader.returnAllSetsList();
        sorter.sort();
    }

    @Test
    public void formatTest_setStandardFormatName(){
        Format format = new Format("Standard", legalSets, bannedList, restrictedList);
        String name = "Standard";
        Assert.assertEquals(name, format.getName());
    }

    @Test
    public void formatTest_getStandardLegalSets(){
        legalSets = sorter.getList();  //returning an empty list; need to fix 
        Format format = new Format("Standard", legalSets, bannedList, restrictedList);
        ArrayList<String> standardLegalSets = new ArrayList<>();
        standardLegalSets.add("Battle for Zendikar");
        standardLegalSets.add("Oath of the Gatewatch");
        standardLegalSets.add("Shadows Over Innistrad");
        standardLegalSets.add("Eldritch Moon");
        standardLegalSets.add("Kaladesh");
        Assert.assertEquals(standardLegalSets, legalSets);
    }
}
