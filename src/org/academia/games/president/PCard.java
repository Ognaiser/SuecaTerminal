package org.academia.games.president;

public class PCard {

    private PCardSuits suit;
    private PCardValues value;

    public PCard(PCardSuits suit, PCardValues value) {
        this.suit = suit;
        this.value = value;
    }

    public PCard() {
        this.suit = PCardSuits.JOCKER;
        this.value = PCardValues.JOKER;
    }


    public PCardSuits getSuit() {
        return suit;
    }

    public PCardValues getValue() {
        return value;
    }


    public String getSuitSymbol() {
        return suit.getSymbol();
    }

    public String getValueSymbol() {
        return value.getValue();
    }

    public String getHandRep() {


        if (value.getValue().equals("Joker")) {

            return "┌───────┐" + ":" +
                    "│" + value.getValue() + suit.getSymbol() + " │:" +
                    "│       │" + ":" +
                    "│   " + suit.getSymbol() + "   │" + ":" +
                    "│       │" + ":" +
                    "│ " + suit.getSymbol() + value.getValue() + "│" + ":" +
                    "└───────┘";

        }

        if (value.getValue().equals("10")) {

            return "┌───────┐" + ":" +
                    "│" + value.getValue() + suit.getSymbol() + "    │:" +
                    "│       │" + ":" +
                    "│   " + suit.getSymbol() + "   │" + ":" +
                    "│       │" + ":" +
                    "│    " + suit.getSymbol() + value.getValue() + "│" + ":" +
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
