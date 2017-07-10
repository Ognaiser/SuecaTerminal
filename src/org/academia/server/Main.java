package org.academia.server;

import org.academia.sueca.Card;
import org.academia.sueca.SuecaCards;
import org.academia.sueca.Suits;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        test();
    }

    private static void test(){

        Card[] hand = new Card[7];

        hand[0] = new Card(Suits.HEARTS, SuecaCards.SEVEN);
        hand[1] = new Card(Suits.SPADES, SuecaCards.SIX);
        hand[2] = new Card(Suits.DIAMONDS, SuecaCards.ACE);
        hand[3] = new Card(Suits.CLUBS, SuecaCards.KING);
        hand[4] = new Card(Suits.HEARTS, SuecaCards.JACK);
        hand[5] = new Card(Suits.HEARTS, SuecaCards.JACK);
        hand[6] = new Card(Suits.CLUBS, SuecaCards.QUEEN);


        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < hand.length; i++) {
                System.out.print(hand[i].getHandRep().split(":")[j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        for (int i = 1; i <= hand.length ; i++){
            System.out.print("    "+i+"     ");
        }
        System.out.println();
    }
}
