import com.google.common.collect.Lists;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class UIController {

    private List<Card> fullCardList;
    private List<MtgSet> fullMtgSetList;
    private Format.Builder formatBuilder = new Format.Builder();
    private List<Format> formatList = Lists.newArrayList();
    private CardFilter filter = new CardFilter();

    public void setUpListOfAllCards() throws IOException, SAXException {
        SAXParserFactory spfac = SAXParserFactory.newInstance();
        SAXParser sp = null;
        try {
            sp = spfac.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        CardHandler handler = new CardHandler();
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
            parser.parse("cards.xml",mtgSetHandler);
        }
        catch (CardMtgSetHandler.DoneParsingException e){
        }
        fullMtgSetList = mtgSetHandler.returnAllSetsList();
    }

    private Format buildStandardFormat(){
        StandardFormat standardFormat = new StandardFormat(formatBuilder);
        standardFormat.buildStandardLegalSetsList(fullMtgSetList);
        return standardFormat.buildStandardFormat();
    }

    private Format buildModernFormat(){
        ModernFormat modernFormat = new ModernFormat(formatBuilder);
        modernFormat.buildModernLegalSetsList(fullMtgSetList);
        modernFormat.buildModernBannedList(fullCardList);
        return modernFormat.buildModernFormat();
    }

    private Format buildLegacyFormat(){
        LegacyFormat legacyFormat = new LegacyFormat(formatBuilder);
        legacyFormat.buildLegacyLegalSetsList(fullMtgSetList);
        legacyFormat.buildLegacyBannedList(fullCardList);
        return legacyFormat.buildLegacyFormat();
    }

    private Format buildVintageFormat() {
        VintageFormat vintageFormat = new VintageFormat(formatBuilder);
        vintageFormat.buildVintageLegalSetsList(fullMtgSetList);
        vintageFormat.buildVintageBannedList(fullCardList);
        vintageFormat.buildVintageRestrictedList(fullCardList);
        return vintageFormat.buildVintageFormat();
    }

    private Format buildEDHFormat(){
        EDHFormat edhFormat = new EDHFormat(formatBuilder);
        edhFormat.buildEDHLegalSetList(fullMtgSetList);
        edhFormat.buildEDHBannedList(fullCardList);
        return edhFormat.buildEDHFormat();
    }

    public List<Format> buildAllFormatList(){
        formatList.add(buildStandardFormat());
        formatList.add(buildModernFormat());
        formatList.add(buildLegacyFormat());
        formatList.add(buildVintageFormat());
        formatList.add(buildEDHFormat());
        return this.formatList;
    }

    public List<Card> filterByColor(EnumSet<CardColor> colors){
        filter.filterByCardColor(filter.getCardsWithTerm(), colors);
        return filter.getFilteredCardList();
    }

    public List<Card> search(String term){
        filter.findTerm(filter.getCardFormatList(), term);
        return filter.getCardsWithTerm();
    }

    public List<Card> getFullCardList(){
        return fullCardList;
    }

    public void retrieveLegalCardsForFormat(Format format) {
        filter.filterByFormat(fullCardList, format);
    }
}
