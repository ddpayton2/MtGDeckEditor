
import com.google.common.collect.ImmutableList;

import java.util.Collections;

public class SetSorter {

    private CardSetHandler handler = new CardSetHandler();
    private ImmutableList<Set> allSetsList = handler.returnAllSetsList();

    public void sortSetList(){
        Collections.sort(allSetsList, (o1, o2) -> o1.getReleaseDate().compareToIgnoreCase(o2.getReleaseDate()));
    }
}
