
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@SuppressWarnings("WeakerAccess")
public class MtgSet implements Comparable<MtgSet>{

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

    public String getMtgSetName(){
        return this.setName;
    }

    public void setMtgSetName(String name){
        this.setName = name;
    }

    public String getMtgSetType() {
        return this.setType;
    }

    public void setMtgSetType(String setType){
        this.setType = setType;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(LocalDate date){
        this.releaseDate = date;
    }

    @Override
    public int compareTo(@NotNull MtgSet o) {
        return (this.getReleaseDate().compareTo(o.getReleaseDate()));
    }
}
