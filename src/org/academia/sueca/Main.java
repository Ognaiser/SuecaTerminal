package org.academia.sueca;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        test();
    }

    private static void test(){

        Card[] hand = new Card[7];

        hand[0] = new Card(Suits.HEARTS,CardsNumber.SEVEN);
        hand[1] = new Card(Suits.SPADES,CardsNumber.SIX);
        hand[2] = new Card(Suits.DIAMONDS,CardsNumber.ACE);
        hand[3] = new Card(Suits.CLUBS,CardsNumber.KING);
        hand[4] = new Card(Suits.HEARTS,CardsNumber.JACK);
        hand[5] = new Card(Suits.HEARTS,CardsNumber.JACK);
        hand[6] = new Card(Suits.CLUBS,CardsNumber.QUEEN);


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


        System.out.println(hand[0].getRepresentacion());
    }


}
