import com.google.common.collect.Lists;

import java.util.EnumSet;
import java.util.List;

public class CardFilter {

    private List<Card> cardFormatList = Lists.newArrayList();
    private List<Card> filteredCardList = Lists.newArrayList();
    private List<Card> searchTermList = Lists.newArrayList();

    public void filterByCardColor(List<Card> cardList, EnumSet<CardColor> colors){
        filteredCardList.clear();
        for(Card card : cardList){
            if(card.containsAllColors(colors)){
                filteredCardList.add(card);
            }
        }
    }

    public List<Card> getFilteredCardList(){
        return this.filteredCardList;
    }

    public void findTerm(List<Card> list, String term) {
        searchTermList.clear();
        for(Card card : list){
            if(card.getCardName().toUpperCase().contains(term.toUpperCase()) || card.getCardType().toUpperCase().contains(term.toUpperCase())
                    || card.getCardText().toUpperCase().contains(term.toUpperCase())){
                searchTermList.add(card);
            }
        }
    }

    public List<Card> getCardsWithTerm(){
        return this.searchTermList;
    }

    public void filterByFormat(List<Card> list, Format format) {
        for(Card card : list){
            if(format.getLegalMtgSetsNames().contains(card.getSetsPrintedIn()));
        }
    }

    public List<Card> getCardFormatList(){
        return this.cardFormatList;
    }
}
