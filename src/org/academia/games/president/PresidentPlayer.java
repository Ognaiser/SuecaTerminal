package org.academia.games.president;

import org.academia.games.GameClient;
import org.academia.games.sueca.SuecaCard;
import org.academia.server.serverClient.ClientPOJO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
        name = getName();
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

    public LinkedList<PresidentCard> play() {
        LinkedList<PresidentCard> cardsPlayed = null;

        while (!isValidPlay()) {

            showHand();
            cardsPlayed = getPlay();

        }

        return cardsPlayed;

    }

    public LinkedList<PresidentCard> play(PresidentCard cardValue, int numberOfCards) {

        LinkedList<PresidentCard> cardsPlayed = null;

        while (!isValidPlay()) {

            showHand();
            cardsPlayed = getPlay(cardValue,numberOfCards);

        }

        return cardsPlayed;
    }

    private LinkedList<PresidentCard> getPlay(PresidentCard cardValue, int numberOfCards) {

        out.println("Please pick a card and number of cards:");
        String symbol = "";
        String numberOfCards = "0";

        passed=false;

        try {

            String input = in.readLine(); // EXAMPLE A 3

            if (hasPassed(input)) {
                return null;
            }

            symbol = input.split(" ")[0];
            numberOfCards = input.split(" ")[1];

            if (!isInputValid(symbol, numberOfCards,cardValue,numberOfCards)) {
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

    private boolean isInputValid(String symbol, String numberOfCards, PresidentCard cardValue, String numberOfCards1) {


        if (!symbolIsValid(symbol)) {
            out.println("That's not a valid card, choose other");
            return false;
        }

        if (!playerHasCards(symbol, numberOfCards)) {
            out.println("you dont have that many cards");
            return false;
        }


        if(!compareCards(symbol,numberOfCards,cardValue,numberOfCards)){


            return false;
        }

        return true;

    }


    private LinkedList<PresidentCard> getPlay() {

        out.println("Please pick a card and number of cards:");
        String symbol = "";
        String numberOfCards = "0";

        passed=false;

        try {

            String input = in.readLine(); // EXAMPLE A 3

            if (hasPassed(input)) {
                return null;
            }

            symbol = input.split(" ")[0];
            numberOfCards = input.split(" ")[1];

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
        System.out.println(name + " has passed "+passed);
        return passed;
    }

    private LinkedList<PresidentCard> updateHand(String symbol, String numberOfCards) {

        LinkedList<PresidentCard> cardsPlayed = new LinkedList<>();

        int counter = 0;
        int iterator = 0;

        for (PresidentCard card : hand) {

            if (card.getValue().getNumber().equals(symbol) &&
                    counter <= Integer.parseInt(numberOfCards)) {

                cardsPlayed.add(hand.remove(iterator));
                counter++;

            }

            iterator++;
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


        return true;

    }

    private boolean playerHasCards(String symbol, String numberOfCards) {

        int counter = 0;

        for (PresidentCard card : hand) {

            if (card.getValue().getNumber().equals(symbol)) {

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

            if (PresidentCards.values()[i].getNumber().equals(value)) {
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


}
