import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class XmlFileReaderTests {

    @Test
    public void testXmlFileReader_cardName() throws ParserConfigurationException, SAXException, IOException {
        UIController controller = new UIController();
        controller.setUpArray();
        ArrayList<Card> cardList = new ArrayList<>();

    }
}
