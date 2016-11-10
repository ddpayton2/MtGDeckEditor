import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class ModernFormat extends Format{

    private final List<MtgSet> modernLegalMtgSets = Lists.newArrayList();
    private final List<Card> modernBannedList = Lists.newArrayList();
    private final List<Card> modernRestrictedList = Lists.newArrayList();
    private final Format.Builder builder = new Format.Builder();

    public ModernFormat(Builder maker) {
        super(maker);
    }

    public void buildModernLegalSetsList(List<MtgSet> list){
        LocalDate eighthEdReleaseDate = LocalDate.of(2003,7,28);
        modernLegalMtgSets.addAll(list.stream().filter(mtgSet -> (mtgSet.getReleaseDate().isEqual(eighthEdReleaseDate) && (mtgSet.getMtgSetType().equalsIgnoreCase("Expansion")
                || mtgSet.getMtgSetType().equalsIgnoreCase("Core"))) || (mtgSet.getReleaseDate().isAfter(eighthEdReleaseDate)
                && (mtgSet.getMtgSetType().equalsIgnoreCase("Expansion")) || mtgSet.getMtgSetType().equalsIgnoreCase("Core"))).collect(Collectors.toList()));
    }

    public void buildModernBannedList(List<Card> cardList){
        modernBannedList.addAll(cardList.stream().filter(card -> card.getCardName().equalsIgnoreCase("Ancient Den")
                || card.getCardName().equalsIgnoreCase("Birthing Pod")
                || card.getCardName().equalsIgnoreCase("Blazing Shoal")
                || card.getCardName().equalsIgnoreCase("Bloodbraid Elf")
                || card.getCardName().equalsIgnoreCase("Chrome Mox")
                || card.getCardName().equalsIgnoreCase("Cloudpost")
                || card.getCardName().equalsIgnoreCase("Dark Depths")
                || card.getCardName().equalsIgnoreCase("Deathrite Shaman")
                || card.getCardName().equalsIgnoreCase("Dig Through Time")
                || card.getCardName().equalsIgnoreCase("Dread Return")
                || card.getCardName().equalsIgnoreCase("Eye of Ugin")
                || card.getCardName().equalsIgnoreCase("Glimpse of Nature")
                || card.getCardName().equalsIgnoreCase("Great Furnace")
                || card.getCardName().equalsIgnoreCase("Green Sun's Zenith")
                || card.getCardName().equalsIgnoreCase("Hypergenesis")
                || card.getCardName().equalsIgnoreCase("Jace, the Mind Sculptor")
                || card.getCardName().equalsIgnoreCase("Mental Misstep")
                || card.getCardName().equalsIgnoreCase("Ponder")
                || card.getCardName().equalsIgnoreCase("Preordain")
                || card.getCardName().equalsIgnoreCase("Punishing Fire")
                || card.getCardName().equalsIgnoreCase("Rite of Flame")
                || card.getCardName().equalsIgnoreCase("Seat of the Synod")
                || card.getCardName().equalsIgnoreCase("Second Sunrise")
                || card.getCardName().equalsIgnoreCase("Seething Song")
                || card.getCardName().equalsIgnoreCase("Sensei's Divining Top")
                || card.getCardName().equalsIgnoreCase("Skullclamp")
                || card.getCardName().equalsIgnoreCase("Splinter Twin")
                || card.getCardName().equalsIgnoreCase("Stoneforge Mystic")
                || card.getCardName().equalsIgnoreCase("Summer Bloom")
                || card.getCardName().equalsIgnoreCase("Treasure Cruise")
                || card.getCardName().equalsIgnoreCase("Tree of Tales")
                || card.getCardName().equalsIgnoreCase("Umezawa's Jitte")
                || card.getCardName().equalsIgnoreCase("Vault of Whispers")).collect(Collectors.toList()));
    }

    public Format buildModernFormat(){
        builder.setFormatName("Modern")
                .setLegalSets(modernLegalMtgSets)
                .setBannedList(modernBannedList)
                .setRestrictedList(modernRestrictedList)
                .build();
        return new Format(builder);
    }
}
