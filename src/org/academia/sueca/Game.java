package org.academia.sueca;

import java.util.LinkedList;

public class Game {

    private final int MAX_TURNS = 10;
    private final int MAX_PLAYERS = 4;
    private LinkedList<Card> deck = new LinkedList<>();
    private ClientHandler[] players;

    public Game(ClientHandler[] players) {
        this.players = players;
        generateDeck();
    }

    private void generateDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                deck.add(new Card(Suits.values()[i], CardsNumber.values()[j]));
            }
        }
    }

    public void distributeHands() {

        LinkedList<Card> hands;

        for (int i = 0; i < MAX_PLAYERS; i++) {
            hands = generateHand();
            players[i].setHand(hands);
        }

    }

    public LinkedList<Card> generateHand() {

        LinkedList<Card> hand = new LinkedList<>();
        int randomCard;

        for (int i = 0; i < 10; i++) {
            randomCard = ((int) (Math.random() * deck.size()));
            hand.add(deck.remove(randomCard));
        }

        return hand;
    }

    public void askNames() {

    }

    public void turn() {

        int turn = 1;

        while (turn < MAX_TURNS) {

            Card[] turnCards = new Card[MAX_PLAYERS];

            for (int i = 0; i < MAX_PLAYERS; i++) {
                turnCards[i] = players[i].play();
                sendAll(turnCards[i].toString());
            }

            int winner = getWinner(turnCards);

            players[winner].addScore(turnCards);

            organize();


            turn++;
        }

        displayScore();
    }

    private void organize() {

        //TODO: Bruno! you are retarded!


    }

    private void sendAll(String text) {

    }

    private int getWinner(Card[] turnCards) {

        return 0;
    }

    private void displayScore() {

    }

}
