package org.academia.games.sueca;


import org.academia.games.Game;

import java.util.LinkedList;


public class SuecaGame implements Game, Runnable {

    private final int MAX_TURNS = 10;
    private final int MAX_PLAYERS = 4;
    private final int INITIAL_HANDSIZE = 10;
    private LinkedList<SuecaCard> deck = new LinkedList<>();
    private LinkedList<SuecaPlayer> players;
    private SuecaCard trump;


    public SuecaGame(LinkedList<SuecaPlayer> players) {
        this.players = players;
    }

    @Override
    public void run() {
        start();
    }

    public void start() {

        generateDeck();
        distributeHands();
        System.out.println("done");
        assignNames();
        System.out.println("1");
        playGame();
        showScore();
        returnToChat();

    }

    private void returnToChat(){
        for (SuecaPlayer player : players) {
            player.getBacktoChat();
        }
    }

    private void generateDeck() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                deck.add(new SuecaCard(SuecaSuit.values()[i], SuecaCards.values()[j]));
            }
        }
    }

    private void distributeHands() {

        LinkedList<SuecaCard> hand;

        for (SuecaPlayer player : players) {

            hand = generateHand();
            player.setHand(hand);
        }

        assignTrump();
    }

    private void assignTrump() {

        trump = players.get(players.size() - 1).getHand().get(9);
    }

    private LinkedList<SuecaCard> generateHand() {

        LinkedList<SuecaCard> hand = new LinkedList<>();
        int randomCard;

        for (int i = 0; i < INITIAL_HANDSIZE; i++) {
            randomCard = ((int) (Math.random() * deck.size()));
            hand.add(deck.remove(randomCard));
        }

        return hand;
    }

    private void assignNames() {

        for (SuecaPlayer player : players) {
            player.assignNick();
        }
    }

    public void playGame() {

        int turn = 1;
        SuecaCard[] turnSuecaCards = new SuecaCard[MAX_PLAYERS];
        SuecaCard turnSuecaCard = null;
        int i = 0;

        greetPlayer();


        sendAll("Trump is: \r\n" + trump.getRepresentation() + "\n\r");

        showHands();

        while (turn <= MAX_TURNS) {
            i = 0;

            for (SuecaPlayer player : players) {

                turnSuecaCard = player.play();

                if (player.isCommand()) {

                    if(checkForCheater(player.getAccusedPlayer())){
                        waivedEndGame();
                        break;
                    }
                }

                if (!validCard(turnSuecaCard, turnSuecaCards[0], player)) {
                    player.hasCheated();
                    System.out.println("player " + player + " has cheated");
                }

                sendAll(player.getName() + " played: \n\r" + turnSuecaCard.getRepresentation());

                turnSuecaCards[i] = turnSuecaCard;
                i++;
            }

            int winner = getWinner(turnSuecaCards);
            players.get(winner).addScore(turnSuecaCards);
            setFirstPlayer(winner);

            sendAll("______________________________" + turn + "__________________________________");

            turn++;
        }

    }


    private void showHands() {

        for (int i = 1; i < players.size(); i++) {
            players.get(i).showHand();
        }

    }

    private boolean checkForCheater(String playerName) {

        for (SuecaPlayer currentPlayer: players) {

            if(currentPlayer.isACheater() && currentPlayer.getName().equals(playerName)){
                return true;
            }
        }
        return false;
    }

    private void waivedEndGame() {
        //TODO: ends the game when player has waived and has been discovered
        //TODO: test Waive methods

    }

    private void setFirstPlayer(int roundWinner) {

        //TODO: Bugfix reorganizing

        System.out.println("Winner is: " + players.get(roundWinner).getName());
        SuecaPlayer toRemove;

        System.out.print("BEFORE: ");
        for (SuecaPlayer each : players) {
            System.out.print(each.getName() + " ");
        }

        for (int i = 0; i < players.size(); i++) {

            if (i == roundWinner) {
                System.out.println();

                System.out.print("NEW order of players: ");
                for (SuecaPlayer each : players) {
                    System.out.print(each.getName() + " ");
                }
                return;
            }

            toRemove = players.removeFirst();
            System.out.println("\nto Remove -> " + toRemove.getName());
            players.addLast(toRemove);

        }
        System.out.println();

        System.out.print("NEW order of players: ");
        for (SuecaPlayer each : players) {
            System.out.print(each.getName() + " ");
        }

    }

    private void sendAll(String text) {

        for (SuecaPlayer player : players) {

            player.sendMessage(text);
        }
    }

    private int getWinner(SuecaCard[] turnSuecaCards) {

        SuecaCard winnerSuecaCard = turnSuecaCards[0];

        for (int playerCard = 1; playerCard < MAX_PLAYERS; playerCard++) {

            winnerSuecaCard = compareCards(winnerSuecaCard, turnSuecaCards[playerCard]);


        }
        System.out.println("Winner PresidentCard is \n " + winnerSuecaCard.getRepresentation());

        return getIndex(turnSuecaCards, winnerSuecaCard);
    }

    private int getIndex(SuecaCard[] turnSuecaCards, SuecaCard winnerSuecaCard) {

        int index = -1;

        for (int i = 0; i < turnSuecaCards.length; i++) {
            if (turnSuecaCards[i].equals(winnerSuecaCard)) {
                index = i;
            }
        }
        System.out.println("Index of winner card is: " + index);
        return index;
    }

    private SuecaCard compareCards(SuecaCard first, SuecaCard second) {

        if (sameSuit(first, second)) {
            return compareCardValue(first, second);
        }

        if (!isTrump(second)) {
            return first;

        } else {
            return second;
        }

    }


    private boolean validCard(SuecaCard playedSuecaCard, SuecaCard firstSuecaCard, SuecaPlayer player) {

        SuecaSuit turnSuecaSuit = firstSuecaCard.getSuecaSuit();

        if (playedSuecaCard.getSuecaSuit().equals(turnSuecaSuit)) {
            return true;
        }

        if (player.hasSuit(turnSuecaSuit)) {
            return false;
        }

        return true;
    }

    private SuecaCard compareCardValue(SuecaCard first, SuecaCard second) {
        return (first.getCardNumber().ordinal() < second.getCardNumber().ordinal()) ? first : second;
    }

    private boolean sameSuit(SuecaCard first, SuecaCard second) {

        return first.getSuecaSuit().equals(second.getSuecaSuit());
    }


    private boolean isTrump(SuecaCard first) {

        return first.getSuecaSuit().equals(trump.getSuecaSuit());
    }


    private void showScore() {

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

    private void greetPlayer() {
        sendAll(" ___        _______   _________   ________           ________   ___        ________       ___    ___ \n" +
                "|\\  \\      |\\  ___ \\ |\\___   ___\\|\\   ____\\         |\\   __  \\ |\\  \\      |\\   __  \\     |\\  \\  /  /|\n" +
                "\\ \\  \\     \\ \\   __/|\\|___ \\  \\_|\\ \\  \\___|_        \\ \\  \\|\\  \\\\ \\  \\     \\ \\  \\|\\  \\    \\ \\  \\/  / /\n" +
                " \\ \\  \\     \\ \\  \\_|/__   \\ \\  \\  \\ \\_____  \\        \\ \\   ____\\\\ \\  \\     \\ \\   __  \\    \\ \\    / / \n" +
                "  \\ \\  \\____ \\ \\  \\_|\\ \\   \\ \\  \\  \\|____|\\  \\        \\ \\  \\___| \\ \\  \\____ \\ \\  \\ \\  \\    \\/  /  /  \n" +
                "   \\ \\_______\\\\ \\_______\\   \\ \\__\\   ____\\_\\  \\        \\ \\__\\     \\ \\_______\\\\ \\__\\ \\__\\ __/  / /    \n" +
                "    \\|_______| \\|_______|    \\|__|  |\\_________\\        \\|__|      \\|_______| \\|__|\\|__||\\___/ /     \n" +
                "                                    \\|_________|                                        \\|___|/      \n");

        sendAll("        _        _                  _             _             _          \n" +
                "       / /\\     /\\_\\               /\\ \\         /\\ \\           / /\\        \n" +
                "      / /  \\   / / /         _    /  \\ \\       /  \\ \\         / /  \\       \n" +
                "     / / /\\ \\__\\ \\ \\__      /\\_\\ / /\\ \\ \\     / /\\ \\ \\       / / /\\ \\      \n" +
                "    / / /\\ \\___\\\\ \\___\\    / / // / /\\ \\_\\   / / /\\ \\ \\     / / /\\ \\ \\     \n" +
                "    \\ \\ \\ \\/___/ \\__  /   / / // /_/_ \\/_/  / / /  \\ \\_\\   / / /  \\ \\ \\    \n" +
                "     \\ \\ \\       / / /   / / // /____/\\    / / /    \\/_/  / / /___/ /\\ \\   \n" +
                " _    \\ \\ \\     / / /   / / // /\\____\\/   / / /          / / /_____/ /\\ \\  \n" +
                "/_/\\__/ / /    / / /___/ / // / /______  / / /________  / /_________/\\ \\ \\ \n" +
                "\\ \\/___/ /    / / /____\\/ // / /_______\\/ / /_________\\/ / /_       __\\ \\_\\\n" +
                " \\_____\\/     \\/_________/ \\/__________/\\/____________/\\_\\___\\     /____/_/\n");
    }


}
