package org.academia.games.president;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class PresidentPlayer {

    //TODO:extend GameClientTest! (to see if its ok)

    private List<PresidentCard> hand;
    private String name;
    private int position;
    private BufferedReader in;
    private PrintWriter out;
    private boolean cheated = false;
    private Socket socket;
    private boolean validPlay;

    public PresidentPlayer(Socket socket) {

        this.socket = socket;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println("Hello my niggas");
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


    private LinkedList<PresidentCard> getPlay() {

        out.println("Please pick a card and number of cards:");
        String symbol = "";
        String numberOfCards = "0";

        try {

            String input = in.readLine(); // A 3


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

        System.out.println("cards played "+cardsPlayed.toString());
        return cardsPlayed;

    }

    private boolean isInputValid(String symbol, String numberOfCards) {


        if (!symbolIsValid(symbol)) {
            out.println("That's not a valid card, choose other");
            return false;
        }

        if (!playerHasCards(symbol, numberOfCards)) {
            out.println("");
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

    public List<PresidentCard> getHand() {
        return hand;
    }

    public void setHand(List<PresidentCard> hand) {
        this.hand = hand;
    }

    public Socket getSocket() {
        return socket;
    }

    public void receiveCard(PresidentCard card) {

        hand.add(card);
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
