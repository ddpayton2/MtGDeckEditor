import com.google.common.collect.Lists;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class EdhDeck extends Deck{

    private List<Card> mainboard = Lists.newArrayList();
    private List<Card> sideboard = Lists.newArrayList();
    private int numberOfCardsInMainboard;
    private int numberOfCardsInSideboard;
    private final Deck.Builder builder = new Deck.Builder();

    public EdhDeck (Deck.Builder builder){
        super(builder);
    }

    public Deck buildEdhDeck(){
        builder.setMinimumNumberOfCardsInMain(99)
                .setMaximumNumberOfCardsInMain(99)
                .setMinimumNumberOfCardsInSide(1)
                .setMaximumNumberOfCardsInSide(1)
                .setMainCardList()
                .setSideCardList()
                .setDeckLegal(false)
                .build();
        return new Deck(builder);
    }
}
