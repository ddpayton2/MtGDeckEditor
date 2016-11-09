import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

@SuppressWarnings("WeakerAccess")
public class StandardFormat extends Format{

    private final List<MtgSet> standardLegalMtgSets = Lists.newArrayList();
    private final List<Card> standardBannedList = Lists.newArrayList();
    private final List<Card> standardRestrictedList = Lists.newArrayList();
    private Format.Builder builder = new Format.Builder();
    private int currentNumberOfStandardLegalMTGSets = 5;

    public StandardFormat(Builder builder) {
        super(builder);
    }

    public void buildStandardLegalSetsList(List<MtgSet> allMtgSets){ // This method gets the 5 most current Expansion/Core MTG Sets and stores them in a list
        Stack<MtgSet> stack = new Stack<>();
        allMtgSets.stream().filter(set -> set.getMtgSetType().equalsIgnoreCase("Expansion") || set.getMtgSetType().equalsIgnoreCase("Core")).forEach(stack::push);
        for(int i = 0; i < currentNumberOfStandardLegalMTGSets; i++){
            standardLegalMtgSets.add(stack.pop());
        }
    }

    public Format buildStandardFormat(){
        builder.setFormatName("Standard")
                .setLegalSets(standardLegalMtgSets)
                .setBannedList(standardBannedList)
                .setRestrictedList(standardRestrictedList)
                .build();
        return new Format(builder);
    }
}
