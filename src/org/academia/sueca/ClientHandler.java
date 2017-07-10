package org.academia.sueca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler {

    private List<Card> hand;
    private String name;
    private int score;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
            System.exit(1);
        }
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

    public void askNick() {
        //TODO:
        out.println("Enter your nickname:");

        try {

            name = in.readLine();

        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.exit(1);

        }
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Card play() {

        //TODO: check number
        return null;
    }

    public void sendMessage(String msg) {

        out.println(msg);
    }

    public int getScore() {
        return score;
    }

    public void addScore(Card[] cards) {
//TODO: decide on where this logic should be
        for (int i = 0; i < cards.length; i++) {
            score += cards[i].getValue();
        }

    }

    public List<Card> getHand() {
        return hand;
    }
}
