
import java.util.Collections;

public class SetSorter {

    private CardSetHandler handler = new CardSetHandler();

    public void sortSetList(){
        Collections.sort(handler.allSetsList, (o1, o2) -> o1.getReleaseDate().compareToIgnoreCase(o2.getReleaseDate()));
    }
}
