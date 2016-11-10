import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

@SuppressWarnings("WeakerAccess")
public class StandardFormat extends Format{

    private final List<MtgSet> standardLegalMtgSets = Lists.newArrayList();
    private final List<Card> standardBannedList = Lists.newArrayList();
    private final List<Card> standardRestrictedList = Lists.newArrayList();
    private final Format.Builder builder = new Format.Builder();

    public StandardFormat(Builder builder) {
        super(builder);
    }

    /**
     * Adds the latest five expansion and/or core sets to this StandardFormat
     * @param allMtgSets a list of all Magic: the Gathering sets
     */
    public void buildStandardLegalSetsList(List<MtgSet> allMtgSets){
        Stack<MtgSet> stack = new Stack<>();
        allMtgSets.stream().filter(set -> set.getMtgSetType().equalsIgnoreCase("Expansion") || set.getMtgSetType().equalsIgnoreCase("Core")).forEach(stack::push);
        int currentNumberOfStandardLegalMTGSets = 5;
        for(int i = 0; i < currentNumberOfStandardLegalMTGSets; i++){
            standardLegalMtgSets.add(stack.pop());
        }
    }

    /**
     * @return a Format that follows the standard format conventions set by Wizards of the Coast
     * @see {{@link #StandardFormat(Builder)}}
     */
    public Format buildStandardFormat(){
        builder.setFormatName("Standard")
                .setLegalSets(standardLegalMtgSets)
                .setBannedList(standardBannedList)
                .setRestrictedList(standardRestrictedList)
                .build();
        return new Format(builder);
    }
}
