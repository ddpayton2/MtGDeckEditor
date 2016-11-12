
import com.google.common.collect.Lists;

import java.util.List;

public class Format {

    public static final class Builder{
        private String formatName;
        private List<MtgSet> legalMtgSets;
        private List<Card> bannedList;
        private List<Card> restrictedList;

        public Builder setFormatName(String name){
            this.formatName = name;
            return this;
        }

        public Builder setLegalSets(List<MtgSet> list){
            this.legalMtgSets = list;
            return this;
        }

        public Builder setBannedList(List<Card> banned){
            this.bannedList = banned;
            return this;
        }

        public Builder setRestrictedList(List<Card> restricted){
            this.restrictedList = restricted;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Format build(){
            return new Format(this);
        }
    }

    private final String formatName;
    private final List<MtgSet> legalMtgSets;
    private final List<String> legalMtgSetsNames = Lists.newArrayList();
    private final List<Card> bannedList;
    private final List<Card> restrictedList;

    public Format(Builder builder){
        this.formatName = builder.formatName;
        this.legalMtgSets = builder.legalMtgSets;
        this.bannedList = builder.bannedList;
        this.restrictedList = builder.restrictedList;
        for(MtgSet set : legalMtgSets){
            legalMtgSetsNames.add(set.getMtgSetName());
        }
    }

    public List<MtgSet> getLegalSets(){
        return this.legalMtgSets;
    }

    public List<Card> getBannedList(){
        return this.bannedList;
    }

    public List<Card> getRestrictedList(){
        return this.restrictedList;
    }

    public String getFormatName(){
        return this.formatName;
    }

    public List<String> getLegalMtgSetsNames(){
        return this.legalMtgSetsNames;
    }
}
