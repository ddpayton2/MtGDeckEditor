import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.Stack;

public class FormatBuilder {

    private List<Set> standardLegalSets = Lists.newArrayList();
    private List<Set> modernLegalSets = Lists.newArrayList();

    public void buildStandardLegalSetsList(List<Set> allSets){
        Stack<Set> stack = new Stack<>();
        for(Set set : allSets){
            if(set.getSetType().equalsIgnoreCase("Expansion") || set.getSetType().equalsIgnoreCase("Core")){
                stack.push(set);
            }
        }
        for(int i = 0; i < 5; i++){
            standardLegalSets.add(stack.pop());
        }
    }

    public List<Set> getStandardLegalSetList(){
        standardLegalSets = Lists.reverse(standardLegalSets);
        return this.standardLegalSets;
    }

    public void buildModernLegalSetsList(List<Set> list){
        LocalDate eighthEdReleaseDate = LocalDate.of(2003, 07, 28);
        for(Set set : list){
            if(set.getReleaseDate().isEqual(eighthEdReleaseDate) || (set.getReleaseDate().isAfter(eighthEdReleaseDate)
                    && set.getSetType().equalsIgnoreCase("expansion")) || set.getReleaseDate().isAfter(eighthEdReleaseDate) && set.getSetType().equalsIgnoreCase("core")){
                modernLegalSets.add(set);
            }
        }
    }

    public List<Set> getModernLegalSets(){
        return this.modernLegalSets;
    }
}
