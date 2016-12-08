import com.google.common.collect.Lists;

import java.util.List;

public class Deck {

    public static final class Builder{
        private List<Card> mainboard;
        private List<Card> sideboard;
        private int minimumNumberOfCardsInMain;
        private int maximumNumberOfCardsInMain;
        private int maximumNumberOfCardsInSide;
        private int minimumNumberOfCardsInSide;
        private int maximumCopiesOfCard;
        private boolean isDeckLegal;

        public Builder setMainCardList(){
            mainboard = Lists.newArrayList();
            return this;
        }

        public Builder setSideCardList(){
            sideboard = Lists.newArrayList();
            return this;
        }

        public Builder setMinimumNumberOfCardsInMain(int minimumNumberOfCardsInMain) {
            this.minimumNumberOfCardsInMain = minimumNumberOfCardsInMain;
            return this;
        }

        public Builder setMaximumNumberOfCardsInMain(int maximumNumberOfCardsInMain) {
            this.maximumNumberOfCardsInMain = maximumNumberOfCardsInMain;
            return this;
        }

        public Builder setMaximumNumberOfCardsInSide(int maximumNumberOfCardsInSide) {
            this.maximumNumberOfCardsInSide = maximumNumberOfCardsInSide;
            return this;
        }

        public Builder setMinimumNumberOfCardsInSide(int minNumber){
            this.minimumNumberOfCardsInSide = minNumber;
            return this;
        }

        public Builder setDeckLegal(boolean deckLegal) {
            isDeckLegal = deckLegal;
            return this;
        }

        public Builder setMaximumCopiesOfCard(int maxCopiesOfCard){
            maximumCopiesOfCard = maxCopiesOfCard;
            return this;
        }

        public Deck build(){
            return new Deck(this);
        }
    }

    private List<Card> deckListMain;
    private List<Card> deckListSide;
    private int minimumNumberOfCardsInMain;
    private int maximumNumberOfCardsInMain;
    private int maximumNumberOfCardsInSide;
    private int minimumNumberOfCardsInSide;
    private boolean isDeckLegal;

    @SuppressWarnings("WeakerAccess")
    public Deck(Builder builder){
        this.deckListMain = builder.mainboard;
        this.deckListSide = builder.sideboard;
        this.maximumNumberOfCardsInMain = builder.maximumNumberOfCardsInMain;
        this.minimumNumberOfCardsInMain = builder.minimumNumberOfCardsInMain;
        this.maximumNumberOfCardsInSide = builder.maximumNumberOfCardsInSide;
        this.maximumNumberOfCardsInSide = builder.minimumNumberOfCardsInSide;
        this.isDeckLegal = builder.isDeckLegal;
    }

    public int getMaximumNumberOfCardsInMain(){
        return this.maximumNumberOfCardsInMain;
    }

    public int getMinimumNumberOfCardsInMain(){
        return this.minimumNumberOfCardsInMain;
    }

    public int getMaximumNumberOfCardsInSide(){
        return this.maximumNumberOfCardsInSide;
    }

    public int getMinimumNumberOfCardsInSide(){
        return this.minimumNumberOfCardsInSide;
    }

    public boolean getIsDeckLegal(){
        return this.isDeckLegal;
    }

    public void addCardToMain(Card card){
        deckListMain.add(card);
    }

    public void addCardToSide(Card card){
        deckListSide.add(card);
    }

    public void removeCardFromMain(Card card){
        deckListMain.remove(card);
    }

    public void removeCardFromSide(Card card){
        deckListSide.remove(card);
    }
}
