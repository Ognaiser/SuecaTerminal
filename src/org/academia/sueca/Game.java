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
                deck.add(new Card(Suit.values()[i], SuecaCards.values()[j]));
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

        greetPlayer();

        sendAll("Trump is: \r\n" + trump.getRepresentation() + "\n\r");

        showHands();

        while (turn < MAX_TURNS) {
            i = 0;

            for (ClientHandler player : players) {

                turnCard = player.getHand().get(player.play());

                sendAll(player.getName() + " played: \n\r" + turnCard.getRepresentation());

                turnCards[i] = turnCard;
                i++;
            }

            int winner = getWinner(turnCards);
            players.get(winner).addScore(turnCards);
            setFirstPlayer(players.get(winner));

            turn++;
        }

    }

    private void showHands() {

        for (int i = 1; i < players.size(); i++) {
            players.get(i).showHand();
        }

    }

    private void setFirstPlayer(ClientHandler roundWinner) {

        for (int i = 0; i < players.size(); i++) {

            if (players.get(i) == roundWinner) {
                return;
            }

            players.add(players.remove(i));
        }
    }

    private void sendAll(String text) {

        for (ClientHandler player : players) {

            player.sendMessage(text);
        }
    }

    private int getWinner(Card[] turnCards) {

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

    public void greetPlayer() {
        sendAll(" ___       __    _______    ___        ________   ________   _____ ______    _______      \n" +
                "|\\  \\     |\\  \\ |\\  ___ \\  |\\  \\      |\\   ____\\ |\\   __  \\ |\\   _ \\  _   \\ |\\  ___ \\     \n" +
                "\\ \\  \\    \\ \\  \\\\ \\   __/| \\ \\  \\     \\ \\  \\___| \\ \\  \\|\\  \\\\ \\  \\\\\\__\\ \\  \\\\ \\   __/|    \n" +
                " \\ \\  \\  __\\ \\  \\\\ \\  \\_|/__\\ \\  \\     \\ \\  \\     \\ \\  \\\\\\  \\\\ \\  \\\\|__| \\  \\\\ \\  \\_|/__  \n" +
                "  \\ \\  \\|\\__\\_\\  \\\\ \\  \\_|\\ \\\\ \\  \\____ \\ \\  \\____ \\ \\  \\\\\\  \\\\ \\  \\    \\ \\  \\\\ \\  \\_|\\ \\ \n" +
                "   \\ \\____________\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\_______\\\\ \\__\\    \\ \\__\\\\ \\_______\\\n" +
                "    \\|____________| \\|_______| \\|_______| \\|_______| \\|_______| \\|__|     \\|__| \\|_______|\n");

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
