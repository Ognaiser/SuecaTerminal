package org.academia.sueca;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private final int MAX_TURNS = 10;
    private final int MAX_PLAYERS = 4;
    private final int INITIAL_HANDSIZE = 10;
    private LinkedList<Card> deck = new LinkedList<>();
    private List<ClientHandler> players;
    private Card trump;


    public Game(List<ClientHandler> players) {

        this.players = players;
        generateDeck();
    }


    public void start() {

        generateDeck();
        distributeHands();

        askNames();
        playGame();
        showScore();
    }

    private void generateDeck() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                deck.add(new Card(Suits.values()[i], SuecaCards.values()[j]));
            }
        }
    }

    public void distributeHands() {

        LinkedList<Card> hand;

        for (ClientHandler player : players) {

            hand = generateHand();
            player.setHand(hand);
        }

        assignTrump();
    }

    private void assignTrump() {

        trump = players.get(players.size() - 1).getHand().get(0);
    }

    public LinkedList<Card> generateHand() {

        LinkedList<Card> hand = new LinkedList<>();
        int randomCard;

        for (int i = 0; i < INITIAL_HANDSIZE; i++) {
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

    public void playGame() {

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

            setFirstPlayer(players.get(winner));

            turn++;
        }

    }

    private void setFirstPlayer(ClientHandler roundWinner) {

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

        for (ClientHandler player : players) {

            player.sendMessage(text);
        }
    }

    private int getWinner(Card[] turnCards) {

        //TODO: Joao
        //devolve a posição da carta vencedora pelo naipe e pelo ordinal do numero
        //o naipe da jogada é o naipe da posição 0
        //atenção a propriedade trump;

        return 0;
    }

    private void showScore() {

        //TODO:  Miguel
        int team1 = players.get(0).getScore() + players.get(2).getScore();
        int team2 = players.get(1).getScore() + players.get(3).getScore();


        String scoreText = "FINAL SCORE:\n" +
                "TEAM 1 - " + players.get(0).getName() + " and " + players.get(2).getName() + ":\n" +
                team1 + "\n ____________________________________________\n" +
                "TEAM 2 - " + players.get(1).getName() + " and " + players.get(3).getName() + ":\n" +
                team2 + "\n ____________________________________________\n" +
                "THE WINNER IS " + (team1 > team2 ? "TEAM 1!" : "TEAM 2!");

        sendAll(scoreText);
    }

}
