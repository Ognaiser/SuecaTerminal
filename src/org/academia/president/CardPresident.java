package org.academia.president;

import org.academia.sueca.Suit;

public class CardPresident {

    private SuitPresident suitPresident;
    private PresidentCards cardNumber;

    public CardPresident(SuitPresident suitPresident, PresidentCards cardNumber) {
        this.suitPresident = suitPresident;
        this.cardNumber = cardNumber;
    }

    public CardPresident() {
        this.suitPresident = SuitPresident.JOCKER;
        this.cardNumber = PresidentCards.JOCKER;
    }

    public SuitPresident getSuit() {
        return suitPresident;
    }

    public PresidentCards getCardNumber() {
        return cardNumber;
    }

    public int getValue(){
        return cardNumber.getValue();
    }

    public String getHandRep(){
        return  "┌───────┐"+":"+
                "│"+cardNumber.getNumber()+ suitPresident.getSymbol()+"     │:"+
                "│       │" +":"+
                "│   "+ suitPresident.getSymbol()+"   │" +":"+
                "│       │" +":"+
                "│     "+ suitPresident.getSymbol()+cardNumber.getNumber()+"│" +":"+
                "└───────┘";

    }

    public String getRepresentation(){
        return  "┌───────┐" +"\r\n"+
                "│"+cardNumber.getNumber()+ suitPresident.getSymbol()+"     │" +"\r\n"+
                "│       │" +"\r\n"+
                "│   "+ suitPresident.getSymbol()+"   │" +"\r\n"+
                "│       │" +"\r\n"+
                "│     "+ suitPresident.getSymbol()+cardNumber.getNumber()+"│" +"\r\n"+
                "└───────┘";
    }

    @Override
    public String toString() {
        return suitPresident.getSymbol() + cardNumber.getNumber();
    }
}
