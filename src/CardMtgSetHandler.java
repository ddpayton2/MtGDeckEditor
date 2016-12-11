import com.google.common.collect.Lists;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
class CardMtgSetHandler extends DefaultHandler {

    private MtgSet mtgSet = new MtgSet();
    private final StringBuilder builder = new StringBuilder();
    private String temp;
    private final List<MtgSet> allSetsList = Lists.newArrayList();

    public class DoneParsingException extends SAXException{}

    @Override
    public void characters(char[] buffer, int start, int length) {
        builder.append(buffer, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("set")) {
            mtgSet = new MtgSet();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws DoneParsingException {
        temp = builder.toString().trim();
        if(qName.equalsIgnoreCase("set")){
            allSetsList.add(mtgSet);
        }
        if(qName.equalsIgnoreCase("name")){
            mtgSet.setMtgSetName(temp);
        }
        else if(qName.equalsIgnoreCase("longname")){
            mtgSet.setLongname(temp.replaceAll("\n", "").replaceAll("&quot;", "'").replaceAll("&#39;", "'"));
        }
        else if(qName.equalsIgnoreCase("setType")){
            mtgSet.setMtgSetType(temp);
        }
        else if(qName.equalsIgnoreCase("releaseDate")){
            if(!temp.equalsIgnoreCase("")){
                LocalDate date = LocalDate.parse(temp, DateTimeFormatter.ISO_LOCAL_DATE);
                mtgSet.setReleaseDate(date);
            }
            else{
                LocalDate date = LocalDate.of(0, 1,31);
                mtgSet.setReleaseDate(date);
            }
        }
        else if(qName.equalsIgnoreCase("sets")){
            throw new DoneParsingException();
        }
        builder.setLength(0);
    }

    public List<MtgSet> returnAllSetsList(){
        return this.allSetsList;
    }
}
