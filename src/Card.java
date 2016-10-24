
public class Card {

    private String cardName;
    private String cardSet;
    private String cardColor;
    private String cardCost;
    private String cardCmc;
    private String cardType;
    private String powerToughness;
    private String cardText;

    public Card(){
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
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

    public void setCardCmc(String cmc){
        this.cardCmc = cmc;
    }

    public String getCardType(){
        return cardType;
    }

    public void setCardType(String type){
        this.cardType = type;
    }

    public void setPowerToughness(String pt){
        this.powerToughness = pt;
    }

    public String getCardText(){
        return cardText;
    }

    public void setCardText(String text){
        this.cardText = text;
    }

}
