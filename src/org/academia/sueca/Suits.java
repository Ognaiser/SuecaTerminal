package org.academia.sueca;

public enum Suits {
    SPADES("\u2660"),
    DIAMONDS("\u2666"),
    HEARTS("\u2764"),
    CLUBS("\u2663");

    private String symbol;

    Suits(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
