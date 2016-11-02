
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SetSorter {

    private final CardSetHandler handler = new CardSetHandler();
    private final List<Set> list = handler.returnAllSetsList();

    public void sort(){
        Collections.sort(list, (o1, o2) -> o1.getReleaseDate().compareTo(o2.getReleaseDate()));
    }

    public List<Set> getList(){
        return list;
    }
}
