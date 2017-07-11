package org.academia.sueca;

import org.academia.server.GameClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by codecadet on 11/07/2017.
 */
public class SuecaClient implements GameClient {

    private List<Card> hand;
    private String name;
    private int score;
    private BufferedReader in;
    private PrintWriter out;

    public SuecaClient(BufferedReader in, PrintWriter out) {

            this.in = in;
            this.out = out;

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

    public void askNick() {
        out.println("Enter your nickname:");

        try {

            this.name = in.readLine();

        } catch (IOException e) {

            System.err.println(e.getMessage());
            System.exit(1);

        }
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Card play() {


        showHand();
        out.println("Please pick a card number:");
        int cardPlayed;

        try {

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


    //TODO: Miguel faz o validateCard()
    private boolean validateCard(Card playedCard, List<Card> hand){



        return true;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }
}
