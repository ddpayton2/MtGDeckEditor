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
    private List<Card> standardCardList = Lists.newArrayList();
    private List<Card> modernCardList = Lists.newArrayList();
    private List<Card> legacyCardList = Lists.newArrayList();
    private List<Card> vintageCardList = Lists.newArrayList();
    private List<Card> edhCardList = Lists.newArrayList();
    private ObservableList<Card> formatCardList = FXCollections.observableArrayList();
    private final CardFilter filter = new CardFilter();

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

    public void buildAllFormats(){
        standard = buildStandardFormat();
        modern = buildModernFormat();
        legacy = buildLegacyFormat();
        vintage = buildVintageFormat();
        edh = buildEDHFormat();
    }

    public Format buildStandardFormat(){
        StandardFormat standardFormat = new StandardFormat(standardFormatBuilder);
        standardFormat.buildStandardLegalSetsList(fullMtgSetList);
        standard = standardFormat.buildStandardFormat();
        standardCardList.addAll(filter.filterByFormat(fullCardList, standard));
        return standard;
    }

    public Format buildModernFormat(){
        ModernFormat modernFormat = new ModernFormat(modernFormatBuilder);
        modernFormat.buildModernLegalSetsList(fullMtgSetList);
        modernFormat.buildModernBannedList(fullCardList);
        modern = modernFormat.buildModernFormat();
        modernCardList.addAll(filter.filterByFormat(fullCardList, modern));
        return modern;
    }

    public Format buildLegacyFormat(){
        LegacyFormat legacyFormat = new LegacyFormat(legacyFormatBuilder);
        legacyFormat.buildLegacyLegalSetsList(fullMtgSetList);
        legacyFormat.buildLegacyBannedList(fullCardList);
        legacy = legacyFormat.buildLegacyFormat();
        legacyCardList.addAll(filter.filterByFormat(fullCardList, legacy));
        return legacy;
    }

    public Format buildVintageFormat() {
        VintageFormat vintageFormat = new VintageFormat(vintageFormatBuilder);
        vintageFormat.buildVintageLegalSetsList(fullMtgSetList);
        vintageFormat.buildVintageBannedList(fullCardList);
        vintageFormat.buildVintageRestrictedList(fullCardList);
        vintage = vintageFormat.buildVintageFormat();
        vintageCardList.addAll(filter.filterByFormat(fullCardList, vintage));
        return vintage;
    }

    public Format buildEDHFormat(){
        EDHFormat edhFormat = new EDHFormat(edhFormatBuilder);
        edhFormat.buildEDHLegalSetList(fullMtgSetList);
        edhFormat.buildEDHBannedList(fullCardList);
        edh = edhFormat.buildEDHFormat();
        edhCardList.addAll(filter.filterByFormat(fullCardList, edh));
        return edh;
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

    public List<Card> retrieveLegalCardsForFormat(Format format) {

        formatCardList.clear();
        if(format.getFormatName().equalsIgnoreCase("Standard")){
            formatCardList.addAll(standardCardList);
        }
        else if(format.getFormatName().equalsIgnoreCase("Modern")){
            formatCardList.addAll(modernCardList);
        }
        else if(format.getFormatName().equalsIgnoreCase("Legacy")){
            formatCardList.addAll(legacyCardList);
        }
        else if(format.getFormatName().equalsIgnoreCase("Vintage")){
            formatCardList.addAll(vintageCardList);
        }
        else if(format.getFormatName().equalsIgnoreCase("Commander (EDH)")){
            formatCardList.addAll(edhCardList);
        }
        return formatCardList;
    }

    public List<Card> searchForTermInFormat(String formatName, String term) {

        List<Format> allFormats = Lists.newArrayList(standard, modern, legacy, vintage, edh);
        for(Format format : allFormats){
            if(format.getFormatName().equalsIgnoreCase(formatName)){
                filter.findTerm(getFormatCardList(), term);
            }
        }
        return filter.getCardsWithTerm();
    }

    public ObservableList<Card> getFormatCardList(){
        return formatCardList;
    }

    public Format getStandard(){
        return standard;
    }

    public Format getModern(){
        return modern;
    }

    public Format getLegacy(){
        return legacy;
    }

    public Format getVintage(){
        return vintage;
    }

    public Format getEdh(){
        return edh;
    }
}
