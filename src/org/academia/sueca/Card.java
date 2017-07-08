package org.academia.sueca;

public class Card {

    private Suites suite;
    private CardsNumber cardNumber;

    public Card(Suites suite, CardsNumber cardNumber) {
        this.suite = suite;
        this.cardNumber = cardNumber;
    }

    public Suites getSuite() {
        return suite;
    }

    public CardsNumber getCardNumber() {
        return cardNumber;
    }

}
