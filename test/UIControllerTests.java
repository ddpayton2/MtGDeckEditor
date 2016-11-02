import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class UIControllerTests {

    private final UIController controller = new UIController();

    @Before
    public void setUpTests() throws ParserConfigurationException, SAXException, IOException {
        controller.setUpArray();
    }

    @Test
    public void testForSearchTerm_fullCardName() throws ParserConfigurationException, SAXException, IOException {
        String term = "Champion of the Parish";
        controller.searchForTerm(term);
        Assert.assertEquals(term + "\n", controller.showOutputOfFilteredList());
    }

    @Test
    public void testForSearchTerm_partialWord() throws ParserConfigurationException, SAXException, IOException {
        String term = "huntmaster";
        controller.searchForTerm(term);
        Assert.assertEquals("Huntmaster of the Fells\nLys Alana Huntmaster\n" , controller.showOutputOfFilteredList());
    }

    @Test
    public void testForSearchTerm_termInType(){
        String term = "goyf";
        controller.searchForTerm(term);
        Assert.assertEquals("Mortivore\nCognivore\nTarmogoyf\nMagnivore\nDetritivore\nTerravore\nLhurgoyf\nCantivore\n", controller.showOutputOfFilteredList());
    }

    @Test
    public void testForSearchTerm_termInCardText(){
        String term = "affinity for islands";
        controller.searchForTerm(term);
        Assert.assertEquals("Spire Golem\n", controller.showOutputOfFilteredList());
    }

    @Test
    public void testForColorFilter_red(){
        String term = "huntmaster";
        controller.searchForTerm(term);
        controller.filterByColor("R");
        Assert.assertEquals("Huntmaster of the Fells\n", controller.showColorFilteredList());
    }

    @Test
    public void testForColorFilter_green(){
        String term = "goyf";
        controller.searchForTerm(term);
        controller.filterByColor("G");
        Assert.assertEquals("Tarmogoyf\nTerravore\nLhurgoyf\n", controller.showColorFilteredList());
    }

    @Test
    public void testForColorFilter_blue(){
        String term = "goyf";
        controller.searchForTerm(term);
        controller.filterByColor("U");
        Assert.assertEquals("Cognivore\n", controller.showColorFilteredList());
    }

    @Test
    public void testForColorFilter_black(){
        String term = "goyf";
        controller.searchForTerm(term);
        controller.filterByColor("B");
        Assert.assertEquals("Mortivore\n", controller.showColorFilteredList());
    }

    @Test
    public void testForColorFilter_white(){
        String term = "goyf";
        controller.searchForTerm(term);
        controller.filterByColor("W");
        Assert.assertEquals("Cantivore\n", controller.showColorFilteredList());
    }

    @Test
    public void testForColorFilter_wasteMana(){
        String term = "instant";
        controller.searchForTerm(term);
        controller.filterByColor("C");
        Assert.assertEquals("Spatial Contortion\nWarping Wail\n", controller.showColorFilteredList());
    }

    @Test
    public void testForColorFilter_colorless(){
        String term = "fox";
        controller.searchForTerm(term);
        controller.filterForColorless();
        Assert.assertEquals("Filigree Familiar\n", controller.showColorFilteredList());
    }
}
