@SuppressWarnings("WeakerAccess")
public enum CardColor {

    COLORLESS("C"),
    WHITE("W"),
    BLUE("U"),
    BLACK("B"),
    RED("R"),
    GREEN("G"),
    EMPTY("");

    private final String colorNickname;

    CardColor(String colorNickname){
        this.colorNickname = colorNickname;
    }

    public static CardColor parse(String nickname){
        for(CardColor color : values()){
            if(color.colorNickname.equals(nickname)){
                return color;
            }
        }
        return null;
    }
}
