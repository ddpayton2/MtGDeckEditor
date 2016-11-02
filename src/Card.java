
@SuppressWarnings("unused")
public class Card {

    private String cardName;
    @SuppressWarnings("WeakerAccess")
    public String cardColor;
    private String cardCost;
    private String cardType;
    private String cardText;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardColor() {
        return cardColor;
    }

    public void setCardColor(String color){
            this.cardColor = color;
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
