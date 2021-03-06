import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class CardFilter {

    private final List<Card> cardFormatList = Lists.newArrayList();
    private final List<Card> filteredCardList = Lists.newArrayList();
    private final List<Card> searchTermList = Lists.newArrayList();

    public void filterByCardColor(List<Card> cardList, EnumSet<CardColor> colors){
        filteredCardList.clear();
        filteredCardList.addAll(cardList.stream().filter(card -> card.containsAllColors(colors)).collect(Collectors.toList()));
        Collections.sort(filteredCardList);
    }

    public List<Card> getFilteredCardList(){
        return this.filteredCardList;
    }

    public void findTerm(List<Card> list, String term) {

        searchTermList.clear();
        searchTermList.addAll(list.stream().filter(card -> card.getCardName().toUpperCase().contains(term.toUpperCase())
                || card.getCardType().toUpperCase().contains(term.toUpperCase())
                || card.getCardText().toUpperCase().contains(term.toUpperCase())
                || card.getCardCost().toUpperCase().contains(term.toUpperCase())
                || card.getPowerAndToughness().toUpperCase().contains(term.toUpperCase()))
                .collect(Collectors.toList()));
    }

    public List<Card> getCardsWithTerm(){
        return this.searchTermList;
    }

    public List<Card> filterByFormat(List<Card> list, Format format) {

        cardFormatList.clear();
        cardFormatList.addAll(list.stream().filter(card -> !Collections.disjoint(format.getLegalMtgSetsNames(), card.getSetsPrintedIn())).collect(Collectors.toList()));
        format.getBannedList().stream().filter(card -> format.getBannedList().contains(card)).forEach(cardFormatList::remove);
        Collections.sort(cardFormatList);
        return cardFormatList;
    }
}
