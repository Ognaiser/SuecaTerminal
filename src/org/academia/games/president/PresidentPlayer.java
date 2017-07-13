package org.academia.games.president;

import org.academia.games.GameClient;
import org.academia.server.serverClient.ClientPOJO;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PresidentPlayer extends GameClient {

    //TODO:extend GameClientTest! (to see if its ok)

    private List<PresidentCard> hand;
    private String name;
    private boolean passed = false;
    private boolean validPlay;

    public PresidentPlayer(ClientPOJO client) {

        super(client);
        hand = new LinkedList<>();
        name = client.getName();

        out.println("In President Game");
    }


    public void showHand() {

        for (int line = 0; line < 7; line++) {
            for (int i = 0; i < hand.size(); i++) {
                out.print(hand.get(i).getHandRep().split(":")[line]);
                out.print(" ");
            }
            out.println();
        }

        for (int i = 1; i <= hand.size(); i++) {
            out.print("    " + i + "     ");
        }
        out.println();
    }

    public LinkedList<PresidentCard> firstPlay() {
        LinkedList<PresidentCard> cardsPlayed = null;

        while (!isValidPlay()) {

            showHand();
            cardsPlayed = getFirstPlayerCards();

        }

        return cardsPlayed;

    }

    public LinkedList<PresidentCard> assistPlay(PresidentCard cardToAssist, int numberOfCards) {

        LinkedList<PresidentCard> cardsPlayed = null;

        while (!isValidPlay()) {
            System.out.println("Inside while loop on assistPlay()");
            showHand();
            cardsPlayed = getAssistingPlayerCards(cardToAssist, numberOfCards);

        }

        return cardsPlayed;
    }

    private LinkedList<PresidentCard> getAssistingPlayerCards(PresidentCard cardToAssist, int numberOfCardsToAssist) {

        out.println("Please pick a card and number of cards:");
        String cardSymbol = "";
        String numberOfCardsPlayed = "0";

        passed = false;

        try {

            String input = in.readLine(); // EXAMPLE 3 2


            if (hasPassed(input)) {
                return null;
            }

            cardSymbol = input.split(" ")[0];
            numberOfCardsPlayed = input.split(" ")[1];



            if (!isInputValid(cardSymbol, numberOfCardsPlayed, cardToAssist, numberOfCardsToAssist)) {
                numberOfCardsPlayed = "0";
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

        return updateHand(cardSymbol, numberOfCardsPlayed);

    }

    private boolean isInputValid(String symbol, String numberOfCardsPlayed, PresidentCard cardToAssist, int numberOfCardsToAssist) {

        if (!symbolIsValid(symbol)) {
            out.println("That's not a valid card, choose other");
            return false;
        }

        if (!playerHasCards(symbol, numberOfCardsPlayed)) {
            out.println("you dont have that many cards");
            return false;
        }


        if (!compareCards(symbol, numberOfCardsPlayed, cardToAssist, numberOfCardsToAssist)) {
            return false;

        }

        System.out.println("assistPlay is valid");
        return true;

    }

    private boolean compareCards(String symbol, String numberOfCardsPlayed, PresidentCard cardToAssist, int numberOfCardsToAssist) {

        System.out.println("Inside compareCards.");
        System.out.println("\nsymbol is -> " + symbol + "\nnumberOfCardsPlayed is -> " + numberOfCardsPlayed + "\ncardToAssist is:\n" + cardToAssist.getRepresentation() + "\nnumberOfCardsToAssist -> " + numberOfCardsToAssist);

        if (PresidentCards.valueOf(symbol).ordinal() >git  cardToAssist.getValue().ordinal()) {

            System.out.println("Inside first If");
            out.println(" choose a higher card than " + symbol + " and played " + cardToAssist.getValue());
            return false;
        }

        System.out.println("\n 1 - After first If");

        if (Integer.parseInt(numberOfCardsPlayed) != numberOfCardsToAssist ) {
            System.out.println("Inside second If");
            out.println("You need to play " + numberOfCardsToAssist + "cards and played only " + numberOfCardsPlayed);
            return false;
        }

        System.out.println("\n 2 - After second If");

        System.out.println("returned true");
        return true;
    }

    private LinkedList<PresidentCard> getFirstPlayerCards() {

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
        System.out.println(name + " has passed " + passed);
        return passed;
    }

    private LinkedList<PresidentCard> updateHand(String cardSymbol, String numberOfCardsPlayed) {

        LinkedList<PresidentCard> cardsPlayed = new LinkedList<>();

        int counter = 0;
        int iterator = 0;

        for (PresidentCard card : hand) {



            if (card.getValue().getValue().equals(cardSymbol) &&
                    counter <= Integer.parseInt(numberOfCardsPlayed)) {

                System.out.println("trying to remove " + cardSymbol);

                PresidentCard toRemove = hand.remove(iterator);
                cardsPlayed.add(toRemove);
                counter++;

            }

            if (counter == Integer.parseInt(numberOfCardsPlayed)){
                return cardsPlayed;
            }

            iterator++;
            System.out.println("counter -> " + counter + "\niterator -> " + iterator);
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

        System.out.println("Inside Player.playerHasCards");

        int counter = 0;

        for (PresidentCard card : hand) {

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

        for (int i = 0; i < PresidentCards.values().length; i++) {

            if (PresidentCards.values()[i].getValue().equals(value)) {
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


    public boolean isValidPlay() {
        return validPlay;
    }

    public void sendMessage(String msg) {

        out.println(msg);
    }

    public String getName() {
        return name;
    }

    public void receiveCard(PresidentCard card) {

        hand.add(card);
        organizeHand();
    }

    private void organizeHand() {

        LinkedList<PresidentCard> orderedHand = (LinkedList<PresidentCard>) this.hand;

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

        for (PresidentCard card : hand) {

            if (card.getValue().equals(PresidentCards.THREE)
                    && card.getSuit().equals(PresidentSuit.CLUBS)) {
                return true;
            }

        }

        return false;
    }


    public List<PresidentCard> getHand() {
        return hand;
    }
}
