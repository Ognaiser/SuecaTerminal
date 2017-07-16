package org.academia.games.president;


//TODO: Implement the logic: Joker beats all
//TODO: Reorganize players (higher card wins the turn)
//TODO: Player with 3 of Clubs starts to play the game
//TODO:

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

    private int getFirstPlayer() {
        for (int i = 0; i < playersInGame.size(); i++) {

            if (playersInGame.get(i).hasThreeOfClubs()) {
                firstPlayer = playersInGame.get(i);
                return i;
            }
        }
        return -1;
    }

    private void setFirstPlayer(int roundWinner) {

        if (roundWinner == -1) {
            System.out.println("Houston we have a problem on setting first player");
        }

        System.out.println("Winner is: " + playersInGame.get(roundWinner).getName());
        PresidentPlayer toRemove;

        for (int i = 0; i < playersInGame.size(); i++) {

            if (i == roundWinner) {
                return;
            }

            toRemove = playersInGame.removeFirst();
            //System.out.println("\nto Remove -> " + toRemove.getName());
            playersInGame.addLast(toRemove);

        }

    }

    private void playGame() {

        //numberOfPlayersInGame = playersInGame.size();
        printStartingHands();
        LinkedList<PCard> stackOfPlayedCards = new LinkedList<>();
        LinkedList<PCard> lastPlayedCards;
        PCard cardPlayedBefore;
        int numberOfCards = 0;
        int winnerIndex = 0;

        while (!gameFinished(numberOfPlayersInGame)) {

            System.out.println("\n--New turn starting--");

            firstPlayer = playersInGame.getFirst();

            setFirstPlayer(getFirstPlayer());
            boolean turnStart = true;

            while (!allButOnePassed() || stackOfPlayedCards.peek().getValue().equals(PCardValues.JOKER)) {

                for (PresidentPlayer player : this.playersInGame) {

                    if (player.hasPassed()) {
                        continue;
                    }

                    System.out.println("Who is playing? -> " + player.getName());

                    if (player.equals(firstPlayer) && turnStart) {

                        //stackOfPlayedCards = firstPlayer.firstPlay();
                        lastPlayedCards = firstPlayer.firstPlay();
                        numberOfCards = lastPlayedCards.size();
                        System.out.println("Number of cards played -> " + numberOfCards);
                        turnStart = false;
                    } else {
                        System.out.println("Assisting play now");

                        cardPlayedBefore = stackOfPlayedCards.peek();
                        lastPlayedCards = player.assistPlay(cardPlayedBefore, numberOfCards);

                        if (player.hasPassed()) {
                            sendAll(player.getName() + " has passed.");
                            continue;
                        }


                    }

                    winnerIndex = playersInGame.indexOf(player);
                    stackOfPlayedCards.addAll(lastPlayedCards);

                    showPlay(player, stackOfPlayedCards, numberOfCards);

                    if (player.getHand().size() == 0) {

                        showResult(player);
                        playersInGame.remove(player);
                        break;

                    }
                }
            }

            setFirstPlayer(winnerIndex);

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

        player.sendMessage("You are in a neutral position on the political landscape");
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

    private void showPlay(PresidentPlayer player, LinkedList<PCard> stackOfPlayedCards, int numberOfCardsPlayed) {

        String cardsToShow = "";
        System.out.println("Showing played cards now.");

        for (int line = 0; line < 7; line++) {
            for (int i = stackOfPlayedCards.size() - numberOfCardsPlayed; i < stackOfPlayedCards.size(); i++) {

                cardsToShow = cardsToShow.concat(stackOfPlayedCards.get(i).getHandRep().split(":")[line]);
                cardsToShow = cardsToShow.concat(" ");
            }
            cardsToShow = cardsToShow.concat("\r\n");
        }

        cardsToShow = cardsToShow.concat("\r\n");

        System.out.println(player.getName() + "played cards: \r\n" + cardsToShow);
        sendAll(player.getName() + " played: \n\r" + cardsToShow);

    }

    private boolean allButOnePassed() {
        int numberOfPlayersThatPassed = 0;

        for (PresidentPlayer player : playersInGame) {
            System.out.println("checking if all but one passed " + player);

            if (player.hasPassed()) {
                numberOfPlayersThatPassed++;
            }

            if (numberOfPlayersThatPassed == playersInGame.size() - 1) {
                resetPlayers();
                return true;
            }
        }
        return false;
    }

    private void resetPlayers() {

        for (PresidentPlayer player : playersInGame) {

            player.setPassed(false);
        }

    }
}
