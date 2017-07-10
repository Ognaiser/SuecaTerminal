package org.academia.sueca;

public class Card {

    private Suits suite;
    private SuecaCards cardNumber;

    public Card(Suits suite, SuecaCards cardNumber) {
        this.suite = suite;
        this.cardNumber = cardNumber;
    }

    public Suits getSuite() {
        return suite;
    }

    public SuecaCards getCardNumber() {
        return cardNumber;
    }

    public int getValue(){
        return cardNumber.getValue();
    }

    public String getHandRep(){
        return  "┌───────┐"+":"+
                "│"+cardNumber.getNumber()+suite.getSymbol()+"     │:"+
                "│       │" +":"+
                "│   "+suite.getSymbol()+"   │" +":"+
                "│       │" +":"+
                "│     "+suite.getSymbol()+cardNumber.getNumber()+"│" +":"+
                "└───────┘";

    }

    public String getRepresentacion(){
        return  "┌───────┐" +"\r\n"+
                "│"+cardNumber.getNumber()+suite.getSymbol()+"     │" +"\r\n"+
                "│       │" +"\r\n"+
                "│   "+suite.getSymbol()+"   │" +"\r\n"+
                "│       │" +"\r\n"+
                "│     "+suite.getSymbol()+cardNumber.getNumber()+"│" +"\r\n"+
                "└───────┘";
    }

    @Override
    public String toString() {
        return suite.getSymbol() + cardNumber.getNumber();
    }
}
