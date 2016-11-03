
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@SuppressWarnings("WeakerAccess")
public class Set implements Comparable<Set>{

    public String setName;
    public String longname;
    public String setType;
    public LocalDate releaseDate;

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

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate date){
        this.releaseDate = date;
    }

    @Override
    public int compareTo(@NotNull Set o) {
        return (this.getReleaseDate().compareTo(o.getReleaseDate()));
    }
}
