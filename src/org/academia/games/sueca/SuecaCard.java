package org.academia.games.sueca;

public class SuecaCard {

    private SuecaSuit suecaSuit;
    private SuecaCards cardNumber;

    public SuecaCard(SuecaSuit suecaSuit, SuecaCards cardNumber) {
        this.suecaSuit = suecaSuit;
        this.cardNumber = cardNumber;
    }


    public SuecaSuit getSuecaSuit() {
        return suecaSuit;
    }

    public SuecaCards getCardNumber() {
        return cardNumber;
    }

    public int getValue(){
        return cardNumber.getValue();
    }

    public String getHandRep(){
        return  "┌───────┐"+":"+
                "│"+cardNumber.getNumber()+ suecaSuit.getSymbol()+"     │:"+
                "│       │" +":"+
                "│   "+ suecaSuit.getSymbol()+"   │" +":"+
                "│       │" +":"+
                "│     "+ suecaSuit.getSymbol()+cardNumber.getNumber()+"│" +":"+
                "└───────┘";

    }

    public String getRepresentation(){
        return  "┌───────┐" +"\r\n"+
                "│"+cardNumber.getNumber()+ suecaSuit.getSymbol()+"     │" +"\r\n"+
                "│       │" +"\r\n"+
                "│   "+ suecaSuit.getSymbol()+"   │" +"\r\n"+
                "│       │" +"\r\n"+
                "│     "+ suecaSuit.getSymbol()+cardNumber.getNumber()+"│" +"\r\n"+
                "└───────┘";
    }

    @Override
    public String toString() {
        return suecaSuit.getSymbol() + cardNumber.getNumber();
    }
}
