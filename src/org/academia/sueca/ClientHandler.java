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

    public void showHand(){

        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < hand.size(); i++) {
                out.print(hand.get(i).getHandRep().split(":")[j]);
                out.print(" ");
            }
            out.println();
        }

        for (int i = 1; i <= hand.size(); i++){
            out.print("    "+i+"     ");
        }
        out.println();
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

}
