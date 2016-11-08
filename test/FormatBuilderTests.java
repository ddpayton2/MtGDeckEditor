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
import java.time.LocalDate;
import java.util.List;

public class FormatBuilderTests {

    private List<Set> allSetsList;
    private FormatBuilder builder = new FormatBuilder();
    private SetSorter sorter = new SetSorter();
    private List<Set> sortedList = Lists.newArrayList();

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = SetSorterTests.class.getResourceAsStream("cards.xml");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        CardSetHandler handler = new CardSetHandler();
        try{
            parser.parse(inputStream, handler);
        }
        catch(CardSetHandler.DoneParsingException e){
        }
        catch(SAXException s){
        }
        allSetsList = handler.returnAllSetsList();
        sortedList = sorter.sort(allSetsList);
    }

    @Test
    public void formatBuilderTests_buildStandardLegalSets(){
        builder.buildStandardLegalSetsList(sortedList);
        List<Set> legalSets = Lists.newArrayList();
        for(Set set : allSetsList){
            if(set.getSetName().equalsIgnoreCase("BFZ") || set.getSetName().equalsIgnoreCase("OGW") ||
                    set.getSetName().equalsIgnoreCase("SOI") || set.getSetName().equalsIgnoreCase("EMN")
                    || set.getSetName().equalsIgnoreCase("KLD")){
                legalSets.add(set);
            }
        }
        Assert.assertEquals(legalSets, builder.getStandardLegalSetList());
    }

    @Test
    public void formatBuilderTests_buildModernLegalSets(){
        builder.buildModernLegalSetsList(sortedList);
        List<Set> modernLegalSets = Lists.newArrayList();
        LocalDate eighthEdReleaseDate = LocalDate.of(2003, 07, 28);
        for(Set set : allSetsList){
            if(set.getReleaseDate().isEqual(eighthEdReleaseDate) || (set.getReleaseDate().isAfter(eighthEdReleaseDate)
            && set.getSetType().equalsIgnoreCase("expansion")) || set.getReleaseDate().isAfter(eighthEdReleaseDate) && set.getSetType().equalsIgnoreCase("core")){
                modernLegalSets.add(set);
            }
        }
        Assert.assertEquals(modernLegalSets, builder.getModernLegalSets());
    }
}
