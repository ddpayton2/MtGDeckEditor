import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class UIController {

    private List<Card> fullCardList;
    private List<MtgSet> fullMtgSetList;
    private final Format.Builder formatBuilder = new Format.Builder();
    private final CardFilter filter = new CardFilter();
    private final ObservableList<Card> formatCardList = FXCollections.observableArrayList();
    public void setUpListOfAllCards() throws IOException, SAXException {
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = null;
        try {
            sp = spfac.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        CardHandler handler = new CardHandler();
        assert sp != null;
        sp.parse("cards.xml", handler);
        fullCardList = handler.getCardList();
    }

    public void setUpListOfAllSets() throws IOException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        CardMtgSetHandler mtgSetHandler = new CardMtgSetHandler();
        try{
            assert parser != null;
            parser.parse("cards.xml",mtgSetHandler);
        }
        catch (CardMtgSetHandler.DoneParsingException ignored){
        }
        fullMtgSetList = mtgSetHandler.returnAllSetsList();
    }

    public Format buildStandardFormat(){
        StandardFormat standardFormat = new StandardFormat(formatBuilder);
        standardFormat.buildStandardLegalSetsList(fullMtgSetList);
        return standardFormat.buildStandardFormat();
    }

    public Format buildModernFormat(){
        ModernFormat modernFormat = new ModernFormat(formatBuilder);
        modernFormat.buildModernLegalSetsList(fullMtgSetList);
        modernFormat.buildModernBannedList(fullCardList);
        return modernFormat.buildModernFormat();
    }

    public Format buildLegacyFormat(){
        LegacyFormat legacyFormat = new LegacyFormat(formatBuilder);
        legacyFormat.buildLegacyLegalSetsList(fullMtgSetList);
        legacyFormat.buildLegacyBannedList(fullCardList);
        return legacyFormat.buildLegacyFormat();
    }

    public Format buildVintageFormat() {
        VintageFormat vintageFormat = new VintageFormat(formatBuilder);
        vintageFormat.buildVintageLegalSetsList(fullMtgSetList);
        vintageFormat.buildVintageBannedList(fullCardList);
        vintageFormat.buildVintageRestrictedList(fullCardList);
        return vintageFormat.buildVintageFormat();
    }

    public Format buildEDHFormat(){
        EDHFormat edhFormat = new EDHFormat(formatBuilder);
        edhFormat.buildEDHLegalSetList(fullMtgSetList);
        edhFormat.buildEDHBannedList(fullCardList);
        return edhFormat.buildEDHFormat();
    }

    public List<Card> filterByColor(EnumSet<CardColor> colors){
        filter.filterByCardColor(filter.getCardsWithTerm(), colors);
        return filter.getFilteredCardList();
    }

    public List<Card> search(String term){
        filter.findTerm(fullCardList, term);
        return filter.getCardsWithTerm();
    }

    public List<Card> getFullCardList(){
        return fullCardList;
    }

    public ObservableList<Card> retrieveLegalCardsForFormat(Format format) {
        formatCardList.clear();
        filter.filterByFormat(fullCardList, format);
        formatCardList.addAll(filter.getCardFormatList());
        Collections.sort(formatCardList);
        return formatCardList;
    }
}
