
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class SetSorter {

    public List<Set> sort(List<Set> list){
        Collections.sort(list);
        for(Set set : list){
            System.out.println(set.getLongname());
        }
        return list;
    }
}
