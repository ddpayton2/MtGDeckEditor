import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Card implements Comparable<Card>{

    private String cardName;
    private String cardCost;
    private String cardType;
    private String cardText;
    private String pt;
    private final List<String> setsPrintedIn = Lists.newArrayList();
    private final EnumSet<CardColor> cardColors = EnumSet.of(CardColor.COLORLESS);

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void addCardColor(CardColor color){
        cardColors.add(color);
        cardColors.remove(CardColor.COLORLESS);
    }

    public boolean containsAllColors(Collection<CardColor> colors){
        return cardColors.containsAll(colors);
    }

    public boolean containsCardColor(CardColor color){
        return cardColors.contains(color);
    }

    public String getCardCost(){
        return cardCost;
    }

    public void setCardCost(String cost){
        this.cardCost = cost;
    }

    public String getCardType(){
        return cardType;
    }

    public void setCardType(String type){
        this.cardType = type;
    }

    public String getCardText(){
        return cardText;
    }

    public void setCardText(String text){
        this.cardText = text;
    }

    public void setPT(String pt){
        this.pt = pt;
    }

    public String getPt(){
        if(this.pt != null){
            return this.pt;
        }
        return "";
    }

    public void addMtgSet(String mtgSet){
        setsPrintedIn.add(mtgSet);
    }

    public List<String> getSetsPrintedIn(){
        return this.setsPrintedIn;
    }

    public String getAllCardInfo(){
        return this.cardName + "\t" + this.getCardCost() + "\n" + "\n"
                + this.getCardType() + "\n" + this.getCardText() + "\n" + this.getPt()
                +"\n" + this.setsPrintedIn;
    }

    @Override
    public int compareTo(@NotNull Card o) {
        return (this.getCardName().compareTo(o.getCardName()));
    }
}
