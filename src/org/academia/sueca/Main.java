package org.academia.sueca;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        test();
    }

    private static void test(){

        Card[] hand = new Card[7];

        hand[0] = new Card(Suit.HEARTS,CardsNumber.SEVEN);
        hand[1] = new Card(Suit.SPADES,CardsNumber.SIX);
        hand[2] = new Card(Suit.DIAMONDS,CardsNumber.ACE);
        hand[3] = new Card(Suit.CLUBS,CardsNumber.KING);
        hand[4] = new Card(Suit.HEARTS,CardsNumber.JACK);
        hand[5] = new Card(Suit.HEARTS,CardsNumber.JACK);
        hand[6] = new Card(Suit.CLUBS,CardsNumber.QUEEN);


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
