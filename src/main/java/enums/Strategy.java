package enums;

public enum Strategy {
    RANDOM(0),
    CUSTOM(1);

    public int stratCode;

    Strategy(int stratCode) {
        this.stratCode = stratCode;
    }


}
