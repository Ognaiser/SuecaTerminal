package org.academia.president;


import java.util.LinkedList;

public class PresidentGame implements Runnable {

    private LinkedList<CardPresident> deck = new LinkedList<>();
    private LinkedList<PresidentClient> players;

    @Override
    public void run() {
        start();
    }


    public void start() {
        generateDeck();
        distributeHands();
        getFirstPlayer();
        //playGame();
    }

    private void getFirstPlayer() {

        for (int i = 1; i < players.size(); i++) {
            players.get(i).showHand();
        }

    }


    private void generateDeck() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new CardPresident(SuitPresident.values()[i], PresidentCards.values()[j]));
            }
        }

        // creating the 2 jokers
        deck.add(new CardPresident());
        deck.add(new CardPresident());
    }

    private void distributeHands() {

        LinkedList<CardPresident> hand;

        for (PresidentClient player : players) {

            hand = generateHand();
            player.setHand(hand);
        }

    }


    private LinkedList<CardPresident> generateHand() {

        LinkedList<CardPresident> hand = new LinkedList<>();
        int randomCard;
        //TODO: fix cycle

        for (int i = 0; i <5; i++) {
            randomCard = ((int) (Math.random() * deck.size()));
            hand.add(deck.remove(randomCard));
        }

        return hand;
    }


}
