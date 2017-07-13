package org.academia.games.president;


import java.util.LinkedList;

public class PresidentGame implements Runnable {

    private LinkedList<PresidentCard> deck = new LinkedList<>();
    private LinkedList<PresidentPlayer> players;
    private PresidentPlayer firstPlayer;

    @Override
    public void run() {
        start();
    }


    public void start() {

        generateDeck();
        distributeHands();
        getFirstPlayer();
        playGame();
    }

    private void playGame() {

        while (!gameFinished()) {

            firstPlayer = players.getFirst();

            LinkedList<PresidentCard> firstPlay = firstPlayer.play();

            PresidentCard cardValue = firstPlay.getFirst();
            int numberOfCards = firstPlay.size();

            for (int i = 1; i < players.size(); i++) {

                players.get(i).play(cardValue,numberOfCards);
            }




        }

    }

    private boolean gameFinished() {
        return false;
    }

    private void getFirstPlayer() {

        for (int i = 1; i < players.size(); i++) {

            if (players.get(i).hasThreeOfClubs()) {
                firstPlayer = players.get(i);
                break;
            }
        }
    }

    private void generateDeck() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new PresidentCard(PresidentSuit.values()[i], PresidentCards.values()[j]));
            }
        }

        // creating the 2 jokers
        deck.add(new PresidentCard());
        deck.add(new PresidentCard());
    }

    private void distributeHands() {

        while (deckHasCards()) {

            for (PresidentPlayer player : players) {

                giveRandomCard(player);
            }

        }

    }

    private boolean deckHasCards() {
        return deck.size() != 0;
    }

    private void giveRandomCard(PresidentPlayer player) {

        int randomCard;

        randomCard = ((int) (Math.random() * deck.size()));
        player.receiveCard(deck.remove(randomCard));
    }


}
