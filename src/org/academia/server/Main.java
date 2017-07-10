package org.academia.server;

import org.academia.sueca.Card;
import org.academia.sueca.SuecaCards;
import org.academia.sueca.Suit;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.init();
        test();
    }

    private static void test(){

        Card[] hand = new Card[7];


        hand[0] = new Card(Suit.HEARTS, SuecaCards.SEVEN);
        hand[1] = new Card(Suit.SPADES, SuecaCards.SIX);
        hand[2] = new Card(Suit.DIAMONDS, SuecaCards.ACE);
        hand[3] = new Card(Suit.CLUBS, SuecaCards.KING);
        hand[4] = new Card(Suit.HEARTS, SuecaCards.JACK);
        hand[5] = new Card(Suit.HEARTS, SuecaCards.JACK);
        hand[6] = new Card(Suit.CLUBS, SuecaCards.QUEEN);



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
