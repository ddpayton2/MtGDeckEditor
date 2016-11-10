import com.google.common.collect.Lists;

import java.util.EnumSet;
import java.util.List;

public class CardColorFilter {

    private List<Card> filteredCardList = Lists.newArrayList();

    public void filterByCardColor(List<Card> cardList, EnumSet<CardColor> colors){
        for(Card card : cardList){
            if(card.containsAllColors(colors)){
                filteredCardList.add(card);
            }
        }
    }

    public List<Card> getFilteredCardList(){
        return this.filteredCardList;
    }
}
