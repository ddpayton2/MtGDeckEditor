import com.google.common.collect.Lists;
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
    private final Format.Builder standardFormatBuilder = new Format.Builder();
    private final Format.Builder modernFormatBuilder = new Format.Builder();
    private final Format.Builder legacyFormatBuilder = new Format.Builder();
    private final Format.Builder vintageFormatBuilder = new Format.Builder();
    private final Format.Builder edhFormatBuilder = new Format.Builder();
    private Format standard;
    private Format modern;
    private Format legacy;
    private Format vintage;
    private Format edh;
    private final CardFilter filter = new CardFilter();
    private final ObservableList<Card> formatCardList = FXCollections.observableArrayList();

    public void setUpAll(){
        try {
            setUpListOfAllCards();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        try {
            setUpListOfAllSets();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        buildAllFormats();
    }

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
        Collections.sort(fullMtgSetList);
    }

    public Format buildStandardFormat(){
        StandardFormat standardFormat = new StandardFormat(standardFormatBuilder);
        standardFormat.buildStandardLegalSetsList(fullMtgSetList);
        standard = standardFormat.buildStandardFormat();
        return standard;
    }

    public Format buildModernFormat(){
        ModernFormat modernFormat = new ModernFormat(modernFormatBuilder);
        modernFormat.buildModernLegalSetsList(fullMtgSetList);
        modernFormat.buildModernBannedList(fullCardList);
        modern = modernFormat.buildModernFormat();
        return modern;
    }

    public Format buildLegacyFormat(){
        LegacyFormat legacyFormat = new LegacyFormat(legacyFormatBuilder);
        legacyFormat.buildLegacyLegalSetsList(fullMtgSetList);
        legacyFormat.buildLegacyBannedList(fullCardList);
        legacy = legacyFormat.buildLegacyFormat();
        return legacy;
    }

    public Format buildVintageFormat() {
        VintageFormat vintageFormat = new VintageFormat(vintageFormatBuilder);
        vintageFormat.buildVintageLegalSetsList(fullMtgSetList);
        vintageFormat.buildVintageBannedList(fullCardList);
        vintageFormat.buildVintageRestrictedList(fullCardList);
        vintage = vintageFormat.buildVintageFormat();
        return vintage;
    }

    public Format buildEDHFormat(){
        EDHFormat edhFormat = new EDHFormat(edhFormatBuilder);
        edhFormat.buildEDHLegalSetList(fullMtgSetList);
        edhFormat.buildEDHBannedList(fullCardList);
        edh = edhFormat.buildEDHFormat();
        return edh;
    }

    public void buildAllFormats(){
        standard = buildStandardFormat();
        modern = buildModernFormat();
        legacy = buildLegacyFormat();
        vintage = buildVintageFormat();
        edh = buildEDHFormat();
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
        return formatCardList;
    }

    public List<Card> searchForTermInFormat(String formatName, String term) {

        List<Format> allFormats = Lists.newArrayList(standard, modern, legacy, vintage, edh);
        for(Format format : allFormats){
            if(format.getFormatName().equalsIgnoreCase(formatName)){
                filter.findTerm(retrieveLegalCardsForFormat(format), term);
            }
        }
        return filter.getCardsWithTerm();
    }

    public ObservableList<Card> getFormatCardList(){
        return formatCardList;
    }
}
