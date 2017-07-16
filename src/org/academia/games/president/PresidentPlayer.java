package org.academia.games.president;

import org.academia.games.GameClient;
import org.academia.server.serverClient.ClientPOJO;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PresidentPlayer extends GameClient {

    //TODO:extend GameClientTest! (to see if its ok)

    private List<PCard> hand;
    private String name;
    private boolean passed = false;
    private boolean validPlay;

    public PresidentPlayer(ClientPOJO client) {

        super(client);
        hand = new LinkedList<>();
        name = client.getName();

        out.println("In President Game");
    }


    public void printHand() {

        for (int line = 0; line < 7; line++) {
            for (int i = 0; i < hand.size(); i++) {
                out.print(hand.get(i).getHandRep().split(":")[line]);
                out.print(" ");
            }
            out.println();
        }

        out.println();

    }

    public void sendMessage(String msg) {

        out.println(msg);
    }

    public LinkedList<PCard> firstPlay() {
        LinkedList<PCard> cardsPlayed = null;
        validPlay = false;

        System.out.println("Asking first player to pick card(s)");

        while (!isValidPlay()) {

            printHand();
            cardsPlayed = getFirstPlayerCards();

        }

        return cardsPlayed;

    }

    public LinkedList<PCard> assistPlay(PCard cardToAssist, int numberOfCards) {

        LinkedList<PCard> cardsPlayed = null;
        validPlay = false;

        while (!isValidPlay()) {
            System.out.println("Inside while loop on assistPlay()");
            printHand();
            cardsPlayed = getAssistingPlayerCards(cardToAssist, numberOfCards);

        }

        return cardsPlayed;
    }

    private LinkedList<PCard> getAssistingPlayerCards(PCard cardToAssist, int numberOfCardsToAssist) {

        out.println("Please pick a card and number of cards:");
        String firstString = "";
        String secondString = "0";

        passed = false;

        try {

            String input = in.readLine(); // EXAMPLE 3 2


            if (hasPassed(input)) {
                return null;
            }

            firstString = input.split(" ")[0];
            secondString = input.split(" ")[1];


            if (!isInputValid(firstString, secondString, cardToAssist, numberOfCardsToAssist)) {
                validPlay = false;
                return null;

            } else {

                validPlay = true;
            }

            System.out.println("Play is valid? " + validPlay);

        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.exit(1);

        }

        System.out.println("Going to update hand");

        return updateHand(firstString, secondString);

    }

    private boolean isInputValid(String firstString, String secondString, PCard cardToAssist, int numberOfCardsToAssist) {

        if (!symbolIsValid(firstString)) {
            out.println("That's not a valid card, choose another");
            return false;
        }

        if (!playerHasCards(firstString, secondString)) {
            out.println("You don't have that many cards!");
            return false;
        }

        if (!compareCards(firstString, secondString, cardToAssist, numberOfCardsToAssist)) {
            return false;

        }

        System.out.println("assistPlay is valid");
        return true;

    }

    private boolean compareCards(String symbol, String numberOfCardsPlayed, PCard cardToAssist, int numberOfCardsToAssist) {

        System.out.println("Inside compareCards.");
        System.out.println("\nsymbol is -> " + symbol + "\nnumberOfCardsPlayed is -> " + numberOfCardsPlayed + "\ncardToAssist is:\n" + cardToAssist.getRepresentation() + "\nnumberOfCardsToAssist -> " + numberOfCardsToAssist);

        System.out.println(" is higher to " + cardToAssist.getValue().ordinal());

        if (PCardValues.getCardBySymbol(symbol).ordinal() > cardToAssist.getValue().ordinal()) {

            System.out.println("Inside first If");
            out.println("You played " + symbol + " which is not high enough\nYou must choose a card higher than " + cardToAssist.getValueSymbol());
            return false;
        }


        if (Integer.parseInt(numberOfCardsPlayed) != numberOfCardsToAssist) {
            System.out.println("Inside second If");
            out.println("You need to play " + numberOfCardsToAssist + " equal cards and higher than " + cardToAssist.getValueSymbol());
            return false;
        }


        System.out.println("returned true");
        return true;
    }

    private LinkedList<PCard> getFirstPlayerCards() {

        out.println("Please pick a card and number of cards:");
        String symbol = "";
        String numberOfCards = "0";

        passed = false;

        try {

            String input = in.readLine(); // EXAMPLE A 3

            if (hasPassed(input)) {
                return null;
            }

            symbol = input.split(" ")[0];
            numberOfCards = input.split(" ")[1];

            System.out.println("validating assistPlay");

            if (!isInputValid(symbol, numberOfCards)) {
                numberOfCards = "0";
                validPlay = false;

                return null;
            } else {

                validPlay = true;
            }

        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.exit(1);

        }

        return updateHand(symbol, numberOfCards);

    }

    private boolean hasPassed(String input) {

        passed = input.equals("pass");
        System.out.println(name + " has passed? " + passed);
        validPlay = true;
        return passed;
    }

    //TODO: Bug is here. Doesn't remove more than one card!
    private LinkedList<PCard> updateHand(String cardSymbol, String numberOfCardsPlayed) {

        LinkedList<PCard> cardsPlayed = new LinkedList<>();
        int numberOfRemovedCards = 0;
        int numberOfCardsToRemove = Integer.parseInt(numberOfCardsPlayed);
        int cardIndex;

        System.out.println("Trying to remove " + numberOfCardsToRemove + " card(s) " + cardSymbol);

        while (numberOfRemovedCards < numberOfCardsToRemove) {

            for (PCard card : hand) {

                System.out.println("Looking to ->" + card.toString() + "| In position " + hand.indexOf(card));

                if (card.getValueSymbol().equals(cardSymbol) ) {

                    cardIndex = hand.indexOf(card);
                    PCard toRemove = hand.remove(cardIndex);
                    cardsPlayed.add(toRemove);

                    numberOfRemovedCards++;
                    System.out.println("removed card(s) " + cardsPlayed.toString());
                    break;
                }
            }
        }

        System.out.println("cards played " + cardsPlayed.toString());

        return cardsPlayed;

    }

    private boolean isInputValid(String symbol, String numberOfCards) {

        if (!symbolIsValid(symbol)) {
            out.println("That's not a valid card, choose other");
            return false;
        }

        if (!playerHasCards(symbol, numberOfCards)) {
            out.println("you dont have that many cards");
            return false;
        }

        System.out.println("input is valid");
        return true;

    }

    private boolean playerHasCards(String symbol, String numberOfCards) {

        int counter = 0;

        for (PCard card : hand) {

            if (card.getValue().getValue().equals(symbol)) {


                counter++;
            }
        }

        if (counter == 0) {
            out.println("You don't have that card");
            return false;
        }

        if (Integer.parseInt(numberOfCards) < 0 ||
                Integer.parseInt(numberOfCards) > 4 ||
                counter < Integer.parseInt(numberOfCards)) {

            out.println("You only have " + counter + " " + symbol);
            return false;
        }

        return true;
    }

    private boolean symbolIsValid(String value) {

        for (int i = 0; i < PCardValues.values().length; i++) {

            if (PCardValues.values()[i].getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void checkCommand(String input) {

        String[] words = input.split(" ");
        if (words[0].equals("!pass")) {
            validPlay = true;
        }
        //TODO check how to do the pass

    }
<<<<<<< HEAD
    
=======


>>>>>>> f903c02f7c847d38e2a39b70f738c6c1549e1e2f
    private boolean isValidPlay() {
        return validPlay;
    }

    public String getName() {
        return name;
    }

    public void receiveCard(PCard card) {

        hand.add(card);
        organizeHand();
    }

    private void organizeHand() {

        LinkedList<PCard> orderedHand = (LinkedList<PCard>) this.hand;

        for (int i = 0; i < orderedHand.size(); i++) {

            for (int j = i; j > 0; j--) {

                if (j != 0 && orderedHand.get(j).getValue().compareTo(orderedHand.get(j - 1).getValue()) < 0) {

                    orderedHand.add(j - 1, orderedHand.remove(j));
                } else {

                    break;
                }
            }
        }

    }

    public boolean hasThreeOfClubs() {

        for (PCard card : hand) {

            if (card.getValue().equals(PCardValues.THREE)
                    && card.getSuit().equals(PCardSuits.CLUBS)) {
                return true;
            }

        }

        return false;
    }

    public List<PCard> getHand() {
        return hand;
    }
}
