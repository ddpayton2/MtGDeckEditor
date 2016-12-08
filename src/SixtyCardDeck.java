import com.google.common.collect.Lists;

import java.util.List;

public class SixtyCardDeck extends Deck {

    private List<Card> mainboard = Lists.newArrayList();
    private List<Card> sideboard = Lists.newArrayList();
    private int numberOfCardsInMainboard;
    private int numberOfCardsInSideboard;
    private final Deck.Builder builder = new Deck.Builder();

    public SixtyCardDeck(Deck.Builder builder){
        super(builder);
    }

    public Deck buildSixtyCardDeck(){
        builder.setMinimumNumberOfCardsInMain(60)
                .setMaximumNumberOfCardsInSide(15)
                .setMaximumCopiesOfCard(4)
                .setMainCardList()
                .setSideCardList()
                .setDeckLegal(false)
                .build();
        return new Deck(builder);
    }
}
