import java.util.Collection;
import java.util.EnumSet;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Card {

    private String cardName;
    private String cardCost;
    private String cardType;
    private String cardText;
    private EnumSet<CardColor> cardColors = EnumSet.of(CardColor.COLORLESS);

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
}
