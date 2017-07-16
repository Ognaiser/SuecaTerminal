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
        return value.getSymbol();
    }

    public String getHandRep() {


        if (getValueSymbol().equals("Joker")) {

            return "┌───────┐" + ":" +
                    "│" + getValueSymbol() + "  │:" +
                    "│       │" + ":" +
                    "│   " + getSuitSymbol() + "   │" + ":" +
                    "│       │" + ":" +
                    "│ " + getValueSymbol() + " │" + ":" +
                    "└───────┘";

        }

        if (getValueSymbol().equals("10")) {

            return "┌───────┐" + ":" +
                    "│" + getValueSymbol() + getSuitSymbol() + "    │:" +
                    "│       │" + ":" +
                    "│   " + getSuitSymbol() + "   │" + ":" +
                    "│       │" + ":" +
                    "│    " + getSuitSymbol() + getValueSymbol() + "│" + ":" +
                    "└───────┘";

        }

        return "┌───────┐" + ":" +
                "│" + getValueSymbol() + getSuitSymbol() + "     │:" +
                "│       │" + ":" +
                "│   " + getSuitSymbol() + "   │" + ":" +
                "│       │" + ":" +
                "│     " + getSuitSymbol() + getValueSymbol() + "│" + ":" +
                "└───────┘";

    }

    public String getRepresentation() {

        if (getValueSymbol().equals("Joker")) {

            return "┌───────┐" + "\r\n" +
                    "│" + getValueSymbol() + "  │\r\n" +
                    "│       │" + "\r\n" +
                    "│   " + getSuitSymbol() + "   │" + "\r\n" +
                    "│       │" + "\r\n" +
                    "│ " + getValueSymbol() + " │" + "\r\n" +
                    "└───────┘";

        }

        if (getValueSymbol().equals("10")) {

            return "┌───────┐" + "\r\n" +
                    "│" + getValueSymbol() + getSuitSymbol() + "    │\r\n" +
                    "│       │" + "\r\n" +
                    "│   " + getSuitSymbol() + "   │" + "\r\n" +
                    "│       │" + "\r\n" +
                    "│    " + getSuitSymbol() + getValueSymbol() + "│" + "\r\n" +
                    "└───────┘";

        }

        return "┌───────┐" + "\r\n" +
                "│" + getValueSymbol() + getSuitSymbol() + "     │" + "\r\n" +
                "│       │" + "\r\n" +
                "│   " + getSuitSymbol() + "   │" + "\r\n" +
                "│       │" + "\r\n" +
                "│     " + getSuitSymbol() + getValueSymbol() + "│" + "\r\n" +
                "└───────┘";
    }

    @Override
    public String toString() {
        return getSuitSymbol() + getValueSymbol();
    }
}
