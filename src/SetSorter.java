
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SetSorter {

    @SuppressWarnings("UnusedReturnValue")
    public List<MtgSet> sort(List<MtgSet> list){
        Collections.sort(list);
        return list;
    }
}
