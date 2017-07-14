package org.academia.games.president;

public class PresidentCard {

    private PresidentSuit suit;
    private PresidentCards value;

    public PresidentCard(PresidentSuit suit, PresidentCards value) {
        this.suit = suit;
        this.value = value;
    }

    public PresidentCard() {
        this.suit = PresidentSuit.JOCKER;
        this.value = PresidentCards.JOKER;
    }


    public PresidentSuit getSuit() {
        return suit;
    }

    public PresidentCards getValue() {
        return value;
    }


    public String getHandRep() {


        if (value.getValue().equals("Joker")) {

            return  "┌───────┐"+":"+
                    "│"+ value.getValue()+ suit.getSymbol()+" │:"+
                    "│       │" +":"+
                    "│   "+ suit.getSymbol()+"   │" +":"+
                    "│       │" +":"+
                    "│ "+ suit.getSymbol()+ value.getValue()+"│" +":"+
                    "└───────┘";

        }

        if (value.getValue().equals("10")) {

        return  "┌───────┐"+":"+
                "│"+ value.getValue()+ suit.getSymbol()+"    │:"+
                "│       │" +":"+
                "│   "+ suit.getSymbol()+"   │" +":"+
                "│       │" +":"+
                "│    "+ suit.getSymbol()+ value.getValue()+"│" +":"+
                "└───────┘";

        }

        return "┌───────┐" + ":" +
                "│" + value.getValue() + suit.getSymbol() + "     │:" +
                "│       │" + ":" +
                "│   " + suit.getSymbol() + "   │" + ":" +
                "│       │" + ":" +
                "│     " + suit.getSymbol() + value.getValue() + "│" + ":" +
                "└───────┘";

    }

    public String getRepresentation() {
        return "┌───────┐" + "\r\n" +
                "│" + value.getValue() + suit.getSymbol() + "     │" + "\r\n" +
                "│       │" + "\r\n" +
                "│   " + suit.getSymbol() + "   │" + "\r\n" +
                "│       │" + "\r\n" +
                "│     " + suit.getSymbol() + value.getValue() + "│" + "\r\n" +
                "└───────┘";
    }

    @Override
    public String toString() {
        return suit.getSymbol() + value.getValue();
    }
}
