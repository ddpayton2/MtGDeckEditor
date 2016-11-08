import com.google.common.collect.Lists;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SuppressWarnings("WeakerAccess")
class CardSetHandler extends DefaultHandler {

    private Set set = new Set();
    private final StringBuilder builder = new StringBuilder();
    private String temp;
    private List<Set> allSetsList = Lists.newArrayList();

    public class DoneParsingException extends SAXException{}

    public void characters(char[] buffer, int start, int length) {
        builder.append(buffer, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("set")) {
            set = new Set();
        }
    }

    public void endElement(String uri, String localName, String qName) throws DoneParsingException {
        temp = builder.toString().trim();
        if(qName.equalsIgnoreCase("set")){
            allSetsList.add(set);
        }
        if(qName.equalsIgnoreCase("name")){
            set.setSetName(temp);
        }
        else if(qName.equalsIgnoreCase("longname")){
            set.setLongname(temp.replaceAll("\n", "").replaceAll("&quot;", "'").replaceAll("&#39;", "'"));
        }
        else if(qName.equalsIgnoreCase("setType")){
            set.setSetType(temp);
        }
        else if(qName.equalsIgnoreCase("releaseDate")){
            if(!temp.equalsIgnoreCase("")){
                LocalDate date = LocalDate.parse(temp, DateTimeFormatter.ISO_LOCAL_DATE);
                set.setReleaseDate(date);
            }
            else{
                LocalDate date = LocalDate.of(0000,01,31);
                set.setReleaseDate(date);
            }
        }
        else if(qName.equalsIgnoreCase("sets")){
            throw new DoneParsingException();
        }
        builder.setLength(0);
    }

    public List<Set> returnAllSetsList(){
        return this.allSetsList;
    }
}
