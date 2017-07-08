package org.academia.sueca;

public class Game {

    private Card[] deck;
    private ClientHandler[] players;
    private final int MAX_TURNS = 10;
    private final int MAX_PLAYERS = 4;

    public Game(ClientHandler[] players) {
        this.players = players;
        generateDeck();
    }

    private void generateDeck(){

    }

    private void turn(){

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

    }

    private void sendAll(String text){

    }

    private int getWinner(Card[] turnCards){

        return 0;
    }

    private void displayScore(){

    }

}
