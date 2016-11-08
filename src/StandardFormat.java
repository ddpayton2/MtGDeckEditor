import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

@SuppressWarnings("WeakerAccess")
public class StandardFormat extends Format{

    private final List<Set> standardLegalSets = Lists.newArrayList();
    private final List<Card> standardBannedList = Lists.newArrayList();
    private final List<Card> standardRestrictedList = Lists.newArrayList();
    private Format.Builder builder = new Format.Builder();

    public StandardFormat(Builder builder) {
        super(builder);
    }

    public void buildStandardLegalSetsList(List<Set> allSets){
        Stack<Set> stack = new Stack<>();
        allSets.stream().filter(set -> set.getSetType().equalsIgnoreCase("Expansion") || set.getSetType().equalsIgnoreCase("Core")).forEach(stack::push);
        for(int i = 0; i < 5; i++){
            standardLegalSets.add(stack.pop());
        }
    }

    public List<Set> getStandardLegalSets(){
        return this.standardLegalSets;
    }

    public Format buildStandardFormat(){
        Format.Builder builder = new Format.Builder();
        builder.setFormatName()
                .setLegalSets(standardLegalSets)
                .setBannedList(standardBannedList)
                .setRestrictedList(standardRestrictedList)
                .setMaxNumberOfSideboardCards()
                .setMinNumberOfMainCards()
                .build();
        return new Format(builder);
    }
}
