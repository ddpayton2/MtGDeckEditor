
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SetSorter {

    private CardSetHandler handler = new CardSetHandler(); //this is an issue because each time we make a new CSH, it creates a new, empty List
    private List<Set> list = handler.returnAllSetsList(); //this list will be empty every time

    public void sort(){
        Collections.sort(list, (o1, o2) -> o1.getReleaseDate().compareTo(o2.getReleaseDate()));
    }

    public List<Set> getList(){
        return this.list;
    }
}
