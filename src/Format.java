
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class Format {

    @SuppressWarnings("WeakerAccess")
    public static final class Builder{
        private String formatName;
        private List<MtgSet> legalMtgSets;
        private List<Card> bannedList;
        private List<Card> restrictedList;

        @SuppressWarnings("WeakerAccess")
        public Builder setFormatName(String name){
            this.formatName = name;
            return this;
        }

        @SuppressWarnings("WeakerAccess")
        public Builder setLegalSets(List<MtgSet> list){
            this.legalMtgSets = list;
            return this;
        }

        @SuppressWarnings("WeakerAccess")
        public Builder setBannedList(List<Card> banned){
            this.bannedList = banned;
            return this;
        }

        @SuppressWarnings("WeakerAccess")
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
    }

    @SuppressWarnings("WeakerAccess")
    public List<MtgSet> getLegalSets(){
        return this.legalMtgSets;
    }

    @SuppressWarnings("WeakerAccess")
    public List<Card> getBannedList(){
        return this.bannedList;
    }

    @SuppressWarnings("WeakerAccess")
    public List<Card> getRestrictedList(){
        return this.restrictedList;
    }

    @SuppressWarnings("WeakerAccess")
    public String getFormatName(){
        return this.formatName;
    }

    @SuppressWarnings("WeakerAccess")
    public List<String> getLegalMtgSetsNames(){
        legalMtgSetsNames.addAll(legalMtgSets.stream().map(MtgSet::getMtgSetName).collect(Collectors.toList()));
        return this.legalMtgSetsNames;
    }
}
