package org.academia.games.president;

public enum PresidentCards {

    JOCKER("B"),//TODO fix this
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


    PresidentCards(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}