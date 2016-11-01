import com.google.common.collect.ImmutableList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class CardSetHandler extends DefaultHandler {

    private Set set = new Set();
    private String temp;
    private ImmutableList.Builder builder = ImmutableList.builder();

    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("set")) {
            set = new Set();
        }
    }

    public void endElement(String uri, String localName, String qName){
        if(qName.equalsIgnoreCase("set")){
            builder.add(set);
        }
        if(qName.equalsIgnoreCase("name")){
            set.setSetName(temp);
        }
        else if(qName.equalsIgnoreCase("longname")){
            set.setLongname(temp);
        }
        else if(qName.equalsIgnoreCase("setType")){
            set.setSetType(temp);
        }
        else if(qName.equalsIgnoreCase("releaseDate")){
            temp = temp.replaceAll("-", "");
            set.setReleaseDate(temp);
        }
    }

    public ImmutableList returnAllSetsList(){
        return builder.build();
    }
}
