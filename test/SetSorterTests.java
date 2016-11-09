
import com.google.common.collect.Ordering;
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

public class SetSorterTests {

    private List<MtgSet> allSetsList;
    private final SetSorter sorter = new SetSorter();

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = SetSorterTests.class.getResourceAsStream("cards.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        CardSetHandler handler = new CardSetHandler();
        try{
            parser.parse(inputStream, handler);
        } catch(SAXException s){
        }
        allSetsList = handler.returnAllSetsList();
    }

    @Test
    public void setSorterTest(){
        sorter.sort(allSetsList);
        boolean isSorted = Ordering.natural().isOrdered(allSetsList);
        Assert.assertTrue("The list is ordered", isSorted);
    }

}
