package org.academia.games.sueca;

public enum SuecaSuit {
    SPADES("\u2660"),
    DIAMONDS("\u001B[31m"+"\u2666"+"\u001B[0m"),
    HEARTS("\u001B[31m" +"\u2764" +"\u001B[0m"),
    CLUBS("\u2663");

    private String symbol;

    SuecaSuit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
