package org.academia.games.president;


import java.util.LinkedList;

public class PresidentGame implements Runnable {

    private LinkedList<PCard> deck = new LinkedList<>();
    private LinkedList<PresidentPlayer> playersInGame;
    private PresidentPlayer firstPlayer;
    private int numberOfPlayersInGame;

    public PresidentGame(LinkedList<PresidentPlayer> playersInGame) {
        this.playersInGame = playersInGame;
        numberOfPlayersInGame = playersInGame.size();
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

    private void generateDeck() {

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 14; j++) {
                deck.add(new PCard(PCardSuits.values()[i], PCardValues.values()[j]));
            }
        }

        // creating the 2 jokers
        deck.add(new PCard());
        deck.add(new PCard());
    }

    private void distributeHands() {

        while (deckHasCards()) {

            for (PresidentPlayer player : playersInGame) {

                giveRandomCard(player);
            }
        }
    }

    private void getFirstPlayer() {
        for (int i = 1; i < playersInGame.size(); i++) {

            if (playersInGame.get(i).hasThreeOfClubs()) {
                firstPlayer = playersInGame.get(i);
                break;
            }
        }
    }

    private void playGame() {

        //numberOfPlayersInGame = playersInGame.size();
        printStartingHands();
        LinkedList<PCard> stackOfPlayedCards = null;
        PCard lastCardPlayed;
        int numberOfCards = 0;

        while (!gameFinished(numberOfPlayersInGame)) {

            System.out.println("\n--New while loop starting--");

            firstPlayer = playersInGame.getFirst();

            for (PresidentPlayer player : this.playersInGame) {

                System.out.println("Who is playing? -> " + player.getName());

                if (player.equals(firstPlayer)) {

                    stackOfPlayedCards = firstPlayer.firstPlay();
                    numberOfCards = stackOfPlayedCards.size();
                    System.out.println("Number of cards played -> " + numberOfCards);

                }
                else {

                    lastCardPlayed = stackOfPlayedCards.peek();
                    stackOfPlayedCards = player.assistPlay(lastCardPlayed,numberOfCards);

                }

                sendAll(player.getName() + " played: \n\r" + stackOfPlayedCards.peek().getRepresentation());
                //showPlay(stackOfPlayedCards);

                if (player.getHand().size() == 0){

                    showResult(player);
                    playersInGame.remove(player);
                    break;

                }
            }

            /*for (int i = 1; i < this.playersInGame.size(); i++) {

                System.out.println("Player assisting -> " + this.playersInGame.get(i).getName());

                stackOfPlayedCards = this.playersInGame.get(i).assistPlay(lastCardPlayed, numberOfCards);
                showPlay(stackOfPlayedCards);
                System.out.println("After calling assistPlay()");

                if (this.playersInGame.get(i).getHand().size() == 0) {

                    showResult(this.playersInGame.get(i));

                    playersInGame--;
                }

            } */

        }

    }

    private void returnToChat() {
        for (PresidentPlayer player : playersInGame) {
            player.getBacktoChat();
        }
    }

    private void sendAll(String text) {

        for (PresidentPlayer player : playersInGame) {

            player.sendMessage(text);
        }
    }

    private void printStartingHands() {

        for (int i = 1; i < playersInGame.size(); i++) {
            playersInGame.get(i).printHand();
        }

    }

    private void showResult(PresidentPlayer player) {

        if (numberOfPlayersInGame == playersInGame.size()) {

            player.sendMessage("Congrats you are president!!!!!!!!!!");
            return;
        }

        if (numberOfPlayersInGame == playersInGame.size() - 1) {

            player.sendMessage("Congrats you are Vice-president!!");
            return;
        }

        if (numberOfPlayersInGame == 2) {

            player.sendMessage("Sorry but you are the Vice-Asshole");
            return;
        }

        if (numberOfPlayersInGame == 1) {

            player.sendMessage("Really Sorry but you are . . . the Asshole");
            return;
        }

        player.sendMessage("You in a neutral position on the political landscape");
        numberOfPlayersInGame--;
    }

    private boolean gameFinished(int playersInGame) {

        return playersInGame == 1;
    }

    private boolean deckHasCards() {
        return deck.size() != 0;
    }

    private void giveRandomCard(PresidentPlayer player) {

        if (deck.size() == 0) {
            return;
        }
        int randomCard;

        randomCard = ((int) (Math.random() * deck.size()));

        player.receiveCard(deck.remove(randomCard));
    }

    private void showPlay(LinkedList<PCard> playedCards) {

        String played = "";

        for (int line = 0; line < 7; line++) {
            for (int i = 0; i < playedCards.size(); i++) {
                played += playedCards.get(i).getHandRep().split(":")[line];
                played += " ";
            }

            sendAll("one play" + played);
        }

    }

}
