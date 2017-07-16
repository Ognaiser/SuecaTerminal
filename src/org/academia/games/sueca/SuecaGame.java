package org.academia.games.sueca;


import org.academia.games.Game;

import java.util.LinkedList;


public class SuecaGame implements Game, Runnable {

    //TODO: ctl c fila de esepra

    private final int MAX_TURNS = 10;
    private final int MAX_PLAYERS = 4;
    private final int INITIAL_HANDSIZE = 10;
    private LinkedList<SuecaCard> deck = new LinkedList<>();
    private LinkedList<SuecaPlayer> players;
    private SuecaCard trump;
    private String cheaterPlayer = null;

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
        assignNames();
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
        int turnPlayer ;
        boolean waived = false;

        SuecaCard[] turnSuecaCards = new SuecaCard[MAX_PLAYERS];

        SuecaCard turnSuecaCard ;

        greetPlayer();

        sendAll("The TRUMP is: \r\n" + trump.getRepresentation() + "\n\r");

        showHands();

        while (turn <= MAX_TURNS) {

            turnPlayer = 0;

            for (SuecaPlayer player : players) {

                turnSuecaCard = player.play();

                if (player.isCommand()) {

                    if(checkForCheater(player.getAccusedPlayer())){
                        cheaterPlayer = player.getAccusedPlayer();
                        waived = true;
                        break;
                    }else{
                        player.out.println("The player has not waived! Now you cant accuse anymore!");
                        turnSuecaCard = player.play();
                    }
                }


                if (turnPlayer != 0 && !validCard(turnSuecaCard, turnSuecaCards[0], player)) {
                    player.hasCheated();
                }

                sendAll(player.getName() + " played: \n\r" + turnSuecaCard.getRepresentation());

                turnSuecaCards[turnPlayer] = turnSuecaCard;
                turnPlayer++;
            }

            if (!waived) {
                int winner = getWinner(turnSuecaCards);
                SuecaPlayer winningPlayer = players.get(winner);

                winningPlayer.addScore(turnSuecaCards);
                setFirstPlayer(winner);

                sendAll("THE WINNER OF TURN " + turn + " IS -->  " + winningPlayer.getName() + "\r\n" +
                        "______________________________" + turn + "__________________________________");

                turn++;

            }else {
                turn = MAX_TURNS + 1;
            }
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

    private void setFirstPlayer(int roundWinner) {

        System.out.println("Winner is: " + players.get(roundWinner).getName());
        SuecaPlayer toRemove;

        for (int i = 0; i < players.size(); i++) {

            if (i == roundWinner) {
                return;
            }

            toRemove = players.removeFirst();
            System.out.println("\nto Remove -> " + toRemove.getName());
            players.addLast(toRemove);

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
        System.out.println("Winner PCard is \n " + winnerSuecaCard.getRepresentation());

        return getIndex(turnSuecaCards, winnerSuecaCard);
    }

    private int getIndex(SuecaCard[] turnSuecaCards, SuecaCard winnerSuecaCard) {

        int index = -1;

        for (int i = 0; i < turnSuecaCards.length; i++) {
            if (turnSuecaCards[i].equals(winnerSuecaCard)) {
                index = i;
            }
        }

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

        String scoreText = "";
        String firstTeam = players.get(0).getName() + " and " + players.get(2).getName();
        String secondTeam = players.get(1).getName() + " and " + players.get(3).getName();

        if (cheaterPlayer == null) {

            int team1 = players.get(0).getScore() + players.get(2).getScore();
            int team2 = players.get(1).getScore() + players.get(3).getScore();


            scoreText = "FINAL SCORE:\n" +
                    "TEAM 1 - " + firstTeam + ":\n" +
                    team1 + "\n ____________________________________________\n" +
                    "TEAM 2 - " + secondTeam + ":\n" +
                    team2 + "\n ____________________________________________\n" +
                    "THE WINNER IS " + (team1 > team2 ? "TEAM 1!" : "TEAM 2!");

            sendAll(scoreText);

        }else {

            if (players.get(0).getName().equals(cheaterPlayer) || players.get(2).getName().equals(cheaterPlayer)) {

                scoreText = cheaterPlayer + " WAIVED and was caught! WHAT A SHAME!!\n" +
                        "TEAM 1 - " + firstTeam + ":\n" +
                        "YOU LOST!\n" +
                        "_________________________________________\n"+
                        "TEAM 2 - " + secondTeam + ":\n" +
                        "YOU WON!\n";
            }else {

                scoreText = cheaterPlayer + " WAIVED and was caught! WHAT A SHAME!!\n" +
                        "TEAM 1 - " + firstTeam + ":\n" +
                        " YOU WON!\n" +
                        "__________________________________________\n"+
                        "TEAM 2 - " + secondTeam + ":\n" +
                        "- LOST!\n";
            }

            sendAll(scoreText);

        }

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
