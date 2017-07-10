package org.academia.sueca;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private final int MAX_TURNS = 10;
    private final int MAX_PLAYERS = 4;
    private LinkedList<Card> deck = new LinkedList<>();
    private List<ClientHandler> players;
    private Card trunfo;
    private Suits trunfoSuit;

    public Game(List<ClientHandler> players) {

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

        for (ClientHandler player : players) {

            hands = generateHand();
            player.setHand(hands);
        }

        trunfo = players.get(players.size() - 1).getHand().get(0);
        trunfoSuit= trunfo.getSuite();
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

        for (ClientHandler player : players) {

            player.askNick();
        }
    }

    public void turn() {

        int turn = 1;
        Card[] turnCards = new Card[MAX_PLAYERS];
        Card turnCard = null;
        int i = 0;

        while (turn < MAX_TURNS) {

            for (ClientHandler player : players) {

                turnCard = player.play();
                sendAll(turnCard.toString());

                turnCards[i] = turnCard;
                i++;
            }

            int winner = getWinner(turnCards);

            players.get(winner).addScore(turnCards);

            organize(players.get(winner));

            turn++;
        }

        displayScore();
    }

    private void organize(ClientHandler roundWinner) {

        int i = 0;

        for (ClientHandler player : players) {

            if (player == roundWinner) {
                return;
            }

            players.add(players.get(i));
            players.remove(player);
            i++;
        }

    }

    private void sendAll(String text) {

    }

    private int getWinner(Card[] turnCards) {

        //devolve a posição da carta vencedora pelo naipe e pellllo ordinal do numero
        //o naipe da jogada é o naipe da posição 0
        //atenção a propriedade trunfo;

        return 0;
    }

    private void displayScore() {

    }

}
