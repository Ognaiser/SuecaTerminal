package org.academia.president;

public enum SuitPresident {
    SPADES("\u2660"),
    DIAMONDS("\u001B[31m"+"\u2666"+"\u001B[0m"),
    HEARTS("\u001B[31m" +"\u2764" +"\u001B[0m"),
    CLUBS("\u2663"),
    JOCKER("J");

    private String symbol;

    SuitPresident(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
