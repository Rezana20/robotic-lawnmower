package enums;

import java.util.HashMap;
import java.util.Map;

public enum Strategy {
    random("0"),
    custom("1");

    public String strategyType;

    Strategy(String code) {
        this.strategyType = code;
    }

    public String getCode() {
        return this.strategyType;
    }

    private static final Map<String, Strategy> lookup = new HashMap<>();

    static {
        for (Strategy strat : Strategy.values()) {
            lookup.put(strat.getCode(), strat);
        }
    }

    public static Strategy get(String code) {
        return lookup.get(code);
    }


}
