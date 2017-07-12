package org.academia.sueca;

public class Card {

    private Suit suit;
    private SuecaCards cardNumber;

    public Card(Suit suit,SuecaCards cardNumber) {
        this.suit = suit;
        this.cardNumber = cardNumber;
    }


    public Suit getSuit() {
        return suit;
    }

    public SuecaCards getCardNumber() {
        return cardNumber;
    }

    public int getValue(){
        return cardNumber.getValue();
    }

    public String getHandRep(){
        return  "┌───────┐"+":"+
                "│"+cardNumber.getNumber()+ suit.getSymbol()+"     │:"+
                "│       │" +":"+
                "│   "+ suit.getSymbol()+"   │" +":"+
                "│       │" +":"+
                "│     "+ suit.getSymbol()+cardNumber.getNumber()+"│" +":"+
                "└───────┘";

    }

    public String getRepresentation(){
        return  "┌───────┐" +"\r\n"+
                "│"+cardNumber.getNumber()+ suit.getSymbol()+"     │" +"\r\n"+
                "│       │" +"\r\n"+
                "│   "+ suit.getSymbol()+"   │" +"\r\n"+
                "│       │" +"\r\n"+
                "│     "+ suit.getSymbol()+cardNumber.getNumber()+"│" +"\r\n"+
                "└───────┘";
    }

    @Override
    public String toString() {
        return suit.getSymbol() + cardNumber.getNumber();
    }
}
