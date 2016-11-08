
import java.util.List;

public class Format {

    public static final class Builder{
        private String formatName;
        private List<Set> legalSets;
        private List<Card> bannedList;
        private List<Card> restrictedList;
        private int minNumberOfMainCards;
        private int maxNumberOfSideboardCards;

        public Builder setFormatName(){
            this.formatName = "Standard";
            return this;
        }

        public Builder setLegalSets(List<Set> list){
            this.legalSets = list;
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

        public Builder setMinNumberOfMainCards(){
            this.minNumberOfMainCards = 60;
            return this;
        }

        public Builder setMaxNumberOfSideboardCards(){
            this.maxNumberOfSideboardCards = 15;
            return this;
        }

        public Format build(){
            return new Format(this);
        }
    }

    private final String formatName;
    private final List<Set> legalSets;
    private final List<Card> bannedList;
    private final List<Card> restrictedList;

    public Format(Builder builder){
        this.formatName = builder.formatName;
        this.legalSets = builder.legalSets;
        this.bannedList = builder.bannedList;
        this.restrictedList = builder.restrictedList;
        int minNumberOfMainCards = builder.minNumberOfMainCards;
        int maxNumberOfSideboardCards = builder.maxNumberOfSideboardCards;
    }

    public List<Set> getLegalSets(){
        return this.legalSets;
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
}
