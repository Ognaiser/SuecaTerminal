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
                deck.add(new Card(Suit.values()[i], CardsNumber.values()[j]));
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

        Card winnerCard = turnCards[0];

        for (int playerCard = 1; playerCard < MAX_PLAYERS; playerCard++) {

            winnerCard = compareCards(winnerCard, turnCards[playerCard]);
        }

        return getIndex(turnCards, winnerCard);
    }

    private int getIndex(Card[] turnCards, Card winnerCard) {

        int index = -1;

        for (int i = 0; i < turnCards.length; i++) {
            if (turnCards[i].equals(winnerCard)) {
                index = i;
            }
        }

        return index;
    }

    private Card compareCards(Card first, Card second) {

        if (sameSuit(first, second)) {
            return compareCardValue(first, second);
        }

        if (!isTrump(second)) {
            return first;

        } else {
            return second;
        }

    }

    private Card compareCardValue(Card first, Card second) {
        return (first.getCardNumber().ordinal() > second.getCardNumber().ordinal()) ? first : second;
    }

    private boolean sameSuit(Card first, Card second) {

        return first.getSuit().equals(second.getSuit());
    }


    private boolean isTrump(Card first) {

        return first.getSuit().equals(trump.getSuit());
    }


    private void showScore() {

        //TODO:  Miguel

        //contruir score, somar os pontos da equipa e contruir uma msg paneleira
    }

}
