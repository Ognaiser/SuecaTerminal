package org.academia.sueca;

public class Card {

    private Suits suite;
    private CardsNumber cardNumber;

    public Card(Suits suite, CardsNumber cardNumber) {
        this.suite = suite;
        this.cardNumber = cardNumber;
    }

    public Suits getSuite() {
        return suite;
    }

    public CardsNumber getCardNumber() {
        return cardNumber;
    }

    public int getValue(){
        return cardNumber.getValue();
    }

    @Override
    public String toString() {
        return suite.getSymbol() + cardNumber.getNumber();
    }
}
