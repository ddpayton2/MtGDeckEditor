
import java.util.Collections;

public class setSorter {

    private setHandler handler = new setHandler();

    public void sortSetList(){
        Collections.sort(handler.allSetsList, (o1, o2) -> o1.getReleaseDate().compareToIgnoreCase(o2.getReleaseDate()));
    }
}
