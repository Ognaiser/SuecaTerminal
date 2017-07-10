package org.academia.sueca;

public enum SuecaCards {
    ACE("A", 11),
    SEVEN("7",10),
    KING("K",4),
    JACK("J",3),
    QUEEN("Q",2),
    SIX("6",0),
    FIVE("5",0),
    FOUR("4",0),
    THREE("3",0),
    TWO("2",0);

    private String number;
    private int value;

    SuecaCards(String number , int value) {
        this.number = number;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getNumber() {
        return number;
    }
}
