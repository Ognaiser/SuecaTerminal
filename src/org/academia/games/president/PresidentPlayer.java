package org.academia.games.president;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    private boolean isCommand;

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

    public PresidentCard play() {


        showHand();
        out.println("Please pick a card and number of cards:");
        int cardPlayed;

        try {

            String input = in.readLine();

            if (input.length() > 1) {
                checkCommand(input);
            }
            cardPlayed = Integer.parseInt(in.readLine());

            while (cardPlayed < 1 || cardPlayed > hand.size()) {

                out.println("That is not a valid card number. \nPlease insert a number between 1 and " + (hand.size()));
                cardPlayed = Integer.parseInt(in.readLine());

            }

            cardPlayed -= 1;

            return hand.remove(cardPlayed);


        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.exit(1);

        }
        return null;
    }

    public void checkCommand(String input) {

        String[] words = input.split(" ");
        if (words[0].equals("!waived")) {
            isCommand = true;
        }
        //TODO MIGUEL verificar o nome no segundo elemento do array

    }


    public boolean isCommand(){
        return isCommand;
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

    public void receiveCard(PresidentCard card){

        hand.add(card);
    }

    public boolean hasThreeOfClubs() {

        for (PresidentCard card:hand) {

            if (card.getValue().equals(PresidentCards.THREE)
                    && card.getSuit().equals(PresidentSuit.CLUBS)){
                return true;
            }

        }

        return false;
    }
}
