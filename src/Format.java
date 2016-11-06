import java.util.List;

public class Format {

    private String name;
    private List<Set> legalSets;
    private List<Card> bannedList;
    private List<Card> restrictedList;

    public Format (String formatName, List<Set> legalSets, List<Card> bannedList, List<Card> restrictedList){
        this.name = formatName;
        this.legalSets = legalSets;
        this.bannedList = bannedList;
        this.restrictedList = restrictedList;
    }

    public String getName(){
        return this.name;
    }

    public List<Set> getLegalSets(){
        return this.legalSets;
    }
}
