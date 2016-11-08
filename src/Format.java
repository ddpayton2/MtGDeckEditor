import java.util.ArrayList;
import java.util.List;

public class Format {

    private String formatName;
    private List<Set> legalSets;
    private ArrayList<Card> bannedList;
    private ArrayList<Card> restrictedList;

    public Format(String name, List<Set> list, ArrayList<Card> banned, ArrayList<Card> restricted){
        this.formatName = name;
        this.legalSets = list;
        this.bannedList = banned;
        this.restrictedList = restricted;
    }

    public String getFormatName(){
        return this.formatName;
    }

    public List<Set> getLegalSets(){
        return this.legalSets;
    }

    public ArrayList<Card> getBannedList(){
        return this.bannedList;
    }

    public ArrayList<Card> getRestrictedList(){
        return this.restrictedList;
    }
}
