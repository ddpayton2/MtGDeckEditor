import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class EDHFormat extends Format{

    private final List<MtgSet> EDHLegalMtgSets = Lists.newArrayList();
    private final List<Card> EDHBannedList = Lists.newArrayList();
    private final List<Card> EDHRestrictedList = Lists.newArrayList();
    private final Format.Builder builder = new Format.Builder();

    public EDHFormat(Builder builder) {
        super(builder);
    }

    public void buildEDHLegalSetList(List<MtgSet> list){
        EDHLegalMtgSets.addAll(list.stream().filter(mtgSet -> !mtgSet.getMtgSetType().equalsIgnoreCase("Un")).collect(Collectors.toList()));
    }

    public void buildEDHBannedList(List<Card> allCardsList){
        List<String> bannedList = Lists.newArrayList(
                "Ancestral Recall",
                "Balance",
                "Biorhythm",
                "Black Lotus",
                "Braids, Cabal Minion",
                "Chaos Orb",
                "Coalition Victory",
                "Channel",
                "Emrakul, the Aeons Torn",
                "Erayo, Soratami Ascendant",
                "Falling Star",
                "Fastbond",
                "Gifts Ungiven",
                "Griselbrand",
                "Karakas",
                "Library of Alexandria",
                "Limited Resources",
                "Mox Emerald",
                "Mox Jet",
                "Mox Ruby",
                "Mox Pearl",
                "Mox Sapphire",
                "Painter's Servant",
                "Panoptic Mirror",
                "Primeval Titan",
                "Prophet of Kruphix",
                "Protean Hulk",
                "Recurring Nightmare",
                "Rofellos, Llanowar Emissary",
                "Shahrazad",
                "Sundering Titan",
                "Sway of the Stars",
                "Sylvan Primordial",
                "Time Vault",
                "Time Walk",
                "Tinker",
                "Tolarian Academy",
                "Trade Secrets",
                "Upheaval",
                "Worldfire",
                "Yawgmoth's Bargain");
        for(Card card : allCardsList){
            if(card.getCardType().equalsIgnoreCase("Conspiracy")){
                EDHBannedList.add(card);
            }
            else if(card.getSetsPrintedIn().contains("pCEL")
                    || card.getSetsPrintedIn().contains("ARC")
                    || card.getSetsPrintedIn().contains("UNH")
                    || card.getSetsPrintedIn().contains("UGL")){
                EDHBannedList.add(card);
            }
            else if (card.getCardText().contains("playing for ante")){
                EDHBannedList.add(card);
            }
            if(bannedList.contains(card.getCardName())){
                EDHBannedList.add(card);
            }
        }
    }

    public Format buildEDHFormat(){
        builder.setFormatName("EDH")
                .setLegalSets(EDHLegalMtgSets)
                .setBannedList(EDHBannedList)
                .setRestrictedList(EDHRestrictedList)
                .build();
        return new Format(builder);
    }
}
