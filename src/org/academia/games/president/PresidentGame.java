package org.academia.games.president;


import java.util.LinkedList;

public class PresidentGame implements Runnable {

    private LinkedList<PresidentCard> deck = new LinkedList<>();
    private LinkedList<PresidentPlayer> players;
    private PresidentPlayer firstPlayer;
    private int playersInGame;

    public PresidentGame(LinkedList<PresidentPlayer> players) {
        this.players = players;
        playersInGame = players.size();
    }

    @Override
    public void run() {

        System.out.println("starting President Game");
        start();
    }

    private void start() {

        generateDeck();
        System.out.println("generated deck");
        distributeHands();
        System.out.println("distributed deck");
        getFirstPlayer();
        System.out.println("got the first player");
        playGame();
        System.out.println("President ended");
        returnToChat();
    }

    private void playGame() {

        int playersInGame = players.size();
        showHands();

        while (!gameFinished(playersInGame)) {

            System.out.println("in main cycle");
            firstPlayer = players.getFirst();

            LinkedList<PresidentCard> firstPlay = firstPlayer.play();
            PresidentCard cardValue = firstPlay.getFirst();
            int numberOfCards = firstPlay.size();

            for (int i = 1; i < players.size(); i++) {

                players.get(i).play(cardValue, numberOfCards);

                if (players.get(i).getHand().size() == 0) {

                    showResult(players.get(i));

                    playersInGame--;
                }

            }

        }

    }

    private void showHands() {

        for (int i = 1; i < players.size(); i++) {
            players.get(i).showHand();
        }

    }

    private void showResult(PresidentPlayer player) {

        if (playersInGame == players.size()) {

            player.sendMessage("Congrats you are president!!!!!!!!!!");
            return;
        }

        if (playersInGame == players.size() - 1) {

            player.sendMessage("Congrats you are Vice-president!!");
            return;
        }

        if (playersInGame == 2) {

            player.sendMessage("Sorry but you are the Vice-Asshole");
            return;
        }

        if (playersInGame == 1) {

            player.sendMessage("Really Sorry but you are . . . the Asshole");
            return;
        }

        player.sendMessage("You in a neutral position on the political landscape");
        playersInGame--;
    }

    private boolean gameFinished(int playersInGame) {

        if (playersInGame == 1) {
            return true;
        }
        return false;
    }

    private void getFirstPlayer() {
        System.out.println("trying to get the first player");
        for (int i = 1; i < players.size(); i++) {

            if (players.get(i).hasThreeOfClubs()) {
                firstPlayer = players.get(i);
                System.out.println("i got the first player " + firstPlayer.getName());
                break;
            }
        }
    }

    private void generateDeck() {

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
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

        if (deck.size()==0){
            return;
        }
        int randomCard;

        randomCard = ((int) (Math.random() * deck.size()));

        player.receiveCard(deck.remove(randomCard));
    }

    private void returnToChat() {
        for (PresidentPlayer player : players) {
            player.getBacktoChat();
        }
    }
}
