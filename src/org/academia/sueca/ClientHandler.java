package org.academia.sueca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

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

    public void askNick(){}

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Card play(){
        return null;
    }

    public void uiUpdate(String msg){

    }

    public int getScore() {
        return score;
    }

    public void addScore(Card[] cards) {

        for (int i = 0; i < cards.length; i++) {
            score += cards[i].getValue();
        }

    }

    @Override
    public void run() {
        askNick();
    }
}
