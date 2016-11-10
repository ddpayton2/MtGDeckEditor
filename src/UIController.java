import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class UIController {

    private List<Card> fullCardList;
    private List<MtgSet> fullMtgSetList;
    private Format.Builder formatBuilder = new Format.Builder();
    private List<Format> formatList = Lists.newArrayList();
    private CardColorFilter filter = new CardColorFilter();
    private List<Card> filteredCardList = Lists.newArrayList();

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
        parser.parse("cards.xml",mtgSetHandler);
        fullMtgSetList = mtgSetHandler.returnAllSetsList();
    }

    private Format buildStandardFormat(){
        StandardFormat standardFormat = new StandardFormat(formatBuilder);
        standardFormat.buildStandardLegalSetsList(fullMtgSetList);
        Format standard = standardFormat.buildStandardFormat();
        return standard;
    }

    private Format buildModernFormat(){
        ModernFormat modernFormat = new ModernFormat(formatBuilder);
        modernFormat.buildModernLegalSetsList(fullMtgSetList);
        modernFormat.buildModernBannedList(fullCardList);
        Format modern = modernFormat.buildModernFormat();
        return modern;
    }

    private Format buildLegacyFormat(){
        LegacyFormat legacyFormat = new LegacyFormat(formatBuilder);
        legacyFormat.buildLegacyLegalSetsList(fullMtgSetList);
        legacyFormat.buildLegacyBannedList(fullCardList);
        Format legacy = legacyFormat.buildLegacyFormat();
        return legacy;
    }

    private Format buildVintageFormat() {
        VintageFormat vintageFormat = new VintageFormat(formatBuilder);
        vintageFormat.buildVintageLegalSetsList(fullMtgSetList);
        vintageFormat.buildVintageBannedList(fullCardList);
        vintageFormat.buildVintageRestrictedList(fullCardList);
        Format vintage = vintageFormat.buildVintageFormat();
        return vintage;
    }

    private Format buildEDHFormat(){
        EDHFormat edhFormat = new EDHFormat(formatBuilder);
        edhFormat.buildEDHLegalSetList(fullMtgSetList);
        edhFormat.buildEDHBannedList(fullCardList);
        Format edh = edhFormat.buildEDHFormat();
        return edh;
    }

    public void buildAllFormatList(){
        formatList.add(buildStandardFormat());
        formatList.add(buildModernFormat());
        formatList.add(buildLegacyFormat());
        formatList.add(buildVintageFormat());
        formatList.add(buildEDHFormat());
    }

    public void filterByColor(EnumSet<CardColor> colors){
        filter.filterByCardColor(fullCardList, colors);
    }

    public List<Format> getAllFormatList(){
        return this.formatList;
    }

    public List<Card> getFullCardList(){
        return fullCardList;
    }
}
