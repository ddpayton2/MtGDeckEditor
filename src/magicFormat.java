import java.util.ArrayList;

public class magicFormat {

    private ArrayList<Set> setList = new ArrayList<>();
    private Set set = new Set();

    public String getSetName(){
        return set.getSetName();
    }

    public ArrayList<Set> getSetList(){
        return this.setList;
    }
}
