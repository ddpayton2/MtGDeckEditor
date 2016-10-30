
public class Set {

    public String setName;
    public String longname;
    public String setType;
    public String releaseDate;

    public String getLongname(){
        return this.longname;
    }

    public void setLongname(String setLongname){
        this.longname = setLongname;
    }

    public String getSetName(){
        return this.setName;
    }

    public void setSetName(String name){
        this.setName = name;
    }

    public String getSetType() {
        return this.setType;
    }

    public void setSetType(String setType){
        this.setType = setType;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String date){
        this.releaseDate = date;
    }
}
