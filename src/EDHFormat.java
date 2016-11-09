import com.google.common.collect.Lists;

import java.util.List;

public class EDHFormat extends Format{

    private final List<Set> EDHLegalSets = Lists.newArrayList();
    private final List<Card> EDHBannedList = Lists.newArrayList();
    private final List<Card> EDHRestrictedList = Lists.newArrayList();
    private Format.Builder builder = new Format.Builder();

    public EDHFormat(Builder builder) {
        super(builder);
    }

    public void buildEDHLegalSetList(List<Set> list){
        for(Set set : list){
            if(!set.getSetType().equalsIgnoreCase("Un")){
                EDHLegalSets.add(set);
            }
        }
    }

    public void buildEDHBannedList(List<Card> allCardsList){
        for(Card card : allCardsList){
            if(card.getCardType().equalsIgnoreCase("Conspiracy")){
                EDHBannedList.add(card);
            }
            else if (card.getCardText().contains("playing for ante")){
                EDHBannedList.add(card);
            }
            if(card.getCardName().equalsIgnoreCase("Ancestral Recall")
                    || card.getCardName().equalsIgnoreCase("Balance")
                    || card.getCardName().equalsIgnoreCase("Biorhythm")
                    || card.getCardName().equalsIgnoreCase("Black Lotus")
                    || card.getCardName().equalsIgnoreCase("Braids, Cabal Minion")
                    || card.getCardName().equalsIgnoreCase("Chaos Orb")
                    || card.getCardName().equalsIgnoreCase("Coalition Victory")
                    || card.getCardName().equalsIgnoreCase("Channel")
                    || card.getCardName().equalsIgnoreCase("Emrakul, the Aeons Torn")
                    || card.getCardName().equalsIgnoreCase("Erayo, Soratami Ascendant")
                    || card.getCardName().equalsIgnoreCase("Falling Star")
                    || card.getCardName().equalsIgnoreCase("Fastbond")
                    || card.getCardName().equalsIgnoreCase("Gifts Ungiven")
                    || card.getCardName().equalsIgnoreCase("Griselbrand")
                    || card.getCardName().equalsIgnoreCase("Karakas")
                    || card.getCardName().equalsIgnoreCase("Library of Alexandria")
                    || card.getCardName().equalsIgnoreCase("Limited Resources")
                    || card.getCardName().equalsIgnoreCase("Mox Emerald")
                    || card.getCardName().equalsIgnoreCase("Mox Jet")
                    || card.getCardName().equalsIgnoreCase("Mox Ruby")
                    || card.getCardName().equalsIgnoreCase("Mox Pearl")
                    || card.getCardName().equalsIgnoreCase("Mox Sapphire")
                    || card.getCardName().equalsIgnoreCase("Painter's Servant")
                    || card.getCardName().equalsIgnoreCase("Panoptic Mirror")
                    || card.getCardName().equalsIgnoreCase("Primeval Titna")
                    || card.getCardName().equalsIgnoreCase("Prophet of Kruphix")
                    || card.getCardName().equalsIgnoreCase("Protean Hulk")
                    || card.getCardName().equalsIgnoreCase("Recurring Nightmare")
                    || card.getCardName().equalsIgnoreCase("Rofellos, Llanowar Emissary")
                    || card.getCardName().equalsIgnoreCase("Shahrazad")
                    || card.getCardName().equalsIgnoreCase("Sundering Titan")
                    || card.getCardName().equalsIgnoreCase("Sway of the Stars")
                    || card.getCardName().equalsIgnoreCase("Sylvan Primordial")
                    || card.getCardName().equalsIgnoreCase("Time Vault")
                    || card.getCardName().equalsIgnoreCase("Time Walk")
                    || card.getCardName().equalsIgnoreCase("Tinker")
                    || card.getCardName().equalsIgnoreCase("Tolarian Academy")
                    || card.getCardName().equalsIgnoreCase("Trade Secrets")
                    || card.getCardName().equalsIgnoreCase("Upheaval")
                    || card.getCardName().equalsIgnoreCase("Worldfire")
                    || card.getCardName().equalsIgnoreCase("Yawgmoth's Bargain")){
                EDHBannedList.add(card);
            }
        }
    }

    public Format buildEDHFormat(){
        builder.setFormatName("EDH")
                .setLegalSets(EDHLegalSets)
                .setBannedList(EDHBannedList)
                .setRestrictedList(EDHRestrictedList)
                .build();
        return new Format(builder);
    }
}
