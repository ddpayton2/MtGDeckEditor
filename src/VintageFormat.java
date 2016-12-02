import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class VintageFormat extends Format{

    private final List<MtgSet> vintageLegalMtgSets = Lists.newArrayList();
    private final List<Card> vintageBannedList = Lists.newArrayList();
    private final List<Card> vintageRestrictedList = Lists.newArrayList();
    private final Format.Builder builder = new Format.Builder();

    public VintageFormat(Builder builder) {
        super(builder);
    }

    public void buildVintageLegalSetsList(List<MtgSet> list){
        vintageLegalMtgSets.addAll(list.stream().filter(mtgSet -> !mtgSet.getMtgSetType().equalsIgnoreCase("Un")).collect(Collectors.toList()));
    }

    public void buildVintageBannedList(List<Card> allCardList){
        for(Card card : allCardList){
            if(card.getCardType().equalsIgnoreCase("Conspiracy")){
                vintageBannedList.add(card);
            }
            else if(card.getSetsPrintedIn().contains("pCEL")
                    || card.getSetsPrintedIn().contains("ARC")
                    || card.getSetsPrintedIn().contains("UNH")
                    || card.getSetsPrintedIn().contains("UGL")){
                vintageBannedList.add(card);
            }
            else if (card.getCardText().contains("playing for ante")){
                vintageBannedList.add(card);
            }
            else if(card.getCardName().equalsIgnoreCase("Chaos Orb")
                    || card.getCardName().equalsIgnoreCase("Falling Star")
                    || card.getCardName().equalsIgnoreCase("Shahrazad")){
                vintageBannedList.add(card);
            }
        }
    }

    public void buildVintageRestrictedList(List<Card> allCardList){
        vintageRestrictedList.addAll(allCardList.stream().filter(card -> card.getCardName().equalsIgnoreCase("Ancestral Recall")
                || card.getCardName().equalsIgnoreCase("Balance")
                || card.getCardName().equalsIgnoreCase("Black Lotus")
                || card.getCardName().equalsIgnoreCase("Brainstorm")
                || card.getCardName().equalsIgnoreCase("Chalice of the Void")
                || card.getCardName().equalsIgnoreCase("Channel")
                || card.getCardName().equalsIgnoreCase("Demonic Consultation")
                || card.getCardName().equalsIgnoreCase("Demonic Tutor")
                || card.getCardName().equalsIgnoreCase("Dig Through Time")
                || card.getCardName().equalsIgnoreCase("Fastbond")
                || card.getCardName().equalsIgnoreCase("Flash")
                || card.getCardName().equalsIgnoreCase("Imperial Seal")
                || card.getCardName().equalsIgnoreCase("Library of Alexandria")
                || card.getCardName().equalsIgnoreCase("Lion's Eye Diamond")
                || card.getCardName().equalsIgnoreCase("Lodestone Golem")
                || card.getCardName().equalsIgnoreCase("Lotus Petal")
                || card.getCardName().equalsIgnoreCase("Mana Crypt")
                || card.getCardName().equalsIgnoreCase("Mana Vault")
                || card.getCardName().equalsIgnoreCase("Memory Jar")
                || card.getCardName().equalsIgnoreCase("Merchant Scroll")
                || card.getCardName().equalsIgnoreCase("Mind's Desire")
                || card.getCardName().equalsIgnoreCase("Mox Emerald")
                || card.getCardName().equalsIgnoreCase("Mox Jet")
                || card.getCardName().equalsIgnoreCase("Mox Ruby")
                || card.getCardName().equalsIgnoreCase("Mox Pearl")
                || card.getCardName().equalsIgnoreCase("Mox Sapphire")
                || card.getCardName().equalsIgnoreCase("Mystical Tutor")
                || card.getCardName().equalsIgnoreCase("Necropotence")
                || card.getCardName().equalsIgnoreCase("Ponder")
                || card.getCardName().equalsIgnoreCase("Sol Ring")
                || card.getCardName().equalsIgnoreCase("Strip Mine")
                || card.getCardName().equalsIgnoreCase("Time Vault")
                || card.getCardName().equalsIgnoreCase("Time Walk")
                || card.getCardName().equalsIgnoreCase("Timetwister")
                || card.getCardName().equalsIgnoreCase("Tinker")
                || card.getCardName().equalsIgnoreCase("Tolarian Academy")
                || card.getCardName().equalsIgnoreCase("Treasure Cruise")
                || card.getCardName().equalsIgnoreCase("Trinisphere")
                || card.getCardName().equalsIgnoreCase("Vampiric Tutor")
                || card.getCardName().equalsIgnoreCase("Wheel of Fortune")
                || card.getCardName().equalsIgnoreCase("Windfall")
                || card.getCardName().equalsIgnoreCase("Yawgmoth's Bargain")
                || card.getCardName().equalsIgnoreCase("Yawgmoth's Will")).collect(Collectors.toList()));
    }

    public Format buildVintageFormat(){
        builder.setFormatName("Vintage")
                .setLegalSets(vintageLegalMtgSets)
                .setBannedList(vintageBannedList)
                .setRestrictedList(vintageRestrictedList)
                .build();
        return new Format(builder);
    }
}
