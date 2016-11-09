import com.google.common.collect.Lists;

import java.util.List;

public class LegacyFormat extends Format {

    private final List<MtgSet> legacyLegalMtgSets = Lists.newArrayList();
    private final List<Card> legacyBannedList = Lists.newArrayList();
    private final List<Card> legacyRestrictedList = Lists.newArrayList();
    private Format.Builder builder = new Format.Builder();

    public LegacyFormat(Builder builder) {
        super(builder);
    }

    public void buildLegacyLegalSetsList(List<MtgSet> list){
        for(MtgSet mtgSet : list){
            if(!mtgSet.getMtgSetType().equalsIgnoreCase("Un")){
                legacyLegalMtgSets.add(mtgSet);
            }
        }
    }

    public void buildLegacyBannedList(List<Card> allCardList){
        for(Card card : allCardList){
            if(card.getCardType().equalsIgnoreCase("Conspiracy")){
                legacyBannedList.add(card);
            }
            else if(card.getCardText().contains("playing for ante")){
                legacyBannedList.add(card);
            }
            else if(card.getCardName().equalsIgnoreCase("Ancestral Recall")
                    || card.getCardName().equalsIgnoreCase("Balance")
                    || card.getCardName().equalsIgnoreCase("Bazaar of Baghdad")
                    || card.getCardName().equalsIgnoreCase("Black Lotus")
                    || card.getCardName().equalsIgnoreCase("Channel")
                    || card.getCardName().equalsIgnoreCase("Chaos Orb")
                    || card.getCardName().equalsIgnoreCase("Demonic Consultation")
                    || card.getCardName().equalsIgnoreCase("Dig Through Time")
                    || card.getCardName().equalsIgnoreCase("Earthcraft")
                    || card.getCardName().equalsIgnoreCase("Falling Star")
                    || card.getCardName().equalsIgnoreCase("Fastbond")
                    || card.getCardName().equalsIgnoreCase("Flash")
                    || card.getCardName().equalsIgnoreCase("Frantic Search")
                    || card.getCardName().equalsIgnoreCase("Goblin Recruiter")
                    || card.getCardName().equalsIgnoreCase("Gush")
                    || card.getCardName().equalsIgnoreCase("Hermit Druid")
                    || card.getCardName().equalsIgnoreCase("Imperial Seal")
                    || card.getCardName().equalsIgnoreCase("Library of Alexandria")
                    || card.getCardName().equalsIgnoreCase("Mana Crypt")
                    || card.getCardName().equalsIgnoreCase("Mana Drain")
                    || card.getCardName().equalsIgnoreCase("Mana Vault")
                    || card.getCardName().equalsIgnoreCase("Memory Jar")
                    || card.getCardName().equalsIgnoreCase("Mental Misstep")
                    || card.getCardName().equalsIgnoreCase("Mind Twist")
                    || card.getCardName().equalsIgnoreCase("Mind's Desire")
                    || card.getCardName().equalsIgnoreCase("Mishra's Workshop")
                    || card.getCardName().equalsIgnoreCase("Mox Emerald")
                    || card.getCardName().equalsIgnoreCase("Mox Jet")
                    || card.getCardName().equalsIgnoreCase("Mox Ruby")
                    || card.getCardName().equalsIgnoreCase("Mox Sapphire")
                    || card.getCardName().equalsIgnoreCase("Mox Pearl")
                    || card.getCardName().equalsIgnoreCase("Mystical Tutor")
                    || card.getCardName().equalsIgnoreCase("Necropotence")
                    || card.getCardName().equalsIgnoreCase("Oath of Druids")
                    || card.getCardName().equalsIgnoreCase("Shahrazad")
                    || card.getCardName().equalsIgnoreCase("Skullclamp")
                    || card.getCardName().equalsIgnoreCase("Sol Ring")
                    || card.getCardName().equalsIgnoreCase("Strip Mine")
                    || card.getCardName().equalsIgnoreCase("Survival of the Fittest")
                    || card.getCardName().equalsIgnoreCase("Time Vault")
                    || card.getCardName().equalsIgnoreCase("Time Walk")
                    || card.getCardName().equalsIgnoreCase("Timetwister")
                    || card.getCardName().equalsIgnoreCase("Tinker")
                    || card.getCardName().equalsIgnoreCase("Tolarian Academy")
                    || card.getCardName().equalsIgnoreCase("Treasure Cruise")
                    || card.getCardName().equalsIgnoreCase("Vampiric Tutor")
                    || card.getCardName().equalsIgnoreCase("Wheel of Fortune")
                    || card.getCardName().equalsIgnoreCase("Windfall")
                    || card.getCardName().equalsIgnoreCase("Yawgmoth's Bargain")
                    || card.getCardName().equalsIgnoreCase("Yawgmoth's Will")){
                legacyBannedList.add(card);
            }
        }
    }

    public Format buildLegacyFormat(){
        builder.setFormatName("Legacy")
                .setLegalSets(legacyLegalMtgSets)
                .setBannedList(legacyBannedList)
                .setRestrictedList(legacyRestrictedList)
                .build();
        return new Format(builder);
    }
}
