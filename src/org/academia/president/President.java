package org.academia.president;

/**
 * Created by codecadet on 10/07/2017.
 */

import org.academia.sueca.Card;
import org.academia.sueca.ClientHandler;
import org.academia.sueca.SuecaCards;
import org.academia.sueca.Suits;

import java.util.LinkedList;
import java.util.List;


public class President {

    private final int MAX_PLAYERS = 5;
    private final int INITIAL_HANDSIZE = 10;
    private LinkedList<Card> deck = new LinkedList<>();
    private List<ClientHandler> players;


    public President(List<ClientHandler> players) {

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


        Card[] turnCards = new Card[MAX_PLAYERS];
        Card turnCard = null;
        int i = 0;

        while (getWinner().equals(null)) {

            for (ClientHandler player : players) {

                turnCard = player.play();
                sendAll(turnCard.getRepresentacion());

                turnCards[i] = turnCard;
            }

            int winner = getWinner(turnCards);

            players.get(winner).addScore(turnCards);

            setFirstPlayer(players.get(winner));

        }

    }

    private ClientHandler getWinner() {

        int i = 0;

        for (ClientHandler player : players) {

            if (player.getHand().isEmpty()) {

                return player;
            }

            i++;
        }

        return null;
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
        //atenção a propriedade trunfo;

        return 0;
    }

    private void showScore() {

        //TODO:  Miguel

        //contruir score, somar os pontos da equipa e contruir uma msg paneleira
    }

}

