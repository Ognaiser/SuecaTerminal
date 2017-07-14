package org.academia.games.president;

public enum PCardValues {

    JOKER("Joker"),//TODO fix this
    TWO("2"),
    ACE("A"),
    KING("K"),
    JACK("J"),
    QUEEN("Q"),
    TEN("10"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3");


    private String value;


    PCardValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PCardValues getCardValue(String symbol){
        switch (symbol) {
            case "Joker":
                return PCardValues.JOKER;
            case "2":
                return PCardValues.TWO;
            case "A":
                return PCardValues.ACE;
            case "K":
                return PCardValues.KING;
            case "Q":
                return PCardValues.QUEEN;
            case "J":
                return PCardValues.JACK;
            case "10":
                return PCardValues.TEN;
            case "9":
                return PCardValues.NINE;
            case "8":
                return PCardValues.EIGHT;
            case "7":
                return PCardValues.SEVEN;
            case "6":
                return PCardValues.SIX;
            case "5":
                return PCardValues.FIVE;
            case "4":
                return PCardValues.FOUR;
            case "3":
                return PCardValues.THREE;
        }
        return null;
    }

}